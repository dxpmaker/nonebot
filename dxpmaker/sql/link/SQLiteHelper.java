package com.dxpmaker.sql.link;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLiteHelper {
    private  Connection conn = null;
    public SQLiteHelper(String dbName, String sqlFilePath) {
        try {
            // 连接到SQLite数据库  
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbName);
            conn.setAutoCommit(false);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public String sqllink(String sqlFilePath) throws IOException {
        PreparedStatement pstmt = null;
        // 读取SQL文件并执行
        try (BufferedReader br = new BufferedReader(new FileReader(sqlFilePath))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                // 去除注释和空行
                if (!line.trim().startsWith("--") && !line.trim().isEmpty()) {
                    sb.append(line);
                    if (line.endsWith(";")) {
                        String sql = sb.toString();
                        pstmt = conn.prepareStatement(sql);
                        pstmt.executeUpdate();
                        sb = new StringBuilder(); // 清空StringBuilder以准备下一次SQL命令
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "1";
    }
//    public static void main(String[] args) {
//        String dbName = "path/to/your/database.db"; // 替换为你的数据库路径
//        String sqlFilePath = "path/to/your/sqlfile.sql"; // 替换为你的SQL文件路径
//        new SQLiteHelper(dbName, sqlFilePath);
//    }
}