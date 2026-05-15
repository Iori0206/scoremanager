package action;

import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
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

        if (teacher == null) {
            return "login.jsp";
        }

        School school = teacher.getSchool();
        if (school == null) {
            school = new School();
            school.setCd("tes");
            teacher.setSchool(school);
        }

        StudentDao stDao = new StudentDao();
        ClassNumDao cDao = new ClassNumDao();
        SubjectDao subDao = new SubjectDao();
        TestDao testDao = new TestDao();

        req.setAttribute("years", stDao.filterEntYear(school));
        req.setAttribute("classes", cDao.filter(school));
        req.setAttribute("subjects", subDao.filter(school));

        String entYearStr = req.getParameter("ent_year");
        String classNum = req.getParameter("class_num");
        String subjectCd = req.getParameter("subject_cd");
        String studentNo = req.getParameter("student_no");

        if (entYearStr == null) entYearStr = "";
        if (classNum == null) classNum = "";
        if (subjectCd == null) subjectCd = "";
        if (studentNo == null) studentNo = "";

        req.setAttribute("ent_year", entYearStr);
        req.setAttribute("class_num", classNum);
        req.setAttribute("subject_cd", subjectCd);
        req.setAttribute("student_no", studentNo);

        req.setAttribute("pageTitle", "成績参照");

        // 学生番号検索
        if (!studentNo.trim().isEmpty()) {
            Student student = stDao.get(studentNo.trim());

            req.setAttribute("pageTitle", "成績一覧（学生）");

            if (student != null && student.getSchool() != null
                    && student.getSchool().getCd().equals(school.getCd())) {

                req.setAttribute("student", student);

                TestListStudentDAO tlsDao = new TestListStudentDAO();
                List<TestScore> tests = tlsDao.filter(student);

                if (tests != null && !tests.isEmpty()) {
                    req.setAttribute("tests", tests);
                } else {
                    req.setAttribute("searchError", "成績情報が存在しませんでした");
                }

            } else {
                // 見本に合わせて、入力した学生番号はそのまま残す
                Student dummy = new Student();
                dummy.setNo(studentNo.trim());
                dummy.setName("");
                req.setAttribute("student", dummy);

                req.setAttribute("searchError", "成績情報が存在しませんでした");
            }

            return "score_search.jsp";
        }

        // 科目検索が触られたか
        boolean subjectSearchTried =
                !entYearStr.isEmpty() || !classNum.isEmpty() || !subjectCd.isEmpty();

        if (subjectSearchTried) {
            boolean entYearOk = !entYearStr.isEmpty() && !"0".equals(entYearStr);
            boolean classOk = !classNum.isEmpty() && !"0".equals(classNum);
            boolean subjectOk = !subjectCd.isEmpty() && !"0".equals(subjectCd);

            if (!(entYearOk && classOk && subjectOk)) {
                req.setAttribute("conditionError", "入学年度とクラスと科目を選択してください");
                return "score_search.jsp";
            }

            int entYear = Integer.parseInt(entYearStr);
            Subject subject = subDao.get(school, subjectCd);

            req.setAttribute("pageTitle", "成績一覧（科目）");
            req.setAttribute("subject", subject);

            if (subject == null) {
                req.setAttribute("searchError", "学生情報が存在しませんでした");
                return "score_search.jsp";
            }

            List<TestScore> tests = testDao.filter(entYear, classNum, subject, 1, school);

            if (tests != null && !tests.isEmpty()) {
                req.setAttribute("tests", tests);
            } else {
                req.setAttribute("searchError", "学生情報が存在しませんでした");
            }
        }

        return "score_search.jsp";
    }
}