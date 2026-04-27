package action;

import javax.security.auth.Subject;

import bean.Teacher;
import dao.SubjectDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {
    @Override
    
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        String cd = request.getParameter("cd");

        
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setSchool(teacher.getSchool());

        new SubjectDAO().delete(subject);

        
        response.sendRedirect("SubjectList.action");
        return null;
    }
}