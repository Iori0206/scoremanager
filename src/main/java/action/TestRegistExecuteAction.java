package action;

import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestScore;
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

        // セッション取得
        HttpSession session =
                request.getSession();

        Teacher teacher =
                (Teacher) session.getAttribute("user");

        // ログインチェック
        if (teacher == null) {

            return "login.jsp";
        }

        // 学校情報
        School school =
                teacher.getSchool();

        // 検索条件取得
        String subjectCd =
                request.getParameter("f3");

        String numStr =
                request.getParameter("f4");

        int num = 0;

        try {

            num =
                    Integer.parseInt(numStr);

        } catch (Exception e) {

            num = 0;
        }

        // 学生番号一覧取得
        String[] studentNos =
                request.getParameterValues("student_no");

        // 保存用リスト
        List<TestScore> testList =
                new ArrayList<>();

        // 科目設定
        Subject subject =
                new Subject();

        subject.setCd(subjectCd);

        if (studentNos != null) {

            for (String studentNo : studentNos) {

                // 点数取得
                String pointStr =
                        request.getParameter(
                                "point_" + studentNo
                        );

                TestScore test =
                        new TestScore();

                // 学生情報
                Student student =
                        new Student();

                student.setNo(studentNo);

                test.setStudent(student);

                // 科目
                test.setSubject(subject);

                // 学校
                test.setSchool(school);

                // 回数
                test.setNum(num);

                // 点数
                if (pointStr != null &&
                        !pointStr.isEmpty()) {

                    try {

                        int point =
                                Integer.parseInt(
                                        pointStr
                                );

                        test.setPoint(point);

                    } catch (NumberFormatException e) {

                        test.setPoint(-1);
                    }

                } else {

                    test.setPoint(-1);
                }

                // リスト追加
                testList.add(test);
            }
        }

        // 保存処理
        if (!testList.isEmpty()) {

            TestDao tDao =
                    new TestDao();

            tDao.save(testList);
        }

        // 完了画面へ
        return "test_regist_done.jsp";
    }
}