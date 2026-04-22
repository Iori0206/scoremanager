package action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class LogoutAction extends Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッション破棄
        req.getSession().invalidate();

        // logout.jsp に遷移
        return "logout.jsp";
    }
}