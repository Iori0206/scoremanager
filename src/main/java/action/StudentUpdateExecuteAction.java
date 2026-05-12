package action;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            return "login.jsp";
        }

        String no = request.getParameter("no");
        String name = request.getParameter("name");
        String classNum = request.getParameter("class_num");
        String entYearStr = request.getParameter("ent_year");
        String isAttendStr = request.getParameter("is_attend");

        int entYear = 0;
        if (entYearStr != null && !entYearStr.isEmpty()) {
            entYear = Integer.parseInt(entYearStr);
        }

        boolean isAttend = "1".equals(isAttendStr);

        Student student = new Student();
        student.setNo(no);
        student.setName(name);
        student.setClassNum(classNum);
        student.setEntYear(entYear);
        student.setAttend(isAttend);

        School school = teacher.getSchool();
        if (school == null) {
            school = new School();
            school.setCd("tes");
        }
        student.setSchool(school);

        StudentDao dao = new StudentDao();
        dao.update(student);

        return "StudentList.action";
    }
}