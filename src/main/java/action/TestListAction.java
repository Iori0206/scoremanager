package action;

import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.TestScore;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListAction extends Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 1. ログインチェック
        if (teacher == null) {
            return "login.jsp";
        }
        
        School school = teacher.getSchool();
        // 学校情報が取得できない場合の予備処置
        if (school == null) {
            school = new School();
            school.setCd("tes"); 
            teacher.setSchool(school);
        }

        // 2. プルダウンの準備（画面を開いた時に必要）
        List<String> years = new ArrayList<>();
        for (int i = 2020; i <= 2030; i++) {
            years.add(String.valueOf(i));
        }
        req.setAttribute("years", years);

        ClassNumDao cdao = new ClassNumDao();
        req.setAttribute("classes", cdao.filter(school));

        SubjectDao sdao = new SubjectDao();
        req.setAttribute("subjects", sdao.filter(school));

        StudentDao stDao = new StudentDao();
        req.setAttribute("students", stDao.filter(school));

        // 3. 検索処理（検索ボタンが押された場合）
        String entYearStr = req.getParameter("ent_year");
        String classNum = req.getParameter("class_num");
        String subjectCd = req.getParameter("subject_cd");
        // 学生番号での直接検索用パラメータ
        String studentNo = req.getParameter("student_no");

        TestDao tDao = new TestDao();
        List<TestScore> list = null;

        if (entYearStr != null && !entYearStr.isEmpty() && 
            classNum != null && !classNum.isEmpty() && 
            subjectCd != null && !subjectCd.isEmpty()) {
            
            // A. 入学年度・クラス・科目での検索
            int entYear = Integer.parseInt(entYearStr);
            Subject subject = sdao.get(school, subjectCd);
            
            if (subject != null) {
                // 第4引数はテスト回数(no)。とりあえず 1 を指定
                list = tDao.filter(entYear, classNum, subject, 1, school);
            }
        } 
        
        // 4. 検索結果をリクエストにセット
        if (list != null) {
            req.setAttribute("tests", list);
            if (list.isEmpty()) {
                req.setAttribute("error", "該当する成績データが見つかりませんでした。");
            }
        }

        return "score_search.jsp";
    }
}