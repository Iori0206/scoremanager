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

        // 入学年度一覧の取得
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

        // 入学年度の型変換
        int entYear = 0;
        if (entYearStr != null && !entYearStr.isEmpty() && !"0".equals(entYearStr)) {
            entYear = Integer.parseInt(entYearStr);
        }

        // クラスが未選択("0")の場合は null に変換して、DAO側で条件から外せるようにする
        if ("0".equals(classNum) || "".equals(classNum)) {
            classNum = null;
        }

        // JSPでの選択状態維持用
        req.setAttribute("f1", entYear);
        req.setAttribute("f2", (classNum == null) ? "0" : classNum);
        req.setAttribute("f3", subjectCd);
        req.setAttribute("f4", studentNo);

        // --- 3. 検索実行処理 ---

        // A. 学生番号が入力されている場合（最優先）
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
        // B. 入学年度・クラス・科目による検索の場合
        else {
            // ボタンが押された（リクエストパラメータが何かしら存在する）ときのみ検索を実行する
            // ※初期表示でいきなりエラーを出さないための判定
            if (entYearStr != null || classNum != null || subjectCd != null) {
                
                // 成績管理の仕様上、科目の選択が必須の場合はここでチェックする
                if (subjectCd == null || subjectCd.isEmpty() || "0".equals(subjectCd)) {
                    req.setAttribute("error", "科目を選択してください。");
                } else {
                    Subject subject = sDao.get(school, subjectCd);
                    if (subject != null) {
                        // entYearが0（未選択）、classNumがnullでもそのままDAOに引き渡して動的検索する
                        List<TestScore> list = dao.filter(entYear, classNum, subject, 0, school); 
                        
                        if (list != null && !list.isEmpty()) {
                            req.setAttribute("tests", list);
                        } else {
                            req.setAttribute("error", "成績情報が存在しませんでした。");
                        }
                    }
                }
            }
        }

        return "score_search.jsp"; // 成績検索用のJSP名（環境に合わせて微調整してください）
    }
}