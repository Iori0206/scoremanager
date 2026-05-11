package action;

import bean.School;
import bean.Subject;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectUpdateAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String cd = request.getParameter("cd");

        School school = new School();
        school.setCd("tes");

        SubjectDao sDao = new SubjectDao();
        Subject subject = sDao.get(school, cd);

        if (subject == null) {
            request.setAttribute("notFoundError", "対象の科目が見つかりません");
        } else {
            request.setAttribute("subject", subject);
        }

        return "subject_update.jsp";
    }
}