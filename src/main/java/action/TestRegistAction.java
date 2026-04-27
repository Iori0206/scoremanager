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

/**
 * 成績登録画面：検索・表示アクション
 */
public class TestRegistAction extends Action {
    @Override
    
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");

        // 1. リクエストパラメータの取得
        String entYearStr = request.getParameter("f1"); // 入学年度
        String classNum = request.getParameter("f2");  // クラス
        String subjectCd = request.getParameter("f3"); // 科目コード
        String numStr = request.getParameter("f4");    // 回数

        SubjectDAO sDao = new SubjectDAO();
        TestDAO tDao = new TestDAO();

        
        if (entYearStr != null && subjectCd != null && numStr != null) {
            int entYear = Integer.parseInt(entYearStr);
            int num = Integer.parseInt(numStr);
            
            // 科目情報を取得
            // (importをbean.Subjectに直すことで、型が一致します)
            Subject subject = sDao.get(subjectCd, teacher.getSchool());
            
            // 2. 登録対象の学生リストを取得
            List<Test> tests = tDao.filter(entYear, classNum, subject, num, teacher.getSchool());
            
            // 3. 結果をセット
            request.setAttribute("tests", tests);
        }

        
        return "test_regist.jsp";
    }
}