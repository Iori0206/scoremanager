package action;

import bean.School;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ClassDeleteExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ログインチェック
        if (teacher == null) {
            return "login.jsp";
        }

        // JSPから送られてきたクラス番号（CD）を取得
        String class_num = request.getParameter("class_num");
        School school = teacher.getSchool();
        
        // 学校情報が取れない場合の補完処理
        if (school == null) {
            school = new School();
            school.setCd(teacher.getSchoolCd());
            teacher.setSchool(school);
        }

        // DAOを生成して削除を実行
        ClassNumDao dao = new ClassNumDao();
        // DAO側を修正して保存すれば、ここの赤線が消えます
        dao.delete(class_num, school);

        // 削除完了後は一覧画面へ戻る
        return "ClassList.action";
    }
}