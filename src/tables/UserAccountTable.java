package tables;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import sqltool.SqlOperator;
import tool.Alerts;
import tool.EditingCell;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class UserAccountTable {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();

    public UserAccountTable(int id, String username, String password) {
        setId(id);
        setUsername(username);
        setPassword(password);
    }

    public static List<TableColumn<UserAccountTable, String>> getColumns() {
        List<TableColumn<UserAccountTable, String>> columns = new ArrayList<>();
        //设置列名
        TableColumn<UserAccountTable, String> idCol = new TableColumn<>("户号");
        TableColumn<UserAccountTable, String> usernameCol = new TableColumn<>("用户名");
        TableColumn<UserAccountTable, String> passwordCol = new TableColumn<>("密码");
        //设置显示方式
        idCol.setCellValueFactory(param -> Bindings.createStringBinding(() -> {
            return String.format("%010d", param.getValue().getId());
        }, param.getValue().idProperty()));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        //添加到列集合
        columns.add(idCol);
        columns.add(usernameCol);
        columns.add(passwordCol);
        for (TableColumn<UserAccountTable, String> column : columns) {
            column.setCellFactory((TableColumn<UserAccountTable, String> p) -> new EditingCell<>());
        }
        //设置编辑完成事件
        idCol.setOnEditCommit(UserAccountTable::idHandle);
        usernameCol.setOnEditCommit(UserAccountTable::usernameHandle);
        passwordCol.setOnEditCommit(UserAccountTable::passwordHandle);
        return columns;
    }

    private static void idHandle(TableColumn.CellEditEvent<UserAccountTable, String> event) {
        String oldId = event.getOldValue();
        String newId = event.getNewValue();
        UserAccountTable row = event.getRowValue();
        //执行更新
        try {
            SqlOperator.getInstance().updateOne(String.valueOf(row.getId()), "id", event.getNewValue(), "用户账户");
            row.setId(Integer.valueOf(newId));
        } catch (SQLException e) {
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setId(0);
            row.setId(Integer.valueOf(oldId));
        }
    }


    private static void usernameHandle(TableColumn.CellEditEvent<UserAccountTable, String> event) {
        String oldValue = event.getOldValue();
        String newValue = event.getNewValue();
        UserAccountTable row = event.getRowValue();
        try {
            SqlOperator.getInstance().updateOne(String.valueOf(row.getId()), "username", newValue, "用户账户");
            row.setUsername(newValue);
        } catch (SQLException e) {//如果输入的数据格式错误
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setUsername("");
            row.setUsername(oldValue);
        }
    }

    private static void passwordHandle(TableColumn.CellEditEvent<UserAccountTable, String> event) {
        String oldValue = event.getOldValue();
        String newValue = event.getNewValue();
        UserAccountTable row = event.getRowValue();
        try {
            SqlOperator.getInstance().updateOne(String.valueOf(row.getId()), "password", newValue, "用户账户");
            row.setPassword(newValue);
        } catch (SQLException e) {//如果输入的数据格式错误
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setPassword("");
            row.setPassword(oldValue);
        }
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }
}
