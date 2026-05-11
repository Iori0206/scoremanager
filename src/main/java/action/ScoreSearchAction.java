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

public class ScoreSearchAction extends Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 1. ログインチェック
        if (teacher == null) {
            return "login.jsp";
        }
        
        School school = teacher.getSchool();
        if (school == null) {
            school = new School();
            school.setCd("tes"); 
            teacher.setSchool(school);
        }

        // --- 2. 画面の部品（プルダウン）の準備 ---
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

        // --- 3. 【重要】H2から成績データを検索して取得する処理 ---
        String entYearStr = req.getParameter("ent_year");
        String classNum = req.getParameter("class_num");
        String subjectCd = req.getParameter("subject_cd");
        String studentNo = req.getParameter("student_no"); // 学生番号での検索用

        List<TestScore> list = null;
        TestDao tDao = new TestDao();

        // 検索ボタンが押された（パラメータがある）場合の処理
        if (studentNo != null && !studentNo.isEmpty()) {
            // A. 学生番号で検索する場合（DAOに専用メソッドが必要）
            // list = tDao.filterByStudentNo(studentNo, school);
        } else if (entYearStr != null && !entYearStr.isEmpty()) {
            // B. 年度・クラス・科目で検索する場合
            int entYear = Integer.parseInt(entYearStr);
            Subject subject = sdao.get(school, subjectCd);
            // H2から成績一覧を取得（第4引数は回数：とりあえず1を指定）
            list = tDao.filter(entYear, classNum, subject, 1, school);
        }

        // 検索結果をJSPへ渡す
        if (list != null) {
            req.setAttribute("tests", list);
            if (list.isEmpty()) {
                req.setAttribute("error", "学生情報が存在しませんでした");
            }
        }

        return "score_search.jsp";
    }
}