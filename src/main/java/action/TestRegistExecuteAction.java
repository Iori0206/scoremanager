package action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestScore;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            return "login.jsp";
        }

        School school = teacher.getSchool();
        if (school == null) {
            school = new School();
            school.setCd(teacher.getSchoolCd());
            teacher.setSchool(school);
        }

        String f1 = request.getParameter("f1");
        String f2 = request.getParameter("f2");
        String f3 = request.getParameter("f3");
        String f4 = request.getParameter("f4");

        int entYear = Integer.parseInt(f1);
        int num = Integer.parseInt(f4);

        SubjectDao subjectDao = new SubjectDao();
        Subject subject = subjectDao.get(school, f3);

        StudentDao studentDao = new StudentDao();

        List<TestScore> testList = new ArrayList<>();
        Map<String, String> pointErrors = new HashMap<>();

        // 画面に出ていた学生番号をそのまま受け取る
        String[] studentNoList = request.getParameterValues("studentNoList");

        if (studentNoList != null) {
            for (String studentNo : studentNoList) {
                Student student = studentDao.get(studentNo);
                String pointStr = request.getParameter("point_" + studentNo);

                TestScore ts = new TestScore();
                ts.setStudent(student);
                ts.setSubject(subject);
                ts.setSchool(school);
                ts.setNum(num);

                if (pointStr == null || pointStr.isEmpty()) {
                    ts.setPoint(-1);
                } else {
                    try {
                        int point = Integer.parseInt(pointStr);

                        if (point < 0 || point > 100) {
                            pointErrors.put(studentNo, "0～100の範囲で入力してください");
                            ts.setPoint(-1);
                        } else {
                            ts.setPoint(point);
                        }
                    } catch (NumberFormatException e) {
                        pointErrors.put(studentNo, "整数で入力してください");
                        ts.setPoint(-1);
                    }
                }

                testList.add(ts);
            }
        }

        if (!pointErrors.isEmpty()) {
            ClassNumDao classNumDao = new ClassNumDao();
            List<String> classNumList = classNumDao.filter(school);

            List<Integer> entYearList = new ArrayList<>();
            for (int y = 2020; y <= 2030; y++) {
                entYearList.add(y);
            }

            TestDao testDao = new TestDao();
            List<TestScore> tests = testDao.filter(entYear, f2, subject, num, school);

            request.setAttribute("subjects", subjectDao.filter(school));
            request.setAttribute("class_num_list", classNumList);
            request.setAttribute("ent_year_list", entYearList);

            request.setAttribute("tests", tests);
            request.setAttribute("subject", subject);
            request.setAttribute("subject_cd", f3);
            request.setAttribute("ent_year", entYear);
            request.setAttribute("class_num", f2);
            request.setAttribute("num", num);
            request.setAttribute("pointErrors", pointErrors);

            return "test_regist.jsp";
        }

        TestDao testDao = new TestDao();
        testDao.save(testList);

        request.setAttribute("subject", subject);
        request.setAttribute("ent_year", entYear);
        request.setAttribute("class_num", f2);
        request.setAttribute("num", num);

        return "test_regist_done.jsp";
    }
}