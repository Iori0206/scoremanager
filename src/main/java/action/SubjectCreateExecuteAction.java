package action; 

import javax.security.auth.Subject;

import bean.Teacher;
import dao.SubjectDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {
    @Override
    
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        String cd = request.getParameter("cd");
        String name = request.getParameter("name");

        SubjectDAO sDao = new SubjectDAO();
        
        
        if (sDao.get(cd, teacher.getSchool()) == null) {
            
            Subject subject = new Subject();
            subject.setCd(cd);
            subject.setName(name);
            subject.setSchool(teacher.getSchool());
            sDao.save(subject);
            
            
            response.sendRedirect("SubjectList.action");
            return null; 
        } else {
            
            request.setAttribute("errors", "科目コードが重複しています");
            
            
            return "subject_create.jsp";
        }
    }
}