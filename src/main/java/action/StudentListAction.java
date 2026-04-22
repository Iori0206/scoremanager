package action;

import java.util.List;

import bean.Student;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentListAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("UTF-8");

        String entYear = request.getParameter("ent_year");
        String classNum = request.getParameter("class_num");
        String isAttend = request.getParameter("is_attend");

        StudentDao dao = new StudentDao();
        List<Student> list = dao.filter(entYear, classNum, isAttend);

        request.setAttribute("students", list);
        request.setAttribute("ent_year", entYear);
        request.setAttribute("class_num", classNum);
        request.setAttribute("is_attend", isAttend);

        return "student_list.jsp";
    }
}