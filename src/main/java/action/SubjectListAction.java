package action;

import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao; // 修正：SubjectDAQではなくSubjectDao
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectListAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // セッションからユーザー情報を取得
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        if (teacher == null) return "login.jsp";

        // DAOの生成（綴りに注意）
        SubjectDao sDao = new SubjectDao();
        
        // ログイン教員の学校に紐づく科目をフィルタリングして取得
        List<Subject> subjects = sDao.filter(teacher.getSchool());

        // リクエスト属性にセットしてJSPへ渡す
        request.setAttribute("subjects", subjects);

        return "subject_list.jsp";
    }
}