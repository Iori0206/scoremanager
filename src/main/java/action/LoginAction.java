package action;

import bean.Teacher;
import dao.TeacherDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class LoginAction extends Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        if (req.getMethod().equals("GET")) {
            return "login.jsp";
        }

        String id = req.getParameter("school_cd"); 
        String password = req.getParameter("password");

        TeacherDao dao = new TeacherDao();
        Teacher teacher = dao.login(id, password);

        if (teacher == null) {
            req.setAttribute("error", "ID またはパスワードが違います");
            return "login.jsp";
        }


        HttpSession session = req.getSession();
        session.setAttribute("user", teacher); 

        return "Menu.action";
    }
}