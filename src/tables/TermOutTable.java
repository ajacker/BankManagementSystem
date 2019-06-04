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

public class TermOutTable {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final IntegerProperty inId = new SimpleIntegerProperty();
    private final IntegerProperty userId = new SimpleIntegerProperty();
    private final StringProperty createTime = new SimpleStringProperty();
    private final FloatProperty money = new SimpleFloatProperty();
    private final StringProperty remark = new SimpleStringProperty();

    public TermOutTable(int id, int inId, int userId, String createTime, float money, String remark) {
        setId(id);
        setInId(inId);
        setUserId(userId);
        setCreateTime(createTime);
        setMoney(money);
        setRemark(remark);
    }

    public static List<TableColumn<TermOutTable, String>> getColumns() {
        List<TableColumn<TermOutTable, String>> columns = new ArrayList<>();
        TableColumn<TermOutTable, String> idCol = new TableColumn<>("单号");
        TableColumn<TermOutTable, String> inIdCol = new TableColumn<>("定期存款单号");
        TableColumn<TermOutTable, String> useridCol = new TableColumn<>("户号");
        TableColumn<TermOutTable, String> createtimeCol = new TableColumn<>("创建时间");
        TableColumn<TermOutTable, String> moneyCol = new TableColumn<>("金额");
        TableColumn<TermOutTable, String> remarkCol = new TableColumn<>("备注");
        idCol.setCellValueFactory(param -> Bindings.createStringBinding(() -> {
            return String.format("%010d", param.getValue().getId());
        }, param.getValue().idProperty()));
        inIdCol.setCellValueFactory(param -> Bindings.createStringBinding(() -> {
            return String.format("%010d", param.getValue().getInId());
        }, param.getValue().inIdProperty()));
        useridCol.setCellValueFactory(param -> Bindings.createStringBinding(() -> {
            return String.format("%010d", param.getValue().getUserId());
        }, param.getValue().userIdProperty()));
        createtimeCol.setCellValueFactory(new PropertyValueFactory<>("createTime"));
        moneyCol.setCellValueFactory(param -> Bindings.createStringBinding(() -> {
            return String.format("%.2f", param.getValue().getMoney());
        }, param.getValue().moneyProperty()));
        remarkCol.setCellValueFactory(new PropertyValueFactory<>("remark"));
        columns.add(idCol);
        columns.add(inIdCol);
        columns.add(useridCol);
        columns.add(createtimeCol);
        columns.add(moneyCol);
        columns.add(remarkCol);
        for (TableColumn<TermOutTable, String> column : columns) {
            column.setCellFactory((TableColumn<TermOutTable, String> p) -> new EditingCell<>());
        }
        //设置编辑完成事件
        idCol.setOnEditCommit(TermOutTable::idHandle);
        inIdCol.setOnEditCommit(TermOutTable::inIdHandle);
        useridCol.setOnEditCommit(TermOutTable::userIdHandle);
        createtimeCol.setOnEditCommit(TermOutTable::createTimeHandle);
        moneyCol.setOnEditCommit(TermOutTable::moneyHandle);
        remarkCol.setOnEditCommit(TermOutTable::remarkHandle);
        return columns;
    }

    private static void idHandle(TableColumn.CellEditEvent<TermOutTable, String> event) {
        String oldValue = event.getOldValue();
        String newValue = event.getNewValue();
        TermOutTable row = event.getRowValue();
        //执行更新
        try {
            SqlOperator.getInstance().updateOne(String.valueOf(row.getId()), "id", event.getNewValue(), "定期取款信息表");
            row.setId(Integer.valueOf(newValue));
        } catch (SQLException e) {
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setId(0);
            row.setId(Integer.valueOf(oldValue));
        }
    }

    private static void userIdHandle(TableColumn.CellEditEvent<TermOutTable, String> event) {
        String oldUserId = event.getOldValue();
        String newUserId = event.getNewValue();
        TermOutTable row = event.getRowValue();
        //执行更新
        try {
            SqlOperator.getInstance().updateOne(String.valueOf(row.getId()), "user_id", event.getNewValue(), "定期取款信息表");
            row.setUserId(Integer.valueOf(newUserId));
        } catch (SQLException e) {
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setUserId(0);
            row.setUserId(Integer.valueOf(oldUserId));
        }
    }

    private static void createTimeHandle(TableColumn.CellEditEvent<TermOutTable, String> event) {
        String oldCreateTime = event.getOldValue();
        String newCreateTime = event.getNewValue();
        TermOutTable row = event.getRowValue();
        try {
            SqlOperator.getInstance().updateOne(String.valueOf(row.getId()), "create_time", newCreateTime, "定期取款信息表");
            row.setCreateTime(newCreateTime);
        } catch (SQLException e) {//如果输入的数据格式错误
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setCreateTime("");
            row.setCreateTime(oldCreateTime);
        }
    }

    private static void inIdHandle(TableColumn.CellEditEvent<TermOutTable, String> event) {
        String oldValue = event.getOldValue();
        String newValue = event.getNewValue();
        TermOutTable row = event.getRowValue();
        //执行更新
        try {
            SqlOperator.getInstance().updateOne(String.valueOf(row.getId()), "in_id", event.getNewValue(), "定期取款信息表");
            row.setInId(Integer.valueOf(newValue));
        } catch (SQLException e) {
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setInId(0);
            row.setInId(Integer.valueOf(oldValue));
        }
    }

    private static void moneyHandle(TableColumn.CellEditEvent<TermOutTable, String> event) {
        String oldValue = event.getOldValue();
        String newValue = event.getNewValue();
        TermOutTable row = event.getRowValue();
        //执行更新
        try {
            SqlOperator.getInstance().updateOne(String.valueOf(row.getId()), "money", event.getNewValue(), "定期取款信息表");
            row.setMoney(Float.valueOf(newValue));
        } catch (SQLException e) {
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setMoney(0);
            row.setMoney(Float.valueOf(oldValue));
        }
    }

    private static void remarkHandle(TableColumn.CellEditEvent<TermOutTable, String> event) {
        String oldRemark = event.getOldValue();
        String newRemark = event.getNewValue();
        TermOutTable row = event.getRowValue();
        try {
            SqlOperator.getInstance().updateOne(String.valueOf(row.getId()), "remark", newRemark, "定期取款信息表");
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

    public int getInId() {
        return inId.get();
    }

    public IntegerProperty inIdProperty() {
        return inId;
    }

    public void setInId(int inId) {
        this.inId.set(inId);
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
