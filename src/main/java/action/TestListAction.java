package action;

import java.util.ArrayList;
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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListAction extends Action {

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

        String entYearStr = req.getParameter("ent_year");
        String classNum = req.getParameter("class_num");
        String subjectCd = req.getParameter("subject_cd");
        String studentNo = req.getParameter("student_no");

        TestDao tDao = new TestDao();
        List<TestScore> list = null;

        if (studentNo != null && !studentNo.isEmpty()) {
            Student targetStudent = stDao.get(studentNo);

            if (targetStudent != null) {
                list = tDao.filter(targetStudent);
            } else {
                req.setAttribute("error", "該当する学生が見つかりませんでした。");
            }

        } else if (entYearStr != null && !entYearStr.isEmpty()
                && classNum != null && !classNum.isEmpty()
                && subjectCd != null && !subjectCd.isEmpty()) {

            int entYear = Integer.parseInt(entYearStr);
            Subject subject = sdao.get(school, subjectCd);

            if (subject != null) {
                list = tDao.filter(entYear, classNum, subject, 1, school);
            } else {
                req.setAttribute("error", "科目情報が見つかりませんでした。");
            }
        }

        if (list != null) {
            req.setAttribute("tests", list);

            if (list.isEmpty()) {
                req.setAttribute("error", "該当する成績データが見つかりませんでした。");
            }
        }

        return "score_search.jsp";
    }
}