package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.School;

public class SchoolDao extends DAO {

    public School login(String cd, String password) throws Exception {

        String sql = "SELECT cd, name FROM school WHERE cd = ?";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, cd);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                School s = new School();
                s.setCd(rs.getString("cd"));
                s.setName(rs.getString("name"));
                return s;
            }
        }

        return null;
    }
}