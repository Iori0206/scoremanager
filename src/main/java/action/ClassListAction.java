package action;

import java.util.List;

import bean.School;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ClassListAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            return "login.jsp";
        }

        School school = teacher.getSchool();
        if (school == null) {
            school = new School();
            school.setCd(teacher.getSchoolCd());
            teacher.setSchool(school);
        }

        ClassNumDao dao = new ClassNumDao();
        List<String> classList = dao.filter(school);

        request.setAttribute("class_list", classList);

        return "class_list.jsp";
    }
}