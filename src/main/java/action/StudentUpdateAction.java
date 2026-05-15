package action;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao; // クラス一覧取得用
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 1. ログインチェック（念のため）
        if (teacher == null) {
            return "login.jsp";
        }

        // 2. 学校情報の取得とセット（NullPointerException対策）
        School school = teacher.getSchool();
        if (school == null) {
            school = new School();
            school.setCd(teacher.getSchoolCd());
            teacher.setSchool(school);
        }

        // 3. URLの ?no=... から学生番号を取得
        String no = request.getParameter("no");

        // 4. DAOを使ってDBから該当する学生の全データを取得
        StudentDao sDao = new StudentDao();
        Student student = sDao.get(no);

        // 5. 画面のプルダウン用リストを準備
        
        // --- クラス一覧（ClassNumDaoを使用するのが一般的） ---
        ClassNumDao cNumDao = new ClassNumDao();
        List<String> class_num_list = cNumDao.filter(school);

        // --- 入学年度一覧（今年を中心に前後10年を生成） ---
        List<Integer> ent_year_list = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();
        for (int y = currentYear - 10; y <= currentYear + 10; y++) {
            ent_year_list.add(y);
        }

        // 6. リクエスト属性にセット
        request.setAttribute("student", student);
        request.setAttribute("ent_year_list", ent_year_list);
        request.setAttribute("class_num_list", class_num_list);

        // 7. 変更画面 (student_update.jsp) を表示
        return "student_update.jsp";
    }
}