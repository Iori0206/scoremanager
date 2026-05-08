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
        List<Subject> list = new ArrayList<Subject>();

        Connection con = getConnection();
        PreparedStatement st = con.prepareStatement(
            "select * from subject where school_cd=?"
        );
        st.setString(1, school.getCd());
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Subject subject = new Subject();
            subject.setSchool(school);
            subject.setCd(rs.getString("cd"));
            subject.setName(rs.getString("name"));
            list.add(subject);
        }

        rs.close();
        st.close();
        con.close();

        return list;
    }

    public Subject get(School school, String cd) throws Exception {
        Subject subject = null;

        Connection con = getConnection();
        PreparedStatement st = con.prepareStatement(
            "select * from subject where school_cd=? and cd=?"
        );
        st.setString(1, school.getCd());
        st.setString(2, cd);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            subject = new Subject();
            subject.setSchool(school);
            subject.setCd(rs.getString("cd"));
            subject.setName(rs.getString("name"));
        }

        rs.close();
        st.close();
        con.close();

        return subject;
    }

    public int insert(Subject subject) throws Exception {
        Connection con = getConnection();
        PreparedStatement st = con.prepareStatement(
            "insert into subject(school_cd, cd, name) values(?, ?, ?)"
        );
        st.setString(1, subject.getSchool().getCd());
        st.setString(2, subject.getCd());
        st.setString(3, subject.getName());

        int count = st.executeUpdate();

        st.close();
        con.close();

        return count;
    }

    public int update(Subject subject) throws Exception {
        Connection con = getConnection();
        PreparedStatement st = con.prepareStatement(
            "update subject set name=? where school_cd=? and cd=?"
        );
        st.setString(1, subject.getName());
        st.setString(2, subject.getSchool().getCd());
        st.setString(3, subject.getCd());

        int count = st.executeUpdate();

        st.close();
        con.close();

        return count;
    }

    public int delete(School school, String cd) throws Exception {
        Connection con = getConnection();
        PreparedStatement st = con.prepareStatement(
            "delete from subject where school_cd=? and cd=?"
        );
        st.setString(1, school.getCd());
        st.setString(2, cd);

        int count = st.executeUpdate();

        st.close();
        con.close();

        return count;
    }
}