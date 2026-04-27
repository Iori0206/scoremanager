package action;

import java.util.List;

import bean.School;
import bean.Student;
import bean.Teacher;
import bean.Test;
import dao.StudentDAO;
import dao.TestDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

/**
 * 成績参照（学生別）実行アクションクラス
 */
public class TestListStudentExecuteAction extends Action {

    @Override
    
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        
        String studentNo = request.getParameter("f4");

        StudentDAO sDao = new StudentDAO();
        TestDAO tDao = new TestDAO();

        
        Student student = sDao.get(studentNo);

        
        if (student != null) {
            
            List<Test> tests = tDao.filter(student);
            
            
            request.setAttribute("student", student);
            request.setAttribute("tests", tests);
        } else {
            
        	request.setAttribute("errors", "学生情報が見つかりませんでした");
        }

        
        return "test_list_student.jsp";
    }
}