package action;

import java.util.List;

import bean.School;
import bean.Student;
import bean.Teacher;
import bean.TestScore;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {

        // ログインチェック
        HttpSession session = request.getSession();

        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            return "login.jsp";
        }

        // 学校情報取得
        School school = teacher.getSchool();

        if (school == null) {
            school = new School();
            school.setCd("tes");
            teacher.setSchool(school);
        }

        // 学生番号取得
        String studentNo = request.getParameter("f4");

        // DAO
        StudentDao sDao = new StudentDao();
        TestDao tDao = new TestDao();
        SubjectDao subDao = new SubjectDao();

        // 検索用リスト
        request.setAttribute(
                "ent_year_list",
                sDao.filterEntYear(school)
        );

        request.setAttribute(
                "class_num_list",
                sDao.filterClassNum(school)
        );

        request.setAttribute(
                "subjects",
                subDao.filter(school)
        );

        // 学生情報取得
        Student student = sDao.get(studentNo);

        if (student != null) {

            // 学校情報をセット
            student.setSchool(school);

            // 学生別成績一覧取得
            List<TestScore> tests = tDao.filter(student);

            request.setAttribute("student", student);
            request.setAttribute("tests", tests);

            // 検索値保持
            request.setAttribute("f4", studentNo);

        } else {

            request.setAttribute(
                    "error",
                    "学生情報が見つかりませんでした"
            );
        }

        // JSPへ
        return "test_list_student.jsp";
    }
}