package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.ClassNum;
import bean.School;

public class ClassNumDao extends DAO {

    public ClassNum get(String class_num, School school) throws Exception {
        ClassNum cn = null;

        String sql = "SELECT * FROM CLASS_NUM WHERE CLASS_NUM = ? AND SCHOOL_CD = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, class_num);
            ps.setString(2, school.getCd());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cn = new ClassNum();
                    cn.setClass_num(rs.getString("CLASS_NUM"));
                    cn.setSchool(school);
                }
            }
        }

        return cn;
    }

    public List<String> filter(School school) throws Exception {
        List<String> list = new ArrayList<>();

        String sql = "SELECT CLASS_NUM FROM CLASS_NUM WHERE SCHOOL_CD = ? ORDER BY CLASS_NUM";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, school.getCd());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getString("CLASS_NUM"));
                }
            }
        }

        return list;
    }

    public boolean save(ClassNum classNum) throws Exception {
        String sql = "INSERT INTO CLASS_NUM (CLASS_NUM, SCHOOL_CD) VALUES (?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, classNum.getClass_num());
            ps.setString(2, classNum.getSchool().getCd());

            return ps.executeUpdate() == 1;
        }
    }

    public boolean delete(String class_num, School school) throws Exception {
        String sql = "DELETE FROM CLASS_NUM WHERE CLASS_NUM = ? AND SCHOOL_CD = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, class_num);
            ps.setString(2, school.getCd());

            return ps.executeUpdate() == 1;
        }
    }
}