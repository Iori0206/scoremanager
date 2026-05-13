package action;

import java.util.List;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.StudentDao; // StudentDaoのみで完結できます
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentListAction extends Action {

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

        // リクエストパラメータの取得
        String entYearStr = request.getParameter("ent_year");
        String classNum = request.getParameter("class_num");
        String isAttendStr = request.getParameter("is_attend");

        int entYear = 0;
        boolean isAttend = false;

        if (entYearStr != null && !entYearStr.isEmpty()) {
            try {
                entYear = Integer.parseInt(entYearStr);
            } catch (NumberFormatException e) {
                entYear = 0;
            }
        }

        if ("1".equals(isAttendStr)) {
            isAttend = true;
        }

        // DB操作用DAO
        StudentDao studentDao = new StudentDao();

        // 1. 検索結果（一覧）を取得
        List<Student> students = studentDao.filter(teacher.getSchool(), entYear, classNum, isAttend);

        // 2. ★【重要】ドロップダウン用の「実際に登録されている入学年度」を取得
        List<Integer> entYearList = studentDao.filterEntYear(teacher.getSchool());

        // 3. ★【重要】ドロップダウン用の「実際に登録されているクラス」を取得
        // ClassNumDao ではなく、学生データが存在するクラスのみに絞るならこちらを使います
        List<String> classNumList = studentDao.filterClassNum(teacher.getSchool());

        // JSPへ値を渡す
        request.setAttribute("students", students);
        request.setAttribute("ent_year_list", entYearList); // 入学年度のリストを渡す
        request.setAttribute("class_num_list", classNumList); // クラスのリストを渡す
        
        request.setAttribute("ent_year", entYear);
        request.setAttribute("class_num", classNum);
        request.setAttribute("is_attend", isAttendStr);

        return "student_list.jsp";
    }
}