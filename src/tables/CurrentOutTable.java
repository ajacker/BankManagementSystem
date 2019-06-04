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
public class CurrentOutTable {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final IntegerProperty userId = new SimpleIntegerProperty();
    private final StringProperty createTime = new SimpleStringProperty();
    private final FloatProperty money = new SimpleFloatProperty();
    private final StringProperty remark = new SimpleStringProperty();

    public CurrentOutTable(int id, int userId, String createTime, float money, String remark) {
        setId(id);
        setUserId(userId);
        setCreateTime(createTime);
        setMoney(money);
        setRemark(remark);
    }

    public static List<TableColumn<CurrentOutTable, String>> getColumns() {
        List<TableColumn<CurrentOutTable, String>> columns = new ArrayList<>();
        TableColumn<CurrentOutTable, String> idCol = new TableColumn<>("单号");
        TableColumn<CurrentOutTable, String> useridCol = new TableColumn<>("户号");
        TableColumn<CurrentOutTable, String> createtimeCol = new TableColumn<>("创建时间");
        TableColumn<CurrentOutTable, String> moneyCol = new TableColumn<>("金额");
        TableColumn<CurrentOutTable, String> remarkCol = new TableColumn<>("备注");
        idCol.setCellValueFactory(param -> Bindings.createStringBinding(() -> {
            return String.format("%010d", param.getValue().getId());
        }, param.getValue().idProperty()));
        useridCol.setCellValueFactory(param -> Bindings.createStringBinding(() -> {
            return String.format("%010d", param.getValue().getUserId());
        }, param.getValue().userIdProperty()));
        createtimeCol.setCellValueFactory(new PropertyValueFactory<>("createTime"));
        moneyCol.setCellValueFactory(param -> Bindings.createStringBinding(() -> {
            return String.format("%.2f", param.getValue().getMoney());
        }, param.getValue().moneyProperty()));
        remarkCol.setCellValueFactory(new PropertyValueFactory<>("remark"));
        columns.add(idCol);
        columns.add(useridCol);
        columns.add(createtimeCol);
        columns.add(moneyCol);
        columns.add(remarkCol);
        for (TableColumn<CurrentOutTable, String> column : columns) {
            column.setCellFactory((TableColumn<CurrentOutTable, String> p) -> new EditingCell<>());
        }
        //设置编辑完成事件
        idCol.setOnEditCommit(CurrentOutTable::idHandle);
        useridCol.setOnEditCommit(CurrentOutTable::userIdHandle);
        createtimeCol.setOnEditCommit(CurrentOutTable::createTimeHandle);
        moneyCol.setOnEditCommit(CurrentOutTable::moneyHandle);
        remarkCol.setOnEditCommit(CurrentOutTable::remarkHandle);
        return columns;
    }

    private static void idHandle(TableColumn.CellEditEvent<CurrentOutTable, String> event) {
        String oldId = event.getOldValue();
        String newId = event.getNewValue();
        CurrentOutTable row = event.getRowValue();
        //执行更新
        try {
            SqlOperator.getInstance().updateOne(String.valueOf(row.getId()), "id", event.getNewValue(), "活期取款信息表");
            row.setId(Integer.valueOf(newId));
        } catch (SQLException e) {
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setId(0);
            row.setId(Integer.valueOf(oldId));
        }
    }

    private static void userIdHandle(TableColumn.CellEditEvent<CurrentOutTable, String> event) {
        String oldUserId = event.getOldValue();
        String newUserId = event.getNewValue();
        CurrentOutTable row = event.getRowValue();
        //执行更新
        try {
            SqlOperator.getInstance().updateOne(String.valueOf(row.getId()), "user_id", event.getNewValue(), "活期取款信息表");
            row.setUserId(Integer.valueOf(newUserId));
        } catch (SQLException e) {
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setUserId(0);
            row.setUserId(Integer.valueOf(oldUserId));
        }
    }

    private static void createTimeHandle(TableColumn.CellEditEvent<CurrentOutTable, String> event) {
        String oldCreateTime = event.getOldValue();
        String newCreateTime = event.getNewValue();
        CurrentOutTable row = event.getRowValue();
        try {
            SqlOperator.getInstance().updateOne(String.valueOf(row.getId()), "create_time", newCreateTime, "活期取款信息表");
            row.setCreateTime(newCreateTime);
        } catch (SQLException e) {//如果输入的数据格式错误
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setCreateTime("");
            row.setCreateTime(oldCreateTime);
        }
    }

    private static void moneyHandle(TableColumn.CellEditEvent<CurrentOutTable, String> event) {
        String oldMoney = event.getOldValue();
        String newMoney = event.getNewValue();
        CurrentOutTable row = event.getRowValue();
        //执行更新
        try {
            SqlOperator.getInstance().updateOne(String.valueOf(row.getId()), "money", event.getNewValue(), "活期取款信息表");
            row.setMoney(Float.valueOf(newMoney));
        } catch (SQLException e) {
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setMoney(0);
            row.setMoney(Float.valueOf(oldMoney));
        }
    }

    private static void remarkHandle(TableColumn.CellEditEvent<CurrentOutTable, String> event) {
        String oldRemark = event.getOldValue();
        String newRemark = event.getNewValue();
        CurrentOutTable row = event.getRowValue();
        try {
            SqlOperator.getInstance().updateOne(String.valueOf(row.getId()), "remark", newRemark, "活期取款信息表");
            row.setRemark(newRemark);
        } catch (SQLException e) {//如果输入的数据格式错误
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setRemark("");
            row.setRemark(oldRemark);
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

    public int getUserId() {
        return userId.get();
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public String getCreateTime() {
        return createTime.get();
    }

    public StringProperty createTimeProperty() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime.set(createTime);
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

    public String getRemark() {
        return remark.get();
    }

    public StringProperty remarkProperty() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark.set(remark);
    }
}
