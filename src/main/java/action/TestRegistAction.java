package action;

import java.util.ArrayList;
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

public class TestRegistAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 1. セッション取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 未ログイン対策
        if (teacher == null) {
            return "login.jsp";
        }

        
        School school = teacher.getSchool();
        if (school == null) {
            school = new School();
            school.setCd("tes"); 
            teacher.setSchool(school);
        }

        // 2. 検索パラメータ取得
        String entYearStr = request.getParameter("f1");
        String classNum   = request.getParameter("f2");
        String subjectCd  = request.getParameter("f3");
        String numStr     = request.getParameter("f4");

        int entYear = 0;
        int num = 0;

        try {
            if (entYearStr != null && !entYearStr.isEmpty() && !entYearStr.equals("0")) {
                entYear = Integer.parseInt(entYearStr);
            }
            if (numStr != null && !numStr.isEmpty() && !numStr.equals("0")) {
                num = Integer.parseInt(numStr);
            }
        } catch (NumberFormatException e) {
            System.out.println("数値変換エラー: " + e.getMessage());
        }

        //  DAOの初期化
        SubjectDao sDao = new SubjectDao();
        TestDao tDao = new TestDao();
        StudentDao stDao = new StudentDao();

        
        List<Integer> entYearList = stDao.filterEntYear(school);
        List<String> classNumList = stDao.filterClassNum(school);
        List<Subject> subjects = sDao.filter(school);

        request.setAttribute("ent_year_list", entYearList);
        request.setAttribute("class_num_list", classNumList);
        request.setAttribute("subjects", subjects); 

        //  選択された科目情報の取得
        Subject subject = null;
        if (subjectCd != null && !subjectCd.isEmpty() && !subjectCd.equals("---")) {
            subject = sDao.get(school, subjectCd);
        }

        //  学生情報（成績）の一覧取得
        List<TestScore> tests = new ArrayList<>();

        if (entYear != 0 
                && classNum != null && !classNum.isEmpty() && !classNum.equals("---")
                && subject != null
                && num != 0) {
            tests = tDao.filter(entYear, classNum, subject, num, school);
        }

        //  JSPへ値をセット
        request.setAttribute("ent_year", entYear);
        request.setAttribute("class_num", classNum);
        request.setAttribute("subject_cd", subjectCd);
        request.setAttribute("num", num);
        request.setAttribute("subject", subject);
        request.setAttribute("tests", tests); 

        return "test_regist.jsp";
    }
}