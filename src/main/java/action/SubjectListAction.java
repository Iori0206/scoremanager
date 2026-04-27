package action;

import java.util.List;

import javax.security.auth.Subject;

import bean.Teacher;
import dao.SubjectDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectListAction extends Action {
    @Override
    
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        SubjectDAO sDao = new SubjectDAO();
        
        
        List<Subject> subjects = sDao.filter(teacher.getSchool());
        
        request.setAttribute("subjects", subjects);
        
        
        return "subject_list.jsp";
    }
}