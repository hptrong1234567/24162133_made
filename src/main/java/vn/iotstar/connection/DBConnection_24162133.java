package vn.iotstar.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection_24162133 {
    
    private final String serverName = "localhost";
    private final String dbName = "BookStore";
    private final String portNumber = "1433";
    private final String instance = "";
    private final String userID = "sa";
    private final String password = "phutrong2111";
    
    public Connection getConnection() throws Exception {
        String url = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=" + dbName 
                    + ";encrypt=false;trustServerCertificate=true;sslProtocol=TLSv1.2;characterEncoding=UTF-8";
        
        if (instance != null && !instance.trim().isEmpty()) {
            url = "jdbc:sqlserver://" + serverName + ":" + portNumber + "\\" + instance 
                  + ";databaseName=" + dbName 
                  + ";encrypt=false;trustServerCertificate=true;sslProtocol=TLSv1.2;characterEncoding=UTF-8";
        }
        
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(url, userID, password);
    }
    
    // Test kết nối
    public static void main(String[] args) {
        try {
            Connection conn = new DBConnection_24162133().getConnection();
            if (conn != null) {
                System.out.println(">>> Kết nối database BookStore thành công!");
                conn.close();
            }
        } catch (Exception e) {
            System.out.println(">>> Lỗi kết nối: " + e.getMessage());
            e.printStackTrace();
        }
    }
}