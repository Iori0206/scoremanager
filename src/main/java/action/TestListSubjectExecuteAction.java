package action;

import java.util.List;

import bean.School;
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
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // セッションとユーザー情報の取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        if (teacher == null) return "login.jsp";

        
        School school = teacher.getSchool();
        if (school == null) {
            school = new School();
            school.setCd("tes"); // デフォルトの学校コードをセット
            teacher.setSchool(school);
        }

        
        String entYearStr = request.getParameter("f1");
        String classNum = request.getParameter("f2");
        String subjectCd = request.getParameter("f3");

        int entYear = 0;
        if (entYearStr != null && !entYearStr.isEmpty()) {
            entYear = Integer.parseInt(entYearStr);
        }

        //  DAOの生成
        StudentDao stDao = new StudentDao();
        SubjectDao sDao = new SubjectDao();
        TestDao tDao = new TestDao();

        
        request.setAttribute("ent_year_list", stDao.filterEntYear(school));
        request.setAttribute("class_num_list", stDao.filterClassNum(school));
        request.setAttribute("subjects", sDao.filter(school));

        //  データの取得
        Subject subject = sDao.get(school, subjectCd); // 引数順を(school, cd)に修正

        // 科目別成績一覧を取得
        // 回数は 0 または 1 など、運用に合わせて指定
        List<TestScore> tests = tDao.filter(entYear, classNum, subject, 0, school);

        // 6. リクエスト属性のセット
        request.setAttribute("tests", tests);
        request.setAttribute("ent_year", entYear);
        request.setAttribute("class_num", classNum);
        request.setAttribute("subject_cd", subjectCd);
        request.setAttribute("subject", subject); // 科目名表示用

        return "test_list_subject.jsp";
    }
}