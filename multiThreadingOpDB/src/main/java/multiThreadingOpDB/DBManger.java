package multiThreadingOpDB;

import java.sql.*;

public class DBManger {
    private Connection connection;
    PreparedStatement ps;

    public DBManger() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = getConnection();
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/demo?characterEncoding=UTF-8", "root",
                "1234");
    }
    public ResultSet executeQuery(String sql) throws SQLException {
        return connection.prepareStatement(sql).executeQuery();
    }
    public ResultSet executeQueryByParam(String sql) throws SQLException {
        ps=connection.prepareStatement(sql);
        return ps.executeQuery();
    }

    public void execute(String sql) throws SQLException {
        connection.prepareStatement(sql).execute();
    }
}
