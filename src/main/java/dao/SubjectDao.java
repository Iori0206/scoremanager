package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends DAO {

    public List<Subject> filter(School school) throws Exception {

        List<Subject> list = new ArrayList<>();

        String sql = "SELECT SUBJECT_ID, SUBJECT_NAME FROM SUBJECT ORDER BY SUBJECT_ID";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // SUBJECT テーブルに SCHOOL_CD が無いので削除
            // ps.setString(1, school.getCd());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Subject s = new Subject();
                s.setSubjectCd(rs.getString("SUBJECT_ID"));
                s.setName(rs.getString("SUBJECT_NAME"));
                list.add(s);
            }
        }

        return list;
    }
}