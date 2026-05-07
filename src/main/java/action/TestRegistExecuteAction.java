package action;

import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Test;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestRegistExecuteAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        String[] studentNoSet = request.getParameterValues("student_no_set[]");
        String[] pointSet = request.getParameterValues("point_set[]");
        
        
        List<Test> testList = new ArrayList<>();
        
        if (studentNoSet != null && pointSet != null) {
            for (int i = 0; i < studentNoSet.length; i++) {
                Test test = new Test();
                
                // 学生番号をセット
                Student student = new Student();
                student.setNo(studentNoSet[i]);
                test.setStudent(student);
                
                
                try {
                    int point = Integer.parseInt(pointSet[i]);
                    test.setPoint(point);
                } catch (NumberFormatException e) {
                    
                    continue; 
                }
                
                // リストに追加
                testList.add(test);
            }
        }

        // 3. DAOを使って一括保存
        if (!testList.isEmpty()) {
            TestDao tDao = new TestDao();
            tDao.save(testList); 
        }

        // 4. 完了画面へリダイレクト
        response.sendRedirect("test_regist_done.jsp");
        return null;
    }
}