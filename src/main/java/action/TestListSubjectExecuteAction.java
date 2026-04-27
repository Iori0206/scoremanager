package action;

import java.util.List;

import javax.security.auth.Subject;

import bean.Teacher;
import bean.Test;
import dao.SubjectDAO;
import dao.TestDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {
    @Override
    // 2. 修正：戻り値を void から String に変更
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");

        // パラメータ取得
        int entYear = Integer.parseInt(request.getParameter("f1")); // 入学年度
        String classNum = request.getParameter("f2");               // クラス
        String subjectCd = request.getParameter("f3");              // 科目コード

        SubjectDAO sDao = new SubjectDAO();
        TestDAO tDao = new TestDAO();

        // 科目情報を取得
        
        Subject subject = sDao.get(subjectCd, teacher.getSchool());
        
        // 科目別成績一覧を取得
        List<Test> tests = tDao.filter(entYear, classNum, subject, teacher.getSchool());

        request.setAttribute("tests", tests);
        
        
        return "test_list_subject.jsp";
    }
}