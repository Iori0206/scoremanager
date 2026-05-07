package action;

import java.util.List;

import bean.School; // 追加
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
        
        // ログインチェック
        if (teacher == null) return "login.jsp";

        // --- NULL回避処理：学校情報がない場合に暫定セット ---
        if (teacher.getSchool() == null) {
            School school = new School();
            // H2コンソールのデータに合わせて "tes" をセット
            school.setCd("tes"); 
            teacher.setSchool(school);
        }
        // ----------------------------------------------

        // 1. パラメータの取得
        String entYearStr = request.getParameter("ent_year"); 
        String classNum = request.getParameter("class_num");   
        String isAttendStr = request.getParameter("is_attend"); 

        int entYear = 0;
        boolean isAttend = false;

        // 2. 型の変換とバリデーション
        if (entYearStr != null && !entYearStr.isEmpty()) {
            try {
                entYear = Integer.parseInt(entYearStr);
            } catch (NumberFormatException e) {
                entYear = 0;
            }
        }
        if (isAttendStr != null && isAttendStr.equals("1")) {
            isAttend = true;
        }

        // 3. DAOの実行
        StudentDao sDao = new StudentDao();
        // teacher.getSchool() が確実に存在するので getCd() で落ちなくなります
        List<Student> students = sDao.filter(teacher.getSchool(), entYear, classNum, isAttend);

        // 4. JSP側で選択状態を保持するためのセット
        request.setAttribute("ent_year", entYear);
        request.setAttribute("class_num", classNum);
        request.setAttribute("is_attend", isAttendStr);
        request.setAttribute("students", students);
        
        return "student_list.jsp";
    }
}