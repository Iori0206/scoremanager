package action;

import java.util.List;

import bean.School;
import bean.TestScore;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class ScoreListAction extends Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        School school = (School) req.getSession().getAttribute("school");
        if (school == null) return "login.jsp";

        String entYear = req.getParameter("ent_year");
        String classNum = req.getParameter("class_num");
        String subjectCd = req.getParameter("subject_cd");

        // 入力チェック
        if (entYear.isEmpty() || classNum.isEmpty() || subjectCd.isEmpty()) {
            req.setAttribute("error", "年度・クラス・科目を選択してください");
            return "score_search.jsp";
        }

        TestDao dao = new TestDao();
        List<TestScore> list = dao.getScoreList(entYear, classNum, subjectCd, school);

        if (list.isEmpty()) {
            req.setAttribute("error", "学生情報が存在しませんでした");
            return "score_search.jsp";
        }

        req.setAttribute("list", list);
        req.setAttribute("ent_year", entYear);
        req.setAttribute("class_num", classNum);
        req.setAttribute("subject_cd", subjectCd);

        return "score_list.jsp";
    }
}