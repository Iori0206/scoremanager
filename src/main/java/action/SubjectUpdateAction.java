package action;

import bean.Subject; // 修正：javax.security...ではなく自作のBeanをインポート
import bean.Teacher;
import dao.SubjectDao; // 修正：SubjectDAQ ではなく SubjectDao
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectUpdateAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // セッションから教員情報を取得
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        if (teacher == null) return "login.jsp";

        // URLパラメータから科目コードを取得
        String cd = request.getParameter("cd");

        // DAOを生成して科目情報を取得
        SubjectDao sDao = new SubjectDao();
        // 前に修正した SubjectDao.get(cd, school) を呼び出す
        Subject subject = sDao.get(cd, teacher.getSchool());

        // リクエスト属性にセット
        request.setAttribute("subject", subject);

        return "subject_update.jsp";
    }
}