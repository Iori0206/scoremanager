package action;

import bean.Subject; // 修正：javax.security... ではなく自作のBean
import bean.Teacher;
import dao.SubjectDao; // 修正：SubjectDAQ ではなく SubjectDao
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // セッションから教員情報を取得
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        if (teacher == null) return "login.jsp";

        // リクエストパラメータの取得
        String cd = request.getParameter("cd");
        String name = request.getParameter("name");

        // Subjectオブジェクトの作成
        Subject subject = new Subject();
        subject.setSubjectCd(cd);
        subject.setName(name);

        // DAOの生成と保存実行
        SubjectDao sDao = new SubjectDao();
        // 先ほど作成したDAOのsave(subject, school)に合わせて呼び出す
        sDao.save(subject, teacher.getSchool());

        // 保存後は一覧へリダイレクト
        response.sendRedirect("SubjectList.action");
        return null;
    }
}