package action;

import java.time.LocalDate;
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
        // 文字エンコーディングの設定
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 1. ログインチェック
        if (teacher == null) {
            return "login.jsp";
        }

        // 2. 学校情報の取得
        School school = teacher.getSchool();
        if (school == null) {
            school = new School();
            school.setCd(teacher.getSchoolCd());
            teacher.setSchool(school);
        }

        // 3. 表示用データの準備（GET/POST共通）
        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classNumList = classNumDao.filter(school); 
        request.setAttribute("class_num_list", classNumList);

        List<Integer> entYearList = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();
        for (int y = currentYear - 10; y <= currentYear + 10; y++) {
            entYearList.add(y);
        }
        request.setAttribute("ent_year_list", entYearList);

        // 4. 画面表示処理（GETリクエスト）
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            return "student_insert.jsp";
        }

        // 5. 登録処理（POSTリクエスト）
        String entYearStr = request.getParameter("ent_year");
        String no = request.getParameter("no");
        String name = request.getParameter("name");
        String classNum = request.getParameter("class_num");

        // 入力値の保持（エラー時に値を引き継ぐため）
        request.setAttribute("ent_year", entYearStr);
        request.setAttribute("no", no);
        request.setAttribute("name", name);
        request.setAttribute("class_num", classNum);

        // バリデーション
        boolean hasError = false;

        if (entYearStr == null || entYearStr.isEmpty()) {
            request.setAttribute("entYearError", "入学年度を選択してください");
            hasError = true;
        }

        if (no == null || no.isEmpty()) {
            request.setAttribute("noError", "学生番号を入力してください");
            hasError = true;
        }

        // ★氏名のチェック：未入力および30文字制限
        if (name == null || name.isEmpty()) {
            request.setAttribute("nameError", "氏名を入力してください");
            hasError = true;
        } else if (name.length() > 30) { // データベースの VARCHAR(30) に合わせた制限
            request.setAttribute("nameError", "氏名は30文字以内で入力してください");
            hasError = true;
        }

        if (classNum == null || classNum.isEmpty()) {
            request.setAttribute("classNumError", "クラスを選択してください");
            hasError = true;
        }

        StudentDao studentDao = new StudentDao();

        // 学生番号の重複チェック
        if (!hasError && studentDao.get(no) != null) {
            request.setAttribute("noError", "学生番号が重複しています");
            hasError = true;
        }

        // エラーがある場合は入力画面へ戻す
        if (hasError) {
            return "student_insert.jsp";
        }

        // Studentオブジェクトの作成と保存
        int entYear = Integer.parseInt(entYearStr);
        Student student = new Student();
        student.setNo(no);
        student.setName(name);
        student.setEntYear(entYear);
        student.setClassNum(classNum);
        student.setAttend(true); 
        student.setSchool(school);

        studentDao.save(student);

        // 完了画面へ
        return "student_insert_done.jsp";
    }
}