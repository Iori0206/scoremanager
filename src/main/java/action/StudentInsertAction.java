package action;

import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentInsertAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            return "login.jsp";
        }

        School school = teacher.getSchool();
        if (school == null) {
            school = new School();
            school.setCd(teacher.getSchoolCd());
            teacher.setSchool(school);
        }

        // クラス一覧
        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classNumList = classNumDao.filter(school);
        request.setAttribute("class_num_list", classNumList);

        // 入学年度一覧
        List<Integer> entYearList = new ArrayList<>();
        for (int y = 2020; y <= 2030; y++) {
            entYearList.add(y);
        }
        request.setAttribute("ent_year_list", entYearList);

        // GETなら入力画面表示
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            return "student_insert.jsp";
        }

        String entYearStr = request.getParameter("ent_year");
        String no = request.getParameter("no");
        String name = request.getParameter("name");
        String classNum = request.getParameter("class_num");

        // 入力値保持
        request.setAttribute("ent_year", entYearStr);
        request.setAttribute("no", no);
        request.setAttribute("name", name);
        request.setAttribute("class_num", classNum);

        boolean hasError = false;

        // エラーメッセージ
        if (entYearStr == null || entYearStr.isEmpty() || "0".equals(entYearStr)) {
            request.setAttribute("entYearError", "入学年度を選択してください");
            hasError = true;
        }

        if (no == null || no.isEmpty()) {
            request.setAttribute("noError", "学生番号を入力してください");
            hasError = true;
        }

        if (name == null || name.isEmpty()) {
            request.setAttribute("nameError", "氏名を入力してください");
            hasError = true;
        }

        if (classNum == null || classNum.isEmpty() || "---".equals(classNum)) {
            request.setAttribute("classNumError", "クラスを選択してください");
            hasError = true;
        }

        StudentDao studentDao = new StudentDao();

        // 学生番号重複チェック
        if (!hasError && studentDao.get(no) != null) {
            request.setAttribute("noError", "学生番号が重複しています");
            hasError = true;
        }

        if (hasError) {
            return "student_insert.jsp";
        }

        int entYear = Integer.parseInt(entYearStr);

        Student student = new Student();
        student.setNo(no);
        student.setName(name);
        student.setEntYear(entYear);
        student.setClassNum(classNum);
        student.setAttend(true);
        student.setSchool(school);

        studentDao.save(student);

        return "student_insert_done.jsp";
    }
}