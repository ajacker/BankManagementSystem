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
public class TermInTable {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final IntegerProperty userId = new SimpleIntegerProperty();
    private final StringProperty createTime = new SimpleStringProperty();
    private final FloatProperty money = new SimpleFloatProperty();
    private final IntegerProperty duration = new SimpleIntegerProperty();
    private final FloatProperty interest = new SimpleFloatProperty();
    private final StringProperty remark = new SimpleStringProperty();

    public TermInTable(int id, int userId, String createTime, float money, int duration, float interest, String remark) {
        setId(id);
        setUserId(userId);
        ;
        setCreateTime(createTime);
        setMoney(money);
        setDuration(duration);
        setInterest(interest);
        setRemark(remark);
    }

    public static List<TableColumn<TermInTable, String>> getColumns() {
        List<TableColumn<TermInTable, String>> columns = new ArrayList<>();
        TableColumn<TermInTable, String> idCol = new TableColumn<>("单号");
        TableColumn<TermInTable, String> useridCol = new TableColumn<>("户号");
        TableColumn<TermInTable, String> createtimeCol = new TableColumn<>("创建时间");
        TableColumn<TermInTable, String> moneyCol = new TableColumn<>("金额");
        TableColumn<TermInTable, String> durationCol = new TableColumn<>("定期限");
        TableColumn<TermInTable, String> interestCol = new TableColumn<>("利息");
        TableColumn<TermInTable, String> remarkCol = new TableColumn<>("备注");
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
        durationCol.setCellValueFactory(param -> Bindings.createStringBinding(() -> {
            if (param.getValue().getDuration() < 12) {
                return param.getValue().getDuration() + "月";
            } else {
                return param.getValue().getDuration() / 12 + "年";
            }
        }, param.getValue().durationProperty()));
        interestCol.setCellValueFactory(param -> Bindings.createStringBinding(() -> {
            return String.format("%.2f", param.getValue().getInterest());
        }, param.getValue().interestProperty()));
        remarkCol.setCellValueFactory(new PropertyValueFactory<>("remark"));
        columns.add(idCol);
        columns.add(useridCol);
        columns.add(createtimeCol);
        columns.add(moneyCol);
        columns.add(durationCol);
        columns.add(interestCol);
        columns.add(remarkCol);
        for (TableColumn<TermInTable, String> column : columns) {
            column.setCellFactory((TableColumn<TermInTable, String> p) -> new EditingCell<>());
        }
        //设置编辑完成事件
        idCol.setOnEditCommit(TermInTable::idHandle);
        useridCol.setOnEditCommit(TermInTable::userIdHandle);
        createtimeCol.setOnEditCommit(TermInTable::createTimeHandle);
        moneyCol.setOnEditCommit(TermInTable::moneyHandle);
        durationCol.setOnEditCommit(TermInTable::durationHandle);
        interestCol.setOnEditCommit(TermInTable::interestHandle);
        remarkCol.setOnEditCommit(TermInTable::remarkHandle);
        return columns;
    }

    private static void idHandle(TableColumn.CellEditEvent<TermInTable, String> event) {
        String oldValue = event.getOldValue();
        String newValue = event.getNewValue();
        TermInTable row = event.getRowValue();
        //执行更新
        try {
            SqlOperator.getInstance().updateOne(String.valueOf(row.getId()), "id", event.getNewValue(), "定期存款信息表");
            row.setId(Integer.valueOf(newValue));
        } catch (SQLException e) {
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setId(0);
            row.setId(Integer.valueOf(oldValue));
        }
    }

    private static void userIdHandle(TableColumn.CellEditEvent<TermInTable, String> event) {
        String oldValue = event.getOldValue();
        String newValue = event.getNewValue();
        TermInTable row = event.getRowValue();
        //执行更新
        try {
            SqlOperator.getInstance().updateOne(String.valueOf(row.getId()), "user_id", event.getNewValue(), "定期存款信息表");
            row.setUserId(Integer.valueOf(newValue));
        } catch (SQLException e) {
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setUserId(0);
            row.setUserId(Integer.valueOf(oldValue));
        }
    }

    private static void createTimeHandle(TableColumn.CellEditEvent<TermInTable, String> event) {
        String oldCreateTime = event.getOldValue();
        String newCreateTime = event.getNewValue();
        TermInTable row = event.getRowValue();
        try {
            SqlOperator.getInstance().updateOne(String.valueOf(row.getId()), "create_time", newCreateTime, "定期存款信息表");
            row.setCreateTime(newCreateTime);
        } catch (SQLException e) {//如果输入的数据格式错误
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setCreateTime("");
            row.setCreateTime(oldCreateTime);
        }
    }

    private static void moneyHandle(TableColumn.CellEditEvent<TermInTable, String> event) {
        String oldValue = event.getOldValue();
        String newValue = event.getNewValue();
        TermInTable row = event.getRowValue();
        //执行更新
        try {
            SqlOperator.getInstance().updateOne(String.valueOf(row.getId()), "money", event.getNewValue(), "定期存款信息表");
            row.setMoney(Float.valueOf(newValue));
        } catch (SQLException e) {
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setMoney(0);
            row.setMoney(Float.valueOf(oldValue));
        }
    }

    private static void durationHandle(TableColumn.CellEditEvent<TermInTable, String> event) {
        int oldValue = getMonth(event.getOldValue());
        TermInTable row = event.getRowValue();
        int newValue;
        try {
            newValue = getMonth(event.getNewValue());
        } catch (NumberFormatException e) {
            String info = "数字格式错误！只能选择6月、1年、2年、3年或5年！";
            Alerts.expectionAlert("更新失败", "格式错误", info, e);
            row.setDuration(0);
            row.setDuration(oldValue);
            return;
        }
        //执行更新
        try {
            SqlOperator.getInstance().updateOne(String.valueOf(row.getId()), "duration", String.valueOf(newValue), "定期存款信息表");
            row.setDuration(newValue);
        } catch (SQLException e) {
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setDuration(0);
            row.setDuration(oldValue);
        }
    }

    private static int getMonth(String str) throws NumberFormatException {
        String number = str.replace("年", "").replace("月", "");
        if (str.endsWith("月")) {
            return Integer.valueOf(number);
        } else {
            return Integer.valueOf(number) * 12;
        }
    }

    private static void interestHandle(TableColumn.CellEditEvent<TermInTable, String> event) {
        String oldValue = event.getOldValue();
        String newValue = event.getNewValue();
        TermInTable row = event.getRowValue();
        //执行更新
        try {
            SqlOperator.getInstance().updateOne(String.valueOf(row.getId()), "interest", event.getNewValue(), "定期存款信息表");
            row.setInterest(Float.valueOf(newValue));
        } catch (SQLException e) {
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setInterest(0);
            row.setInterest(Float.valueOf(oldValue));
        }
    }

    private static void remarkHandle(TableColumn.CellEditEvent<TermInTable, String> event) {
        String oldValue = event.getOldValue();
        String newValue = event.getNewValue();
        TermInTable row = event.getRowValue();
        try {
            SqlOperator.getInstance().updateOne(String.valueOf(row.getId()), "remark", newValue, "定期存款信息表");
            row.setRemark(newValue);
        } catch (SQLException e) {//如果输入的数据格式错误
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setRemark("");
            row.setRemark(oldValue);
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

    public int getDuration() {
        return duration.get();
    }

    public IntegerProperty durationProperty() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration.set(duration);
    }

    public float getInterest() {
        return interest.get();
    }

    public FloatProperty interestProperty() {
        return interest;
    }

    public void setInterest(float interest) {
        this.interest.set(interest);
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
