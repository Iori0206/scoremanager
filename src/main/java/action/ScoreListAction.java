package action;

import java.util.List;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.TestScore;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ScoreListAction extends Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        if (teacher == null) return "login.jsp";

        
        School school = teacher.getSchool();
        if (school == null) {
            school = new School();
            school.setCd("tes"); // テスト用コード
            teacher.setSchool(school);
        }

        StudentDao stDao = new StudentDao();
        SubjectDao sDao = new SubjectDao();
        TestDao dao = new TestDao();

        
        List<Integer> entYearList = stDao.filterEntYear(school);
        List<String> classNumList = stDao.filterClassNum(school);
        List<Subject> subjects = sDao.filter(school);

        req.setAttribute("ent_year_list", entYearList);
        req.setAttribute("class_num_list", classNumList);
        req.setAttribute("subjects", subjects);

        //  検索パラメータ取得
        String entYearStr = req.getParameter("ent_year");
        String classNum = req.getParameter("class_num");
        String subjectCd = req.getParameter("subject_cd");

        // 初回表示（パラメータなし）や未選択のチェック
        if (entYearStr == null || entYearStr.isEmpty() || entYearStr.equals("0") ||
            classNum == null || classNum.isEmpty() || classNum.equals("---") ||
            subjectCd == null || subjectCd.isEmpty() || subjectCd.equals("---")) {
            
            // 初回表示またはエラー時は検索画面へ
            return "score_search.jsp";
        }

        // 4. データ取得処理
        int entYear = Integer.parseInt(entYearStr);
        Subject subject = sDao.get(school, subjectCd);

        // 成績リストの取得
        List<TestScore> list = dao.filter(entYear, classNum, subject, 1, school);

        if (list == null || list.isEmpty()) {
            req.setAttribute("error", "学生情報が存在しませんでした");
            return "score_search.jsp";
        }

        // 結果をリクエストにセット
        req.setAttribute("list", list);
        req.setAttribute("ent_year", entYear);
        req.setAttribute("class_num", classNum);
        req.setAttribute("subject_cd", subjectCd);
        req.setAttribute("subject", subject); // 科目名表示用

        return "score_list.jsp";
    }
}