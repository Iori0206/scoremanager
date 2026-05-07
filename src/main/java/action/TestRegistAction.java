package action;

import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.TestScore;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 1. セッション取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 未ログイン対策
        if (teacher == null) {
            return "login.jsp";
        }

        // 学校情報の補完（DB内の'tes'データと一致させるため）
        if (teacher.getSchool() == null) {
            School school = new School();
            school.setCd("tes"); 
            teacher.setSchool(school);
        }

        // 2. 検索パラメータ取得
        String entYearStr = request.getParameter("f1"); // 入学年度
        String classNum   = request.getParameter("f2"); // クラス番号
        String subjectCd  = request.getParameter("f3"); // 科目コード
        String numStr     = request.getParameter("f4"); // 回数

        int entYear = 0;
        int num = 0;

        // 数値変換処理
        try {
            if (entYearStr != null && !entYearStr.isEmpty()) {
                entYear = Integer.parseInt(entYearStr);
            }
            if (numStr != null && !numStr.isEmpty()) {
                num = Integer.parseInt(numStr);
            }
        } catch (NumberFormatException e) {
            System.out.println("数値変換エラー: " + e.getMessage());
        }

        // 3. DAOの初期化
        SubjectDao sDao = new SubjectDao();
        TestDao tDao = new TestDao();

        // --- 追加：科目一覧を取得（プルダウン用） ---
        List<Subject> subjects = sDao.filter(teacher.getSchool());
        request.setAttribute("subjects", subjects); 

        // 選択された科目情報の取得
        Subject subject = null;
        if (subjectCd != null && !subjectCd.isEmpty()) {
            subject = sDao.get(subjectCd, teacher.getSchool());
        }

        // 4. 学生情報（成績）の一覧取得
        List<TestScore> tests = new ArrayList<>();

        // 検索条件がすべて揃っている場合のみ、学生リストを取得
        if (entYear != 0 
                && classNum != null && !classNum.isEmpty() && !classNum.equals("------")
                && subject != null
                && num != 0) {

            tests = tDao.filter(entYear, classNum, subject, num, teacher.getSchool());
        }

        // 5. JSPへ結果を転送
        request.setAttribute("ent_year", entYear);
        request.setAttribute("class_num", classNum);
        request.setAttribute("subject_cd", subjectCd);
        request.setAttribute("num", num);

        request.setAttribute("subject", subject);
        request.setAttribute("tests", tests); 

        // 6. 画面表示
        return "test_regist.jsp";
    }
}