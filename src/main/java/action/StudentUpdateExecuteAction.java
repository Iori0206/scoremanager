package action;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 1. 画面の入力値を受け取る
        String no = request.getParameter("no");
        String name = request.getParameter("name");
        int entYear = Integer.parseInt(request.getParameter("ent_year"));
        String classNum = request.getParameter("class_num");
        // チェックボックスはチェックがないと null が返るため判定が必要
        boolean isAttend = request.getParameter("is_attend") != null;

        // セッションからログインユーザー（教員）情報を取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 2. Beanにセット
        Student student = new Student();
        student.setNo(no);
        student.setName(name);
        student.setEntYear(entYear);
        student.setClassNum(classNum);
        student.setAttend(isAttend);
        // ★重要：ログインユーザーの学校情報をセット
        student.setSchool(teacher.getSchool());

        // 3. DAOを使ってDBを更新
        StudentDao sDao = new StudentDao();
        sDao.save(student);

        // 4. 完了画面 (student_update_done.jsp) へ
        return "student_update_done.jsp";
    }
}