package action;

import bean.Student;
import bean.Teacher;
import dao.StudentDao; // これを必ず追加してください
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentInsertAction extends Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // 1. セッションと教員情報の取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ログインチェック
        if (teacher == null) {
            return "login.jsp";
        }

        // GETリクエストの場合は登録画面を表示して終了
        if (req.getMethod().equals("GET")) {
            return "student_insert.jsp";
        }

        // 2. POSTリクエスト時の登録処理
        req.setCharacterEncoding("UTF-8");

        // リクエストパラメータの取得
        String no = req.getParameter("no");
        String name = req.getParameter("name");
        int entYear = Integer.parseInt(req.getParameter("ent_year"));
        String classNum = req.getParameter("class_num");
        // チェックボックスがONならtrue
        boolean isAttend = req.getParameter("is_attend") != null;

        // 3. Studentオブジェクトの作成
        Student s = new Student();
        s.setNo(no);
        s.setName(name);
        s.setEntYear(entYear);
        s.setClassNum(classNum);
        s.setAttend(isAttend);
        // ログイン中の先生の学校をセット
        s.setSchool(teacher.getSchool());

        // 4. DAOで保存実行
        StudentDao dao = new StudentDao();
        dao.save(s);

        // 登録後は一覧画面へリダイレクト（Actionを呼び出す）
        return "StudentList.action";
    }
}