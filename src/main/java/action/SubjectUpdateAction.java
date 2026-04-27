package action;


import javax.security.auth.Subject;

import bean.Teacher;
import dao.SubjectDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectUpdateAction extends Action {
    @Override
    
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        String cd = request.getParameter("cd");

        
        SubjectDAO sDao = new SubjectDAO();
        Subject subject = sDao.get(cd, teacher.getSchool());
        request.setAttribute("subject", subject);

        
        return "subject_update.jsp";
    }
}