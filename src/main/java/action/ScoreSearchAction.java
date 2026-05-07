package action;

import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ScoreSearchAction extends Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        // 修正：school ではなく user(Teacher) から取得
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ログインチェック
        if (teacher == null) {
            return "login.jsp";
        }
        
        School school = teacher.getSchool();

        // 1. 年度一覧（2020年から10年分）
        List<String> years = new ArrayList<>();
        for (int i = 2020; i <= 2030; i++) {
            years.add(String.valueOf(i));
        }
        req.setAttribute("years", years);

        // 2. クラス一覧
        ClassNumDao cdao = new ClassNumDao();
        req.setAttribute("classes", cdao.filter(school));

        // 3. 科目一覧
        SubjectDao sdao = new SubjectDao();
        // 修正：SubjectDao側のfilterメソッドと引数を合わせる
        req.setAttribute("subjects", sdao.filter(school));

        return "score_search.jsp";
    }
}