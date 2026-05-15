package action;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            return "login.jsp";
        }

        String cd = request.getParameter("cd");

        School school = teacher.getSchool();
        if (school == null) {
            school = new School();
            school.setCd(teacher.getSchoolCd());
            teacher.setSchool(school);
        }

        SubjectDao dao = new SubjectDao();
        Subject subject = dao.get(school, cd);

        if (subject == null) {
            request.setAttribute("notFoundError", "科目が存在していません");
            request.setAttribute("cd", cd);
        } else {
            request.setAttribute("subject", subject);
        }

        return "subject_delete.jsp";
    }
}