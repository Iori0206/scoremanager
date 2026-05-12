package action;

import java.util.List;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            return "login.jsp";
        }

        if (teacher.getSchool() == null) {
            School school = new School();
            school.setCd(teacher.getSchoolCd());
            teacher.setSchool(school);
        }

        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjectList = subjectDao.filter(teacher.getSchool());

        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classNumList = classNumDao.filter(teacher.getSchool());

        request.setAttribute("subject_list", subjectList);
        request.setAttribute("class_num_list", classNumList);

        return "test_regist.jsp";
    }
}