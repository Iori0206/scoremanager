package action;

import java.util.List;

import bean.Student;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestListStudentExecuteAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        if (teacher == null) return "login.jsp";

        String studentNo = request.getParameter("f4");

        StudentDao sDao = new StudentDao();
        TestDao tDao = new TestDao();

        // 学生情報を取得
        Student student = sDao.get(studentNo);

        if (student != null) {
            // 学生別成績一覧を取得
            // TestDao側で filter(Student) というメソッドを定義しておく必要があります
            List<Test> tests = tDao.filter(student);

            request.setAttribute("student", student);
            request.setAttribute("tests", tests);
        }

        return "test_list_student.jsp";
    }
}