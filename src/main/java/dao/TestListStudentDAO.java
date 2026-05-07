package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

// 1. クラス名の最後を Dao に修正（ファイル名も TestListStudentDao.java にしてください）
// 2. 継承元を Dao に修正（小文字）
public class TestListStudentDAO extends DAO {

    /**
     * 特定の学生に紐づく成績一覧を取得する
     * @param student 学生の情報
     * @return 成績（科目名、点数など）のリスト
     */
    public List<TestListStudent> filter(Student student) throws Exception {
        List<TestListStudent> list = new ArrayList<>();
        // 3. 親クラス（Dao.java）から getConnection() を引き継いで使用
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        // SQL文：科目(SUBJECT)と成績(TEST)を結合
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
                // Bean にデータをセット
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
            // リソースの解放
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return list;
    }
}