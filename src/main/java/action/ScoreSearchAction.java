package action;

import java.util.List;

import bean.School;
import dao.ClassNumDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class ScoreSearchAction extends Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        School school = (School) req.getSession().getAttribute("school");
        if (school == null) return "login.jsp";

        // 年度一覧（必要なら変更OK）
        req.setAttribute("years", List.of("2020", "2021", "2022", "2023", "2024", "2025"));

        // クラス一覧
        ClassNumDao cdao = new ClassNumDao();
        req.setAttribute("classes", cdao.filter(school));

        // 科目一覧
        SubjectDao sdao = new SubjectDao();
        req.setAttribute("subjects", sdao.filter(school));

        return "score_search.jsp";
    }
}