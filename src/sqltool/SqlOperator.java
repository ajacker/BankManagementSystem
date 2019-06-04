package sqltool;

import java.sql.*;

public class SqlOperator {
    private static final SqlOperator SQLOP = new SqlOperator();
    private Statement stmt;
    private Connection connection;

    private SqlOperator() {
        initializeDB();
    }

    private void initializeDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_db?useUnicode=true&characterEncoding=UTF-8", "root", "456852");
            stmt = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SqlOperator getInstance() {
        return SQLOP;
    }

    public Statement getStmt() {
        return stmt;
    }

    public Connection getConnection() {
        return connection;
    }

    public ResultSet selectAllFrom(String str) {
        String sql = "select * from " + str;
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet selectAllFrom(String str, int user_id) {
        String sql = "select * from " + str + " where user_id =" + user_id;
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            sql = "select * from " + str + " where id =" + user_id;
            try {
                return stmt.executeQuery(sql);
            } catch (SQLException e1) {
                e1.printStackTrace();
                return null;
            }
        }
    }

    public int updateOne(String id, String aim, String value, String table) throws SQLException {
        String sql = "update " + table + " set " + aim + " = '" + value + "' where id=" + id;
        return stmt.executeUpdate(sql);
    }

    public int updateOne(String aim, String value, String table) throws SQLException {
        String sql = "update " + table + " set " + aim + " = '" + value + "'";
        return stmt.executeUpdate(sql);
    }

    public int updateInterest(String aim, String value) throws SQLException {
        String sql = "update 利率 set " + aim + " = '" + value + "'";
        return stmt.executeUpdate(sql);
    }

    public int insertOne(String table, String rows, String values) throws SQLException {
        String sql = "insert " + table + " " + rows + " values" + values;
        System.out.println(sql);
        return stmt.executeUpdate(sql);
    }

    public int deleteSome(String table, String ids) throws SQLException {
        String sql = "delete from " + table + " where id in " + ids;
        System.out.println(sql);
        return stmt.executeUpdate(sql);
    }

    public void callMethod() throws SQLException {
        String sql = "call caculate_interest()";
        CallableStatement statement = connection.prepareCall(sql);
        statement.execute();
        statement.close();
    }
}
