package model.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectDB {
    private String instanceName;
    private String server;
    private String user;
    private String pass;
    private static ConnectDB instance; //Singleton
    private Connection conn;
    
    private ConnectDB() {
        instanceName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        server = "jdbc:sqlserver://FEBO-PC\\MSSQLSERVER2012:1433;databaseName=muro";
        user = "desarrollador";
        pass = "intel123!";
    }
    /**The synchronized word is used for support multiple user connections*/
    public synchronized static ConnectDB getInstance() {
        if (instance == null) {
            instance = new ConnectDB();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            Class.forName(instanceName);
            conn = DriverManager.getConnection(server,user, pass);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
    
    public void closeConnection() throws SQLException{
        if(conn != null){
            conn.close();
        }
        
    }
}
