package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.Teacher;

public class TeacherDao extends DAO {

    public Teacher login(String id, String password) throws Exception {

        String sql = "SELECT id, password, name, school_cd FROM teacher WHERE id = ? AND password = ?";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, id);
            st.setString(2, password);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                Teacher t = new Teacher();
                t.setId(rs.getString("id"));
                t.setName(rs.getString("name"));
                t.setSchoolCd(rs.getString("school_cd"));
                return t;
            }
        }

        return null;
    }
}