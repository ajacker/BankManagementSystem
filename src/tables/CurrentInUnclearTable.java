package tables;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import sqltool.SqlOperator;
import tool.Alerts;
import tool.EditingCell;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class CurrentInUnclearTable {

    private final IntegerProperty userId = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final FloatProperty money = new SimpleFloatProperty();
    private final FloatProperty currentBalance = new SimpleFloatProperty();

    public CurrentInUnclearTable(int userId, String name, int id, float money, float currentBalance) {
        setUserId(userId);
        setName(name);
        setId(id);
        setMoney(money);
        setCurrentBalance(currentBalance);
    }

    public static List<TableColumn<CurrentInUnclearTable, String>> getColumns() {
        List<TableColumn<CurrentInUnclearTable, String>> columns = new ArrayList<>();
        TableColumn<CurrentInUnclearTable, String> useridCol = new TableColumn<>("户号");
        TableColumn<CurrentInUnclearTable, String> nameCol = new TableColumn<>("姓名");
        TableColumn<CurrentInUnclearTable, String> idCol = new TableColumn<>("单号");
        TableColumn<CurrentInUnclearTable, String> moneyCol = new TableColumn<>("金额");
        TableColumn<CurrentInUnclearTable, String> balanceCol = new TableColumn<>("活期余额");
        useridCol.setCellValueFactory(param -> Bindings.createStringBinding(() -> {
            return String.format("%010d", param.getValue().getUserId());
        }, param.getValue().userIdProperty()));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        idCol.setCellValueFactory(param -> Bindings.createStringBinding(() -> {
            return String.format("%010d", param.getValue().getId());
        }, param.getValue().idProperty()));
        moneyCol.setCellValueFactory(param -> Bindings.createStringBinding(() -> {
            return String.format("%.2f", param.getValue().getMoney());
        }, param.getValue().moneyProperty()));
        balanceCol.setCellValueFactory(param -> Bindings.createStringBinding(() -> {
            return String.format("%.2f", param.getValue().getCurrentBalance());
        }, param.getValue().currentBalanceProperty()));
        columns.add(useridCol);
        columns.add(nameCol);
        columns.add(idCol);
        columns.add(moneyCol);
        columns.add(balanceCol);
        for (TableColumn<CurrentInUnclearTable, String> column : columns) {
            column.setCellFactory((TableColumn<CurrentInUnclearTable, String> p) -> new EditingCell<>());
        }
        //设置编辑完成事件
        useridCol.setOnEditCommit(CurrentInUnclearTable::userIdHandle);
        nameCol.setOnEditCommit(CurrentInUnclearTable::nameHandle);
        idCol.setOnEditCommit(CurrentInUnclearTable::idHandle);
        moneyCol.setOnEditCommit(CurrentInUnclearTable::moneyHandle);
        balanceCol.setOnEditCommit(CurrentInUnclearTable::balanceHandle);
        return columns;
    }


    private static void userIdHandle(TableColumn.CellEditEvent<CurrentInUnclearTable, String> event) {
        String oldValue = event.getOldValue();
        String newValue = event.getNewValue();
        CurrentInUnclearTable row = event.getRowValue();
        //执行更新
        try {
            SqlOperator.getInstance().updateOne(String.valueOf(row.getUserId()), "id", event.getNewValue(), "用户信息表");
            row.setUserId(Integer.valueOf(newValue));
        } catch (SQLException e) {
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setUserId(0);
            row.setUserId(Integer.valueOf(oldValue));
        }
    }

    private static void nameHandle(TableColumn.CellEditEvent<CurrentInUnclearTable, String> event) {
        String oldValue = event.getOldValue();
        String newValue = event.getNewValue();
        CurrentInUnclearTable row = event.getRowValue();
        try {
            SqlOperator.getInstance().updateOne(String.valueOf(row.getUserId()), "name", newValue, "用户信息表");
            row.setName(newValue);
            event.getTableView().refresh();
        } catch (SQLException e) {//如果输入的数据格式错误
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setName("");
            row.setName(oldValue);
        }
    }

    private static void idHandle(TableColumn.CellEditEvent<CurrentInUnclearTable, String> event) {
        String oldValue = event.getOldValue();
        String newValue = event.getNewValue();
        CurrentInUnclearTable row = event.getRowValue();
        //执行更新
        try {
            SqlOperator.getInstance().updateOne(String.valueOf(row.getId()), "id", event.getNewValue(), "活期存款信息表");
            row.setId(Integer.valueOf(newValue));
        } catch (SQLException e) {
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setId(0);
            row.setId(Integer.valueOf(oldValue));
        }
    }

    private static void moneyHandle(TableColumn.CellEditEvent<CurrentInUnclearTable, String> event) {
        String oldValue = event.getOldValue();
        String newValue = event.getNewValue();
        CurrentInUnclearTable row = event.getRowValue();
        //执行更新
        try {
            SqlOperator.getInstance().updateOne(String.valueOf(row.getId()), "money", event.getNewValue(), "活期存款信息表");
            row.setMoney(Float.valueOf(newValue));
        } catch (SQLException e) {
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setMoney(0);
            row.setMoney(Float.valueOf(oldValue));
        }
    }

    private static void balanceHandle(TableColumn.CellEditEvent<CurrentInUnclearTable, String> event) {
        String oldValue = event.getOldValue();
        String newValue = event.getNewValue();
        CurrentInUnclearTable row = event.getRowValue();
        //执行更新
        try {
            SqlOperator.getInstance().updateOne(String.valueOf(row.getUserId()), "current_balance", event.getNewValue(), "用户信息表  ");
            row.setCurrentBalance(Float.valueOf(newValue));
        } catch (SQLException e) {
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setCurrentBalance(0);
            row.setCurrentBalance(Float.valueOf(oldValue));
        }
    }

    public int getUserId() {
        return userId.get();
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
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

    public float getMoney() {
        return money.get();
    }

    public FloatProperty moneyProperty() {
        return money;
    }

    public void setMoney(float money) {
        this.money.set(money);
    }

    public float getCurrentBalance() {
        return currentBalance.get();
    }

    public FloatProperty currentBalanceProperty() {
        return currentBalance;
    }

    public void setCurrentBalance(float currentBalance) {
        this.currentBalance.set(currentBalance);
    }
}
