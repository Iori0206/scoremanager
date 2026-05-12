package action;

import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import bean.TestScore;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import dao.TestListStudentDAO;
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
        StudentDao stDao = new StudentDao();
        SubjectDao sDao = new SubjectDao();
        TestDao dao = new TestDao();

        // 1. プルダウン用データの取得
        req.setAttribute("years", stDao.filterEntYear(school));
        req.setAttribute("classes", stDao.filterClassNum(school));
        req.setAttribute("subjects", sDao.filter(school));
        req.setAttribute("students", stDao.filter(school));

        // 検索パラメータ取得
        String entYearStr = req.getParameter("ent_year");
        String classNum = req.getParameter("class_num");
        String subjectCd = req.getParameter("subject_cd");
        String studentNo = req.getParameter("student_no");

        // 選択状態の保持用
        req.setAttribute("ent_year", entYearStr);
        req.setAttribute("class_num", classNum);
        req.setAttribute("subject_cd", subjectCd);
        req.setAttribute("student_no", studentNo);

        // 初回表示チェック（どちらの検索フォームも未入力の場合）
        if ((entYearStr == null || entYearStr.isEmpty()) && (studentNo == null || studentNo.isEmpty())) {
            return "score_search.jsp";
        }

        // 2. 検索実行処理
        if (studentNo != null && !studentNo.isEmpty()) {
            // 【重要】空白などが混じらないようにトリミング
            studentNo = studentNo.trim();
            
            // 学生情報の取得（DAO側で学校コードを考慮した検索ができるように修正済みであることを想定）
            Student student = stDao.get(studentNo);

            if (student != null) {
                // 自校の学生であることを確認（セキュリティ上重要）
                if (student.getSchool().getCd().equals(school.getCd())) {
                    req.setAttribute("student", student);

                    TestListStudentDAO tlsDao = new TestListStudentDAO();
                    // filterメソッド内で T.STUDENT_NO と T.SCHOOL_CD を使って検索
                    List<TestListStudent> tests = tlsDao.filter(student);
                    
                    if (tests != null && !tests.isEmpty()) {
                        req.setAttribute("tests", tests); 
                    } else {
                        req.setAttribute("error", "成績情報が存在しませんでした。");
                    }
                } else {
                    req.setAttribute("error", "他校の学生情報は参照できません。");
                }
            } else {
                req.setAttribute("error", "学生情報が取得できませんでした。");
            }

        } else if (entYearStr != null && !entYearStr.isEmpty()) {
            // 【年度・クラス・科目で検索する場合】
            int entYear = Integer.parseInt(entYearStr);
            Subject subject = sDao.get(school, subjectCd);
            
            if (subject == null) {
                req.setAttribute("error", "科目を選択してください。");
                return "score_search.jsp";
            }

            // TestDaoを使って検索
            List<TestScore> list = dao.filter(entYear, classNum, subject, 1, school);
            
            if (list != null && !list.isEmpty()) {
                req.setAttribute("tests", list);
            } else {
                req.setAttribute("error", "成績情報が存在しませんでした。");
            }
        }

        return "score_search.jsp";
    }
}