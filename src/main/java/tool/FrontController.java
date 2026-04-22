package tool;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"*.action"})
public class FrontController extends HttpServlet {

    @Override
    public void doPost(
            HttpServletRequest request, HttpServletResponse response
    ) throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        try {
            // 例: StudentList.action → StudentList
            String path = request.getServletPath().substring(1);
            String base = path.replace(".action", "");

            // ★ Action は action パッケージに置く
            String className = "action." + base + "Action";

            Action action = (Action) Class.forName(className)
                    .getDeclaredConstructor().newInstance();

            String url = action.execute(request, response);

            request.getRequestDispatcher(url).forward(request, response);

        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }

    @Override
    public void doGet(
            HttpServletRequest request, HttpServletResponse response
    ) throws ServletException, IOException {
        doPost(request, response);
    }
}