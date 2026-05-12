package action;

import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestScore;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistExecuteAction extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            return "login.jsp";
        }

        School school = teacher.getSchool();

        // 1. パラメータの取得
        String subjectCd = request.getParameter("f3"); // 科目コード
        String numStr = request.getParameter("f4");    // 回数
        String[] studentNos = request.getParameterValues("student_no");

        // 2. 回数(num)の数値変換とバリデーション
        int num = 0; 
        try {
            if (numStr != null) {
                num = Integer.parseInt(numStr);
            }
        } catch (NumberFormatException e) {
            num = 0;
        }

        // 回数が0以下（未選択や不正値）の場合はエラーを返して終了
        if (num <= 0) {
            request.setAttribute("error", "回数を正しく選択してください");
            // 前の画面に戻る際、選択していた科目などを保持するためにセット
            request.setAttribute("f3", subjectCd); 
            return "test_regist.jsp";
        }

        // 3. 科目の存在チェック
        SubjectDao sDao = new SubjectDao();
        Subject subject = sDao.get(school, subjectCd);

        if (subject == null) {
            request.setAttribute("error", "科目が存在しません");
            return "test_regist.jsp";
        }

        // 4. 成績データのリスト作成
        List<TestScore> testList = new ArrayList<>();

        if (studentNos != null) {
            for (String studentNo : studentNos) {
                // 各学生の得点を取得
                String pointStr = request.getParameter("point_" + studentNo);

                // 得点が入力されていない場合はスキップ
                if (pointStr == null || pointStr.isEmpty()) {
                    continue;
                }

                int point;
                try {
                    point = Integer.parseInt(pointStr);
                } catch (NumberFormatException e) {
                    continue; // 数値以外はスキップ
                }

                // 得点範囲チェック
                if (point < 0 || point > 100) {
                    continue;
                }

                // 保存用データの作成
                TestScore test = new TestScore();
                Student student = new Student();
                student.setNo(studentNo);

                test.setStudent(student);
                test.setSubject(subject);
                test.setSchool(school);
                test.setNum(num);   // ここで確実に選択された回数をセット
                test.setPoint(point);

                testList.add(test);
            }
        }

        // 5. データベースへの保存処理
        if (!testList.isEmpty()) {
            TestDao tDao = new TestDao();
            // saveメソッド内で既存データの削除(delete)と新規登録(insert)が行われる想定
            boolean result = tDao.save(testList);

            if (!result) {
                request.setAttribute("error", "成績登録に失敗しました。データベースを確認してください。");
                return "test_regist.jsp";
            }
        } else {
            // 得点が一つも入力されていなかった場合
            request.setAttribute("error", "登録する得点が入力されていません");
            return "test_regist.jsp";
        }

        // 6. 完了画面への遷移準備
        request.setAttribute("subjectName", subject.getName());
        request.setAttribute("num", num);

        return "test_regist_done.jsp";
    }
}