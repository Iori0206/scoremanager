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
            // 例: StudentUpdate.action → "StudentUpdate"
            String path = request.getServletPath().substring(1);
            String base = path.replace(".action", "");

            // ★ クラス名の決定ロジックの修正
            String className;
            
            if (base.equals("StudentUpdate")) {
                // 💡 リクエストメソッド（GETかPOSTか）で切り分ける
                if (request.getMethod().equalsIgnoreCase("POST")) {
                    // 編集画面の「変更」ボタン（form method="post"）から来た場合 → 実行用
                    className = "action.StudentUpdateExecuteAction";
                } else {
                    // 一覧の「変更」リンク（GETリクエスト）から来た場合 → 表示用
                    className = "action.StudentUpdateAction";
                }
            } else {
                // それ以外のURL（StudentList, StudentInsertなど）は、後ろに "Action" を付ける
                className = "action." + base + "Action";
            }

            // 動的にクラスをロードしてインスタンス化
            Action action = (Action) Class.forName(className)
                    .getDeclaredConstructor().newInstance();

            // Actionを実行
            String url = action.execute(request, response);

            // 結果のURL（JSPなど）へフォワード
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