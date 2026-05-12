package action;

import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import bean.TestScore;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import dao.TestListStudentDAO;
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
            school.setCd("tes"); // デフォルト値
            teacher.setSchool(school);
        }

        // --- 2. 画面の部品（プルダウン）の準備 ---
        StudentDao stDao = new StudentDao();
        ClassNumDao cdao = new ClassNumDao();
        SubjectDao sdao = new SubjectDao();

        req.setAttribute("years", stDao.filterEntYear(school));
        req.setAttribute("classes", cdao.filter(school));
        req.setAttribute("subjects", sdao.filter(school));
        req.setAttribute("students", stDao.filter(school)); 

        // --- 3. 検索パラメータ取得 ---
        String entYearStr = req.getParameter("ent_year");
        String classNum = req.getParameter("class_num");
        String subjectCd = req.getParameter("subject_cd");
        String studentNo = req.getParameter("student_no");

        // パラメータの値をコンソールに表示（原因特定用）
        System.out.println("--- ScoreSearch Debug ---");
        System.out.println("student_no: [" + studentNo + "]");
        System.out.println("ent_year: [" + entYearStr + "]");
        System.out.println("subject_cd: [" + subjectCd + "]");

        // 選択状態の保持
        req.setAttribute("ent_year", entYearStr);
        req.setAttribute("class_num", classNum);
        req.setAttribute("subject_cd", subjectCd);
        req.setAttribute("student_no", studentNo);

        TestDao tDao = new TestDao();

        // --- 4. 検索処理の実行 ---
        
        // A. 学生番号で検索する場合
        if (studentNo != null && !studentNo.isEmpty()) {
            studentNo = studentNo.trim(); // 空白を確実に除去
            Student student = stDao.get(studentNo);

            if (student != null) {
                // 学校コードが一致しているかチェック（セキュリティ）
                if (student.getSchool().getCd().equals(school.getCd())) {
                    req.setAttribute("student", student);
                    TestListStudentDAO tlsDao = new TestListStudentDAO();
                    List<TestListStudent> tests = tlsDao.filter(student);
                    
                    if (tests != null && !tests.isEmpty()) {
                        req.setAttribute("tests", tests); 
                    } else {
                        req.setAttribute("error", "成績情報が存在しませんでした");
                    }
                } else {
                    req.setAttribute("error", "他校の学生データです");
                }
            } else {
                req.setAttribute("error", "学生情報が存在しませんでした");
            }

        // B. 年度・クラス・科目で検索する場合
        } else if (entYearStr != null && !entYearStr.isEmpty()) {
            try {
                int entYear = Integer.parseInt(entYearStr);
                Subject subject = sdao.get(school, subjectCd);
                
                if (subject == null) {
                    req.setAttribute("error", "科目を選択してください");
                } else {
                    // 第4引数の「回数」は一旦 1 で固定。必要に応じてパラメータ化してください。
                    List<TestScore> list = tDao.filter(entYear, classNum, subject, 1, school);
                    if (list != null && !list.isEmpty()) {
                        req.setAttribute("tests", list);
                    } else {
                        req.setAttribute("error", "成績情報が存在しませんでした");
                    }
                }
            } catch (NumberFormatException e) {
                req.setAttribute("error", "入学年度を正しく選択してください");
            }
        }

        return "score_search.jsp";
    }
}