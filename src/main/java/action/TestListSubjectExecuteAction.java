package action;

import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
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

public class TestListSubjectExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ログインチェック
        if (teacher == null) {
            return "login.jsp";
        }

        School school = teacher.getSchool();

        // 1. パラメータの取得
        String subjectCd = req.getParameter("subject_cd");
        String numStr = req.getParameter("num"); // 画面からの回数を受け取る
        // JSP側のname属性に合わせて取得（[]の有無を確認してください）
        String[] studentNoList = req.getParameterValues("student_no_list[]");
        String[] pointList = req.getParameterValues("point_list[]");

        // 回数のパース（デフォルトは1）
        int num = 1;
        if (numStr != null && !numStr.isEmpty()) {
            try {
                num = Integer.parseInt(numStr);
            } catch (NumberFormatException e) {
                num = 1;
            }
        }

        SubjectDao sDao = new SubjectDao();
        Subject subject = sDao.get(school, subjectCd);

        // 2. 登録用データのリストを作成
        List<TestScore> testList = new ArrayList<>();
        StudentDao stDao = new StudentDao();

        if (studentNoList != null && pointList != null) {
            for (int i = 0; i < studentNoList.length; i++) {
                TestScore ts = new TestScore();
                
                // 学生情報を取得してセット
                Student student = stDao.get(studentNoList[i]);
                ts.setStudent(student);
                
                // 【重要】科目、回数、学校をセット
                ts.setSubject(subject);
                ts.setNum(num); // これによりDBのNO列に1や2が正しく保存されます
                ts.setSchool(school);

                // 点数のセット
                try {
                    int point = Integer.parseInt(pointList[i]);
                    ts.setPoint(point);
                } catch (Exception e) {
                    ts.setPoint(-1); // 不正入力や未入力は-1として扱う
                }
                
                testList.add(ts);
            }
        }

        // 3. データベースへ保存
        // ここでエラーが出る場合は、TestDaoにsave(List<TestScore>)が定義されているか確認
        TestDao tDao = new TestDao();
        tDao.save(testList);

        // 4. 完了後の遷移
        // 検索画面に戻る際に必要な情報をリクエストスコープにセットするか、Actionへリダイレクトします
        return "ScoreSearch.action"; 
    }
}