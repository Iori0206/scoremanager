package action;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        if (teacher == null) return "login.jsp";

        String cd = request.getParameter("cd");
        String name = request.getParameter("name");
        SubjectDao sDao = new SubjectDao();

        // DAOの引数に学校情報を追加して呼び出す
        if (sDao.get(cd, teacher.getSchool()) == null) {
            Subject subject = new Subject();
            subject.setSubjectCd(cd);
            subject.setName(name);

            // 保存処理
            sDao.save(subject, teacher.getSchool());
            return "SubjectList.action";
        } else {
            request.setAttribute("errors", "科目コードが重複しています");
            return "subject_create.jsp";
        }
    }
}