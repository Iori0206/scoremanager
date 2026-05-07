package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListSubjectDAO extends DAO {

    public List<TestListStudent> filter(Student student) throws Exception {
        List<TestListStudent> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String sql = "SELECT SUB.NAME AS SUBJECT_NAME, T.SUBJECT_CD, T.NUM, T.POINT "
                   + "FROM TEST T "
                   + "JOIN SUBJECT SUB ON T.SUBJECT_CD = SUB.CD AND T.SCHOOL_CD = SUB.SCHOOL_CD "
                   + "WHERE T.STUDENT_NO = ? AND T.SCHOOL_CD = ? "
                   + "ORDER BY T.SUBJECT_CD ASC, T.NUM ASC";

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, student.getNo());
            statement.setString(2, student.getSchool().getCd());

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                TestListStudent tls = new TestListStudent();
                tls.setSubjectName(resultSet.getString("SUBJECT_NAME"));
                tls.setSubjectCd(resultSet.getString("SUBJECT_CD"));
                tls.setNum(resultSet.getInt("NUM"));
                tls.setPoint(resultSet.getInt("POINT"));
                list.add(tls);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return list;
    }
}