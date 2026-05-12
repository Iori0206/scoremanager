package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.ClassNum;
import bean.School;

public class ClassNumDao extends DAO {

    // 1件取得
    public ClassNum get(String class_num, School school) throws Exception {
        ClassNum cn = null;

        if (school == null || class_num == null || class_num.isBlank()) {
            return null;
        }

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

    // クラス一覧（String のリスト）
    public List<String> filter(School school) throws Exception {
        List<String> list = new ArrayList<>();

        if (school == null) {
            return list;
        }

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

    // 新規登録
    public boolean save(ClassNum classNum) throws Exception {
        if (classNum == null || classNum.getSchool() == null || classNum.getClass_num() == null || classNum.getClass_num().isBlank()) {
            return false;
        }

        String sql = "INSERT INTO CLASS_NUM (CLASS_NUM, SCHOOL_CD) VALUES (?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, classNum.getClass_num());
            ps.setString(2, classNum.getSchool().getCd());

            return ps.executeUpdate() == 1;
        }
    }

    // クラス番号変更（更新）
    public boolean save(ClassNum classNum, String newClassNum) throws Exception {
        if (classNum == null || classNum.getSchool() == null || classNum.getClass_num() == null || newClassNum == null || newClassNum.isBlank()) {
            return false;
        }

        String sql = "UPDATE CLASS_NUM SET CLASS_NUM = ? WHERE CLASS_NUM = ? AND SCHOOL_CD = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newClassNum);
            ps.setString(2, classNum.getClass_num());
            ps.setString(3, classNum.getSchool().getCd());

            return ps.executeUpdate() == 1;
        }
    }
}