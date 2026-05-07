package action;

import java.util.List;

import bean.Subject;
import bean.Teacher;
import bean.TestScore;
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

        String entYearStr = req.getParameter("ent_year");
        String classNum = req.getParameter("class_num");
        String subjectCd = req.getParameter("subject_cd");

        // 未選択チェック
        if (entYearStr == null || entYearStr.isEmpty() || 
            classNum == null || classNum.isEmpty() || 
            subjectCd == null || subjectCd.isEmpty()) {
            req.setAttribute("error", "年度・クラス・科目を選択してください");
            return "score_search.jsp";
        }

        int entYear = Integer.parseInt(entYearStr); // Stringをintに変換

        SubjectDao sDao = new SubjectDao();
        Subject subject = sDao.get(subjectCd, teacher.getSchool()); // Subjectオブジェクトを取得

        TestDao dao = new TestDao();
        // メソッド名を getScoreList から filter に修正し、引数を合わせる
        List<TestScore> list = dao.filter(entYear, classNum, subject, 1, teacher.getSchool());

        if (list == null || list.isEmpty()) {
            req.setAttribute("error", "学生情報が存在しませんでした");
            return "score_search.jsp";
        }

        req.setAttribute("list", list);
        req.setAttribute("ent_year", entYear);
        req.setAttribute("class_num", classNum);
        req.setAttribute("subject_cd", subjectCd);

        return "score_list.jsp";
    }
}