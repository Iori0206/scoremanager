package action;

import java.util.List;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentListAction extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 1. ログインチェック
        if (teacher == null) {
            return "login.jsp";
        }

        // 2. 学校情報取得
        School school = teacher.getSchool();

        // ※DBのSTUDENTテーブルに登録されている「school_cd」に合わせて書き換えてください
        if (school == null) {
            school = new School();
            school.setCd("tes");
            teacher.setSchool(school);
        }

        // 3. パラメータ取得
        String entYearStr = request.getParameter("ent_year");
        String classNum = request.getParameter("class_num");
        String isAttendStr = request.getParameter("is_attend");

        // 4. 入学年度の型変換
        int entYear = 0;
        if (entYearStr != null && !entYearStr.isEmpty()) {
            try {
                entYear = Integer.parseInt(entYearStr);
            } catch (NumberFormatException e) {
                entYear = 0;
            }
        }

        // 5. 在学フラグの処理（初回表示はデフォルトでチェックを入れる）
        boolean isAttend = false;
        if (isAttendStr == null) {
            isAttend = true;
            isAttendStr = "1";
        } else if (isAttendStr.equals("1")) {
            isAttend = true;
        }

        // 6. DAO呼び出し
        StudentDao sDao = new StudentDao();

        // 学生一覧・プルダウン用リストの取得
        List<Student> students = sDao.filter(school, entYear, classNum, isAttend);
        List<Integer> entYearList = sDao.filterEntYear(school);
        List<String> classNumList = sDao.filterClassNum(school);

        // 7. JSPへリクエスト属性をセット
        request.setAttribute("students", students);
        request.setAttribute("ent_year_list", entYearList);
        request.setAttribute("class_num_list", classNumList);

        // 検索状態を保持
        request.setAttribute("ent_year", entYear);
        request.setAttribute("class_num", classNum);
        request.setAttribute("is_attend", isAttendStr);

        return "student_list.jsp";
    }
}