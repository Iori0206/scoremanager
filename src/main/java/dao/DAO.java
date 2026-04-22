package dao;

import java.sql.Connection;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DAO {
    static DataSource ds; // 1

    public Connection getConnection() throws Exception { // 2
        if (ds==null) {
            InitialContext ic=new InitialContext();
            ds=(DataSource)ic.lookup("java:/comp/env/jdbc/book"); // 3
        }
        return ds.getConnection(); // 4
    }
}