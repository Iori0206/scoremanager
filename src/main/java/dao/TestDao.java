package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.TestScore;

public class TestDao extends DAO {

    /**
     * 成績保存
     */
    public boolean save(List<TestScore> testList) throws Exception {

        String sql =
            "MERGE INTO TEST " +
            "(STUDENT_NO, SUBJECT_CD, SCHOOL_CD, POINT, NO) " +
            "KEY(STUDENT_NO, SUBJECT_CD, SCHOOL_CD, NO) " +
            "VALUES (?, ?, ?, ?, ?)";

        try (
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            for (TestScore ts : testList) {

                // 未入力はスキップ
                if (ts.getPoint() < 0) {
                    continue;
                }

                ps.setString(1, ts.getStudent().getNo());
                ps.setString(2, ts.getSubject().getCd());
                ps.setString(3, ts.getSchool().getCd());
                ps.setInt(4, ts.getPoint());

                // 回数
                ps.setInt(5, ts.getNum());

                ps.addBatch();
            }

            int[] result = ps.executeBatch();

            return result.length > 0;
        }
    }

    /**
     * 科目別成績取得
     */
    public List<TestScore> filter(
            int entYear,
            String classNum,
            Subject subject,
            int num,
            School school
    ) throws Exception {

        List<TestScore> list = new ArrayList<>();

        if (subject == null) {
            return list;
        }

        String sql =
            "SELECT " +
            "S.NO AS STUDENT_NO, " +
            "S.NAME, " +
            "T.POINT " +

            "FROM STUDENT S " +

            "LEFT JOIN TEST T " +
            "ON T.STUDENT_NO = S.NO " +
            "AND T.SUBJECT_CD = ? " +
            "AND T.NO = ? " +
            "AND T.SCHOOL_CD = ? " +

            "WHERE S.ENT_YEAR = ? " +
            "AND S.CLASS_NUM = ? " +
            "AND S.SCHOOL_CD = ? " +

            "ORDER BY S.NO";

        try (
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, subject.getCd());
            ps.setInt(2, num);
            ps.setString(3, school.getCd());

            ps.setInt(4, entYear);
            ps.setString(5, classNum);
            ps.setString(6, school.getCd());

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    TestScore ts = new TestScore();

                    Student student = new Student();

                    student.setNo(
                            rs.getString("STUDENT_NO")
                    );

                    student.setName(
                            rs.getString("NAME")
                    );

                    student.setEntYear(entYear);

                    student.setClassNum(classNum);

                    student.setSchool(school);

                    ts.setStudent(student);

                    ts.setSubject(subject);

                    ts.setNum(num);

                    ts.setSchool(school);

                    int point =
                            rs.getInt("POINT");

                    if (rs.wasNull()) {

                        ts.setPoint(-1);

                    } else {

                        ts.setPoint(point);
                    }

                    list.add(ts);
                }
            }
        }

        return list;
    }

    /**
     * 学生別成績取得
     */
    public List<TestScore> filter(
            Student student
    ) throws Exception {

        List<TestScore> list =
                new ArrayList<>();

        String sql =
            "SELECT " +
            "T.POINT, " +
            "T.NO, " +
            "T.SUBJECT_CD, " +
            "SUB.NAME AS SUBJECT_NAME " +

            "FROM TEST T " +

            "JOIN SUBJECT SUB " +
            "ON T.SUBJECT_CD = SUB.CD " +
            "AND T.SCHOOL_CD = SUB.SCHOOL_CD " +

            "WHERE T.STUDENT_NO = ? " +
            "AND T.SCHOOL_CD = ? " +

            "ORDER BY T.NO, T.SUBJECT_CD";

        try (
            Connection con = getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    student.getNo()
            );

            ps.setString(
                    2,
                    student.getSchool().getCd()
            );

            try (
                ResultSet rs =
                        ps.executeQuery()
            ) {

                while (rs.next()) {

                    TestScore ts =
                            new TestScore();

                    ts.setPoint(
                            rs.getInt("POINT")
                    );

                    // 回数
                    ts.setNum(
                            rs.getInt("NO")
                    );

                    Subject subject =
                            new Subject();

                    subject.setCd(
                            rs.getString(
                                    "SUBJECT_CD"
                            )
                    );

                    subject.setName(
                            rs.getString(
                                    "SUBJECT_NAME"
                            )
                    );

                    ts.setSubject(subject);

                    ts.setStudent(student);

                    ts.setSchool(
                            student.getSchool()
                    );

                    list.add(ts);
                }
            }
        }

        return list;
    }
}