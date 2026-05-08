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
        String cd = request.getParameter("cd");
        String name = request.getParameter("name");

        School school = new School();
        school.setCd("tes");

        Subject subject = new Subject();
        subject.setSchool(school);
        subject.setCd(cd);
        subject.setName(name);

        SubjectDao sDao = new SubjectDao();
        sDao.update(subject);

        return "SubjectList.action";
    }
}