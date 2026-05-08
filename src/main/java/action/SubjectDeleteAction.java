package action;

import bean.School;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectDeleteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String cd = request.getParameter("cd");

        School school = new School();
        school.setCd("tes");

        SubjectDao sDao = new SubjectDao();
        request.setAttribute("subject", sDao.get(school, cd));

        return "subject_delete.jsp";
    }
}