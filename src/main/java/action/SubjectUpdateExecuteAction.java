package action;

import bean.School;
import bean.Subject;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");

        String cd = request.getParameter("cd");
        String name = request.getParameter("name");

        School school = new School();
        school.setCd("tes");

        Subject subject = new Subject();
        subject.setSchool(school);
        subject.setCd(cd);
        subject.setName(name);

        if (name == null || name.isEmpty()) {
            request.setAttribute("subject", subject);
            request.setAttribute("nameError", "科目名を入力してください");
            return "subject_update.jsp";
        }

        SubjectDao sDao = new SubjectDao();
        sDao.update(subject);

        return "subject_update_done.jsp";
    }
}