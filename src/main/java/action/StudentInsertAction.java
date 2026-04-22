package action;

import bean.School;
import bean.Student;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentInsertAction extends Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // GET → フォーム表示
        if (req.getMethod().equals("GET")) {
            return "student_insert.jsp";
        }

        School school = (School) req.getSession().getAttribute("school");
        if (school == null) {
            return "login.jsp";
        }

        // POST → 登録処理
        req.setCharacterEncoding("UTF-8");

        String no = req.getParameter("no");
        String name = req.getParameter("name");
        int entYear = Integer.parseInt(req.getParameter("ent_year"));
        String classNum = req.getParameter("class_num");
        boolean isAttend = req.getParameter("is_attend") != null;

        Student s = new Student();
        s.setNo(no);
        s.setName(name);
        s.setEntYear(entYear);
        s.setClassNum(classNum);
        s.setAttend(isAttend);
        s.setSchool(school);  // ★ null ではなくなる

        StudentDao dao = new StudentDao();
        dao.save(s);

        return "StudentList.action";
    }
}