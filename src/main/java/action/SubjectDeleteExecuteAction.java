package action;

import bean.Subject; // 修正：自作のSubjectクラスをインポート
import bean.Teacher;
import dao.SubjectDao; // 修正：最後は小文字のo
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        if (teacher == null) return "login.jsp";

        String cd = request.getParameter("cd");

        Subject subject = new Subject();
        subject.setSubjectCd(cd); // Subject.javaのセッター名に合わせる

        SubjectDao sDao = new SubjectDao();
        // 引数に「subject」と「school」を渡すDAOの定義に合わせる
        sDao.delete(subject, teacher.getSchool());

        response.sendRedirect("SubjectList.action");
        return null;
    }
}