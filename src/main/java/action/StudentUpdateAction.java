package action;

import java.util.List;

import bean.Student;
import bean.Teacher;
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

        // 1. URLの ?no=... から学生番号を取得
        String no = request.getParameter("no");

        // 2. DAOを使ってDBから該当する学生の全データを取得
        StudentDao sDao = new StudentDao();
        Student student = sDao.get(no);

        // 3. 画面のプルダウン（入学年度・クラス）に必要なリストを取得
        // 💡 DAOのメソッド名に合わせて修正しました
        List<Integer> ent_year_list = sDao.filterEntYear(teacher.getSchool());
        List<String> class_num_list = sDao.filterClassNum(teacher.getSchool());

        // 4. JSPで表示するためにリクエスト属性にセット
        request.setAttribute("student", student);
        request.setAttribute("ent_year_list", ent_year_list);
        request.setAttribute("class_num_list", class_num_list);

        // 5. 変更画面 (student_update.jsp) を表示
        return "student_update.jsp";
    }
}