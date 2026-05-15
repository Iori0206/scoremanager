package action;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

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

        boolean hasError = false;

        if (cd.isEmpty()) {
            request.setAttribute("cdError", "科目コードを入力してください");
            hasError = true;
        } else if (cd.length() != 3) {
            request.setAttribute("cdError", "科目コードは3文字で入力してください");
            hasError = true;
        }

        if (name.isEmpty()) {
            request.setAttribute("nameError", "科目名を入力してください");
            hasError = true;
        }

        School school = teacher.getSchool();
        if (school == null) {
            school = new School();
            school.setCd(teacher.getSchoolCd());
            teacher.setSchool(school);
        }

        SubjectDao dao = new SubjectDao();

        if (!cd.isEmpty() && dao.get(school, cd) != null) {
            request.setAttribute("cdError", "科目コードが重複しています");
            hasError = true;
        }

        if (hasError) {
            return "subject_create.jsp";
        }

        Subject subject = new Subject();
        subject.setSchool(school);
        subject.setCd(cd);
        subject.setName(name);

        dao.insert(subject);

        return "subject_create_done.jsp";
    }
}