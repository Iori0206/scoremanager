package action;

import java.util.List;

import bean.School;
import bean.Subject;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectListAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("UTF-8");

        School school = new School();
        school.setCd("tes");

        SubjectDao dao = new SubjectDao();
        List<Subject> list = dao.filter(school);

        request.setAttribute("subjects", list);

        return "subject_list.jsp";
    }
}