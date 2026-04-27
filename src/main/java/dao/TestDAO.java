package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import bean.School;
import bean.Student;
import bean.Test;

public class TestDAO extends DAO {

    public Test get(Student student, Subject subject, int no) throws Exception {
        
    }

    public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        String sql = "select s.no as student_no, s.name as student_name, s.ent_year, s.class_num, "
                   + "t.point, t.no as test_no "
                   + "from student s "
                   + "left join test t on s.no = t.student_no and t.subject_cd = ? and t.no = ? "
                   + "where s.ent_year = ? and s.class_num = ? and s.school_cd = ? "
                   + "order by s.no asc";

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, subject.getCd());
            statement.setInt(2, num);
            statement.setInt(3, entYear);
            statement.setString(4, classNum);
            statement.setString(5, school.getCd());
            
            ResultSet rSet = statement.executeQuery();

            while (rSet.next()) {
                Test test = new Test();
                Student student = new Student();
                student.setNo(rSet.getString("student_no"));
                student.setName(rSet.getString("student_name"));
                
                test.setStudent(student);
                test.setSubject(subject); 
                test.setSchool(school);   
                
                
                int point = rSet.getInt("point");
                if (rSet.wasNull()) {
                    test.setPoint(-1); // 未受験を-1で表現
                } else {
                    test.setPoint(point);
                }
                
                test.setNo(num);
                list.add(test);
            }
        } finally {
            if (statement != null) { statement.close(); }
            if (connection != null) { connection.close(); }
        }
        return list;
    }

   
}