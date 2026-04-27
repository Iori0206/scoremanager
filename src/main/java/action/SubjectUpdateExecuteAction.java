package action;


import javax.security.auth.Subject;

import bean.Teacher;
import dao.SubjectDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {
    @Override
    
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        String cd = request.getParameter("cd");
        String name = request.getParameter("name");

        
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(teacher.getSchool());

        new SubjectDAO().save(subject); 
        
        
        response.sendRedirect("SubjectList.action");
        return null;
    }
}