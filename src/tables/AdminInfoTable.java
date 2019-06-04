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
public class AdminInfoTable {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty createtime = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty sex = new SimpleStringProperty();
    private final StringProperty tel = new SimpleStringProperty();
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();

    public AdminInfoTable(int id, String createtime, String name, String sex, String tel, String username, String password) {
        setId(id);
        setCreatetime(createtime);
        setName(name);
        setSex(sex);
        setTel(tel);
        setUsername(username);
        setPassword(password);
    }

    public static List<TableColumn<AdminInfoTable, String>> getColumns() {
        List<TableColumn<AdminInfoTable, String>> columns = new ArrayList<>();
        //设置列名
        TableColumn<AdminInfoTable, String> idCol = new TableColumn<>("户号");
        TableColumn<AdminInfoTable, String> createtimeCol = new TableColumn<>("注册时间");
        TableColumn<AdminInfoTable, String> nameCol = new TableColumn<>("姓名");
        TableColumn<AdminInfoTable, String> sexCol = new TableColumn<>("性别");
        TableColumn<AdminInfoTable, String> telCol = new TableColumn<>("电话");
        TableColumn<AdminInfoTable, String> usernameCol = new TableColumn<>("用户名");
        TableColumn<AdminInfoTable, String> passwordCol = new TableColumn<>("密码");
        //设置显示方式
        idCol.setCellValueFactory(param -> Bindings.createStringBinding(() -> {
            return String.format("%010d", param.getValue().getId());
        }, param.getValue().idProperty()));
        createtimeCol.setCellValueFactory(new PropertyValueFactory<>("createtime"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        sexCol.setCellValueFactory(new PropertyValueFactory<>("sex"));
        telCol.setCellValueFactory(new PropertyValueFactory<>("tel"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        //添加到列集合
        columns.add(idCol);
        columns.add(createtimeCol);
        columns.add(nameCol);
        columns.add(sexCol);
        columns.add(telCol);
        columns.add(usernameCol);
        columns.add(passwordCol);
        for (TableColumn<AdminInfoTable, String> column : columns) {
            column.setCellFactory((TableColumn<AdminInfoTable, String> p) -> new EditingCell<>());
        }
        //设置编辑完成事件
        idCol.setOnEditCommit(AdminInfoTable::idHandle);
        createtimeCol.setOnEditCommit(AdminInfoTable::createtimeHandle);
        nameCol.setOnEditCommit(AdminInfoTable::nameHandle);
        sexCol.setOnEditCommit(AdminInfoTable::sexHandle);
        telCol.setOnEditCommit(AdminInfoTable::telHandle);
        usernameCol.setOnEditCommit(AdminInfoTable::usernameHandle);
        passwordCol.setOnEditCommit(AdminInfoTable::passwordHandle);
        return columns;
    }

    private static void idHandle(TableColumn.CellEditEvent<AdminInfoTable, String> event) {
        String oldId = event.getOldValue();
        String newId = event.getNewValue();
        AdminInfoTable row = event.getRowValue();
        //执行更新
        try {
            SqlOperator.getInstance().updateOne(String.valueOf(row.getId()), "id", event.getNewValue(), "用户信息表");
            row.setId(Integer.valueOf(newId));
        } catch (SQLException e) {
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setId(0);
            row.setId(Integer.valueOf(oldId));
        }
    }


    private static void createtimeHandle(TableColumn.CellEditEvent<AdminInfoTable, String> event) {
        String oldValue = event.getOldValue();
        String newValue = event.getNewValue();
        AdminInfoTable row = event.getRowValue();
        try {
            SqlOperator.getInstance().updateOne(String.valueOf(row.getId()), "create_time", newValue, "用户信息表");
            row.setCreatetime(newValue);
        } catch (SQLException e) {//如果输入的数据格式错误
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setCreatetime("");
            row.setCreatetime(oldValue);
        }
    }

    private static void nameHandle(TableColumn.CellEditEvent<AdminInfoTable, String> event) {
        String oldValue = event.getOldValue();
        String newValue = event.getNewValue();
        AdminInfoTable row = event.getRowValue();
        try {
            SqlOperator.getInstance().updateOne(String.valueOf(row.getId()), "name", newValue, "用户信息表");
            row.setName(newValue);
        } catch (SQLException e) {//如果输入的数据格式错误
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setName("");
            row.setName(oldValue);
        }
    }

    private static void sexHandle(TableColumn.CellEditEvent<AdminInfoTable, String> event) {
        String oldValue = event.getOldValue();
        String newValue = event.getNewValue();
        AdminInfoTable row = event.getRowValue();
        try {
            SqlOperator.getInstance().updateOne(String.valueOf(row.getId()), "sex", newValue, "用户信息表");
            row.setSex(newValue);
        } catch (SQLException e) {//如果输入的数据格式错误
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setSex("");
            row.setSex(oldValue);
        }
    }

    private static void telHandle(TableColumn.CellEditEvent<AdminInfoTable, String> event) {
        String oldValue = event.getOldValue();
        String newValue = event.getNewValue();
        AdminInfoTable row = event.getRowValue();
        try {
            SqlOperator.getInstance().updateOne(String.valueOf(row.getId()), "tel", newValue, "用户信息表");
            row.setTel(newValue);
        } catch (SQLException e) {//如果输入的数据格式错误
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setTel("");
            row.setTel(oldValue);
        }
    }

    private static void usernameHandle(TableColumn.CellEditEvent<AdminInfoTable, String> event) {
        String oldValue = event.getOldValue();
        String newValue = event.getNewValue();
        AdminInfoTable row = event.getRowValue();
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

    private static void passwordHandle(TableColumn.CellEditEvent<AdminInfoTable, String> event) {
        String oldValue = event.getOldValue();
        String newValue = event.getNewValue();
        AdminInfoTable row = event.getRowValue();
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

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
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

    public String getCreatetime() {
        return createtime.get();
    }

    public StringProperty createtimeProperty() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime.set(createtime);
    }

    public String getSex() {
        return sex.get();
    }

    public StringProperty sexProperty() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex.set(sex);
    }

    public String getTel() {
        return tel.get();
    }

    public StringProperty telProperty() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel.set(tel);
    }
}
