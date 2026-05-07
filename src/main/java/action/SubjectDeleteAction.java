package action;

import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectDeleteAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String cd = request.getParameter("cd");
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");

        SubjectDao sDao = new SubjectDao(); 
       
        request.setAttribute("subject", sDao.get(cd, teacher.getSchool()));

        return "subject_delete.jsp";
    }
}