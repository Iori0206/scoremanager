package action;

import java.util.List;

import bean.School;
import bean.Student;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListStudentExecuteAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        // ログインチェック
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        if (teacher == null) return "login.jsp";

        
        School school = teacher.getSchool();
        if (school == null) {
            school = new School();
            school.setCd("tes"); 
            teacher.setSchool(school);
        }

        String studentNo = request.getParameter("f4");

        
        StudentDao sDao = new StudentDao();
        TestDao tDao = new TestDao();
        SubjectDao subDao = new SubjectDao(); // 検索プルダウン用

       
        request.setAttribute("ent_year_list", sDao.filterEntYear(school));
        request.setAttribute("class_num_list", sDao.filterClassNum(school));
        request.setAttribute("subjects", subDao.filter(school));

        // 学生情報と成績の取得
        Student student = sDao.get(studentNo);

        if (student != null) {
            // 学生別成績一覧を取得
            
            List<Test> tests = tDao.filter(student);

            request.setAttribute("student", student);
            request.setAttribute("tests", tests);
            request.setAttribute("f4", studentNo); // 検索値を保持
        } else {
            // 学生が見つからない場合の処理
            request.setAttribute("error", "学生情報が見つかりませんでした");
        }

        // 6. 結果を表示するJSPへ
        return "test_list_student.jsp";
    }
}