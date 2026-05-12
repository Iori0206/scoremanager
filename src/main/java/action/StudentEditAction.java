package action;

import bean.Student;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentEditAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String no = request.getParameter("no");

        StudentDao dao = new StudentDao();
        Student student = dao.get(no);

        request.setAttribute("student", student);

        return "student_edit.jsp";
    }
}