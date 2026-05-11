package action;

import bean.ClassNum;
import bean.School;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ClassDeleteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            return "login.jsp";
        }

        String class_num = request.getParameter("class_num");

        School school = teacher.getSchool();
        if (school == null) {
            school = new School();
            school.setCd(teacher.getSchoolCd());
            teacher.setSchool(school);
        }

        ClassNumDao dao = new ClassNumDao();
        ClassNum classNum = dao.get(class_num, school);

        request.setAttribute("class_data", classNum);

        return "class_delete.jsp";
    }
}