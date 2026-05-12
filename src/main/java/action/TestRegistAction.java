package action;

import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.TestScore;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            return "login.jsp";
        }

        if (teacher.getSchool() == null) {
            School school = new School();
            school.setCd(teacher.getSchoolCd());
            teacher.setSchool(school);
        }

        School school = teacher.getSchool();

        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjects = subjectDao.filter(school);

        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classNumList = classNumDao.filter(school);

        List<Integer> entYearList = new ArrayList<>();
        for (int y = 2020; y <= 2030; y++) {
            entYearList.add(y);
        }

        request.setAttribute("subjects", subjects);
        request.setAttribute("class_num_list", classNumList);
        request.setAttribute("ent_year_list", entYearList);

        String f1 = request.getParameter("f1");
        String f2 = request.getParameter("f2");
        String f3 = request.getParameter("f3");
        String f4 = request.getParameter("f4");

        int entYear = 0;
        int num = 0;

        if (f1 != null && !f1.isEmpty() && !"0".equals(f1)) {
            entYear = Integer.parseInt(f1);
        }
        if (f4 != null && !f4.isEmpty() && !"0".equals(f4)) {
            num = Integer.parseInt(f4);
        }

        request.setAttribute("ent_year", entYear);
        request.setAttribute("class_num", f2);
        request.setAttribute("subject_cd", f3);
        request.setAttribute("num", num);

        if (entYear != 0
                && f2 != null && !f2.isEmpty() && !"---".equals(f2)
                && f3 != null && !f3.isEmpty() && !"---".equals(f3)
                && num != 0) {

            Subject subject = subjectDao.get(school, f3);
            request.setAttribute("subject", subject);

            if (subject != null) {
                TestDao testDao = new TestDao();
                List<TestScore> tests = testDao.filter(entYear, f2, subject, num, school);
                request.setAttribute("tests", tests);
            }
        }

        return "test_regist.jsp";
    }
}