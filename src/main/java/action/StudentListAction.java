package action;

import java.util.List;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentListAction extends Action {

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

        String entYearStr = request.getParameter("ent_year");
        String classNum = request.getParameter("class_num");
        String isAttendStr = request.getParameter("is_attend");

        int entYear = 0;
        boolean isAttend = false;

        if (entYearStr != null && !entYearStr.isEmpty()) {
            try {
                entYear = Integer.parseInt(entYearStr);
            } catch (NumberFormatException e) {
                entYear = 0;
            }
        }

        if ("1".equals(isAttendStr)) {
            isAttend = true;
        }

        StudentDao studentDao = new StudentDao();
        List<Student> students = studentDao.filter(teacher.getSchool(), entYear, classNum, isAttend);

        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classNumList = classNumDao.filter(teacher.getSchool());

        request.setAttribute("students", students);
        request.setAttribute("class_num_list", classNumList);
        request.setAttribute("ent_year", entYear);
        request.setAttribute("class_num", classNum);
        request.setAttribute("is_attend", isAttendStr);

        return "student_list.jsp";
    }
}