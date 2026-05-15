package action;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            return "login.jsp";
        }

        String cd = request.getParameter("cd");
        String name = request.getParameter("name");

        if (cd == null) {
            cd = "";
        }
        if (name == null) {
            name = "";
        }

        cd = cd.trim();
        name = name.trim();

        request.setAttribute("cd", cd);
        request.setAttribute("name", name);

        School school = teacher.getSchool();
        if (school == null) {
            school = new School();
            school.setCd(teacher.getSchoolCd());
            teacher.setSchool(school);
        }

        SubjectDao dao = new SubjectDao();
        Subject current = dao.get(school, cd);

        if (current == null) {
            request.setAttribute("notFoundError", "科目が存在していません");
            return "subject_update.jsp";
        }

        if (name.isEmpty()) {
            request.setAttribute("nameError", "科目名を入力してください");
            return "subject_update.jsp";
        }

        Subject subject = new Subject();
        subject.setSchool(school);
        subject.setCd(cd);
        subject.setName(name);

        dao.update(subject);

        return "subject_update_done.jsp";
    }
}