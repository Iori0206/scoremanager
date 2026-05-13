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
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            return "login.jsp";
        }

        // 学校情報の補完
        if (teacher.getSchool() == null) {
            School school = new School();
            school.setCd(teacher.getSchoolCd());
            teacher.setSchool(school);
        }

        // --- 修正ポイント1：パラメータ名を JSP(f1, f2, f3) に合わせる ---
        String entYearStr = request.getParameter("f1"); // 入学年度
        String classNum = request.getParameter("f2");  // クラス
        String isAttendStr = request.getParameter("f3"); // 在学中チェック

        int entYear = 0;
        boolean isAttend = false;

        // 入学年度の数値変換
        if (entYearStr != null && !entYearStr.isEmpty()) {
            try {
                entYear = Integer.parseInt(entYearStr);
            } catch (NumberFormatException e) {
                entYear = 0;
            }
        }

        // 在学中チェックの判定
        // チェックボックスはチェックされているとパラメータが送信されます（値は何でも良い）
        if (isAttendStr != null) {
            isAttend = true;
        }

        StudentDao studentDao = new StudentDao();

        // 1. 検索実行（絞り込み結果）
        List<Student> students = studentDao.filter(teacher.getSchool(), entYear, classNum, isAttend);

        // 2. ドロップダウン用のデータ取得
        List<Integer> entYearList = studentDao.filterEntYear(teacher.getSchool());
        List<String> classNumList = studentDao.filterClassNum(teacher.getSchool());

        // --- 修正ポイント2：JSPで選択状態を維持するための名前(f1, f2, f3)で戻す ---
        request.setAttribute("students", students);
        request.setAttribute("ent_year_list", entYearList);
        request.setAttribute("class_num_list", classNumList);
        
        // JSP側の ${f1} や ${f2} という記述に合わせてセット
        request.setAttribute("f1", entYear);
        request.setAttribute("f2", classNum);
        request.setAttribute("f3", isAttendStr); // チェック状態維持用

        return "student_list.jsp";
    }
}