package action;

import bean.ClassNum;
import bean.School;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ClassCreateExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            return "login.jsp";
        }

        String class_num = request.getParameter("class_num");

        if (class_num == null || class_num.isBlank()) {
            request.setAttribute("error", "クラス番号を入力してください。");
            return "class_create.jsp";
        }

        if (teacher.getSchool() == null) {
            School school = new School();
            school.setCd(teacher.getSchoolCd());
            teacher.setSchool(school);
        }

        ClassNumDao dao = new ClassNumDao();

        if (dao.get(class_num, teacher.getSchool()) != null) {
            request.setAttribute("error", "そのクラス番号はすでに存在します。");
            return "class_create.jsp";
        }

        ClassNum classNum = new ClassNum();
        classNum.setClass_num(class_num);
        classNum.setSchool(teacher.getSchool());

        dao.save(classNum);

        return "ClassList.action";
    }
}