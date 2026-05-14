package action;

import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestScore;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import dao.TestListStudentDAO;
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
        StudentDao stDao = new StudentDao();
        SubjectDao sDao = new SubjectDao();
        TestDao dao = new TestDao();
        ClassNumDao classNumDao = new ClassNumDao();

        // --- 1. プルダウン用データの準備 ---

        // 入学年度一覧：StudentDaoの既存メソッドを使用して、DBにある年度を取得する
        List<Integer> entYearList = stDao.filterEntYear(school);
        req.setAttribute("ent_year_list", entYearList);

        // クラス一覧の取得
        List<String> classNumList = classNumDao.filter(school);
        req.setAttribute("class_num_list", classNumList);

        // 科目一覧の取得
        List<Subject> subjectList = sDao.filter(school);
        req.setAttribute("subjects", subjectList);

        // --- 2. 検索パラメータ取得 ---

        String entYearStr = req.getParameter("f1");    // 入学年度
        String classNum = req.getParameter("f2");     // クラス
        String subjectCd = req.getParameter("f3");    // 科目
        String studentNo = req.getParameter("f4");    // 学生番号

        // 型変換用の変数
        int entYear = 0;
        if (entYearStr != null && !entYearStr.isEmpty() && !"0".equals(entYearStr)) {
            entYear = Integer.parseInt(entYearStr);
        }

        // JSPでの選択状態維持用（f1をintで渡すとJSPの比較が確実になります）
        req.setAttribute("f1", entYear);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);
        req.setAttribute("f4", studentNo);

        // --- 3. 検索実行処理 ---

        // A. 学生番号が入力されている場合（優先）
        if (studentNo != null && !studentNo.isEmpty()) {
            studentNo = studentNo.trim();
            Student student = stDao.get(studentNo);

            // 学生が存在し、かつ自校の学生であるかチェック
            if (student != null && student.getSchool().getCd().equals(school.getCd())) {
                req.setAttribute("student", student);
                TestListStudentDAO tlsDao = new TestListStudentDAO();
                List<TestScore> tests = tlsDao.filter(student);
                
                if (tests != null && !tests.isEmpty()) {
                    req.setAttribute("tests", tests); 
                } else {
                    req.setAttribute("error", "成績情報が存在しませんでした。");
                }
            } else {
                req.setAttribute("error", "学生情報が見つからないか、参照権限がありません。");
            }
        } 
        // B. 入学年度・クラス・科目が指定されている場合
        else if (entYear != 0) {
            // 科目が未選択（0またはnull）でないかチェック
            if (subjectCd == null || subjectCd.isEmpty() || "0".equals(subjectCd)) {
                req.setAttribute("error", "科目を選択してください。");
            } else {
                Subject subject = sDao.get(school, subjectCd);
                if (subject != null) {
                    // 成績一覧を取得（1回目・2回目の全データ、または仕様に合わせてフィルタ）
                    List<TestScore> list = dao.filter(entYear, classNum, subject, 0, school); // 0は「全て」などの指定を想定
                    
                    if (list != null && !list.isEmpty()) {
                        req.setAttribute("tests", list);
                    } else {
                        req.setAttribute("error", "成績情報が存在しませんでした。");
                    }
                }
            }
        }

        return "score_search.jsp";
    }
}