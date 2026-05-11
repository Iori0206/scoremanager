package action;

import java.util.List;

import bean.School;
import bean.Student; // 学生Beanをインポート
import bean.Subject;
import bean.Teacher;
import bean.TestScore;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ScoreListAction extends Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        if (teacher == null) return "login.jsp";

        School school = teacher.getSchool();
        if (school == null) {
            school = new School();
            school.setCd("tes"); // テスト用コード
            teacher.setSchool(school);
        }

        StudentDao stDao = new StudentDao();
        SubjectDao sDao = new SubjectDao();
        TestDao dao = new TestDao();

        // --- 1. プルダウン用データの取得 ---
        List<Integer> entYearList = stDao.filterEntYear(school);
        List<String> classNumList = stDao.filterClassNum(school);
        List<Subject> subjects = sDao.filter(school);
        
        // ★追加：全学生のリストを取得（下の検索プルダウン用）
        // StudentDaoに全学生を取得する filter(school) メソッドがある前提です
        List<Student> students = stDao.filter(school);

        // --- 2. JSPの変数名に合わせてセット (${years}, ${classes}, ${students}) ---
        req.setAttribute("years", entYearList);
        req.setAttribute("classes", classNumList);
        req.setAttribute("subjects", subjects);
        req.setAttribute("students", students); // これを追加することで一覧が出ます

        // 検索パラメータ取得
        String entYearStr = req.getParameter("ent_year");
        String classNum = req.getParameter("class_num");
        String subjectCd = req.getParameter("subject_cd");
        String studentNo = req.getParameter("student_no"); // 学生番号パラメータ

        // 初回表示または条件未指定のチェック
        // (学生番号も年度も入力がない場合は、検索せずに画面を表示)
        if ((entYearStr == null || entYearStr.isEmpty()) && (studentNo == null || studentNo.isEmpty())) {
            return "score_search.jsp";
        }

        // --- 3. 検索実行処理 ---
        List<TestScore> list = null;
        
        if (studentNo != null && !studentNo.isEmpty()) {
            // 学生番号で検索する場合（DAOに専用のメソッドが必要）
            // list = dao.filterByStudentNo(studentNo, school);
        } else if (entYearStr != null && !entYearStr.isEmpty()) {
            // 年度・クラス・科目で検索する場合
            int entYear = Integer.parseInt(entYearStr);
            Subject subject = sDao.get(school, subjectCd);
            list = dao.filter(entYear, classNum, subject, 1, school);
        }

        // 検索結果の判定
        if (list == null || list.isEmpty()) {
            if (entYearStr != null || studentNo != null) {
                req.setAttribute("error", "学生情報が存在しませんでした");
            }
            return "score_search.jsp";
        }

        // 4. 結果をセットして同じ画面に表示
        req.setAttribute("tests", list); // JSPのテーブル表示用
        
        // 元の検索画面（score_search.jsp）に戻る
        return "score_search.jsp";
    }
}