package action;

import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            return "login.jsp";
        }

        if (teacher.getSchool() == null) {
            School school = new School();
            school.setCd(teacher.getSchoolCd());
            teacher.setSchool(school);
        }

        // 科目一覧
        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjects = subjectDao.filter(teacher.getSchool());

        // クラス一覧
        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classNumList = classNumDao.filter(teacher.getSchool());

        // 入学年度一覧（最低限版）
        List<Integer> entYearList = new ArrayList<>();
        for (int y = 2020; y <= 2030; y++) {
            entYearList.add(y);
        }

        // JSPが参照している名前に合わせて渡す
        request.setAttribute("subjects", subjects);
        request.setAttribute("class_num_list", classNumList);
        request.setAttribute("ent_year_list", entYearList);

        return "test_regist.jsp";
    }
}