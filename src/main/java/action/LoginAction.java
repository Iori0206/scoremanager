package action;

import bean.School;
import bean.Teacher;
import dao.TeacherDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class LoginAction extends Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        if (req.getMethod().equals("GET")) {
            return "login.jsp";
        }

        String id = req.getParameter("school_cd");   // ← login.jsp の入力名が school_cd なのでそのまま使う
        String password = req.getParameter("password");

        TeacherDao dao = new TeacherDao();
        Teacher teacher = dao.login(id, password);

        if (teacher == null) {
            req.setAttribute("error", "ID またはパスワードが違います");
            return "login.jsp";
        }

        // ★ school_cd を School に詰めて session に保存
        School school = new School();
        school.setCd(teacher.getSchoolCd());
        req.getSession().setAttribute("school", school);

        return "Menu.action";
    }
}