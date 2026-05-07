package action;

import java.util.List;

import bean.Subject;
import bean.Teacher;
import bean.TestScore;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        if (teacher == null) return "login.jsp";

        // パラメータ取得
        int entYear = Integer.parseInt(request.getParameter("f1")); // 入学年度
        String classNum = request.getParameter("f2");               // クラス
        String subjectCd = request.getParameter("f3");              // 科目コード

        // DAOの生成
        SubjectDao sDao = new SubjectDao();
        TestDao tDao = new TestDao();

        // 科目情報を取得
        Subject subject = sDao.get(subjectCd, teacher.getSchool());

        // 科目別成績一覧を取得
        // 第4引数に「回数(0など)」を入れることで TestDao のメソッドと一致させます
        List<TestScore> tests = tDao.filter(entYear, classNum, subject, 0, teacher.getSchool());

        request.setAttribute("tests", tests);

        return "test_list_subject.jsp";
    }
}