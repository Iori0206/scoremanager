package action;

import bean.School;
import bean.Subject;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");

        String cd = request.getParameter("cd");
        String name = request.getParameter("name");

        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);

        boolean hasError = false;

        if (cd == null || cd.isEmpty()) {
            request.setAttribute("cdError", "科目コードを入力してください");
            hasError = true;
        } else if (cd.length() != 1) {
            request.setAttribute("cdError", "今のデータに合わせるなら1文字で入力してください");
            hasError = true;
        }

        if (name == null || name.isEmpty()) {
            request.setAttribute("nameError", "科目名を入力してください");
            hasError = true;
        }

        if (hasError) {
            request.setAttribute("subject", subject);
            return "subject_create.jsp";
        }

        School school = new School();
        school.setCd("tes");
        subject.setSchool(school);

        SubjectDao dao = new SubjectDao();
        dao.insert(subject);

        return "subject_create_done.jsp";
    }
}