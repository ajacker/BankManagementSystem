package tables;

import javafx.beans.binding.Bindings;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.scene.control.TableColumn;
import sqltool.SqlOperator;
import tool.Alerts;
import tool.EditingCell;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class InterestRateTable {
    private final FloatProperty current = new SimpleFloatProperty();
    private final FloatProperty term6 = new SimpleFloatProperty();
    private final FloatProperty term12 = new SimpleFloatProperty();
    private final FloatProperty term24 = new SimpleFloatProperty();
    private final FloatProperty term36 = new SimpleFloatProperty();
    private final FloatProperty term60 = new SimpleFloatProperty();

    public InterestRateTable(float current, float term6, float term12, float term24, float term36, float term60) {
        setCurrent(current);
        setTerm6(term6);
        setTerm12(term12);
        setTerm24(term24);
        setTerm36(term36);
        setTerm60(term60);
    }

    public static List<TableColumn<InterestRateTable, String>> getColumns() {
        List<TableColumn<InterestRateTable, String>> columns = new ArrayList<>();
        //设置列名
        TableColumn<InterestRateTable, String> currentCol = new TableColumn<>("活期");
        TableColumn<InterestRateTable, String> term6Col = new TableColumn<>("定期6月");
        TableColumn<InterestRateTable, String> term12Col = new TableColumn<>("定期1年");
        TableColumn<InterestRateTable, String> term24Col = new TableColumn<>("定期2年");
        TableColumn<InterestRateTable, String> term36Col = new TableColumn<>("定期3年");
        TableColumn<InterestRateTable, String> term60Col = new TableColumn<>("定期5年");
        //设置显示方式
        currentCol.setCellValueFactory(param -> Bindings.createStringBinding(() -> {
            return String.format("%.2f%%", param.getValue().getCurrent());
        }, param.getValue().currentProperty()));
        term6Col.setCellValueFactory(param -> Bindings.createStringBinding(() -> {
            return String.format("%.2f%%", param.getValue().getTerm6());
        }, param.getValue().term6Property()));
        term12Col.setCellValueFactory(param -> Bindings.createStringBinding(() -> {
            return String.format("%.2f%%", param.getValue().getTerm12());
        }, param.getValue().term12Property()));
        term24Col.setCellValueFactory(param -> Bindings.createStringBinding(() -> {
            return String.format("%.2f%%", param.getValue().getTerm24());
        }, param.getValue().term24Property()));
        term36Col.setCellValueFactory(param -> Bindings.createStringBinding(() -> {
            return String.format("%.2f%%", param.getValue().getTerm36());
        }, param.getValue().term36Property()));
        term60Col.setCellValueFactory(param -> Bindings.createStringBinding(() -> {
            return String.format("%.2f%%", param.getValue().getTerm60());
        }, param.getValue().term60Property()));
        //添加到列集合
        columns.add(currentCol);
        columns.add(term6Col);
        columns.add(term12Col);
        columns.add(term24Col);
        columns.add(term36Col);
        columns.add(term60Col);
        for (TableColumn<InterestRateTable, String> column : columns) {
            column.setCellFactory((TableColumn<InterestRateTable, String> p) -> new EditingCell<>());
        }
        //设置编辑完成事件
        currentCol.setOnEditCommit(InterestRateTable::currentHandle);
        term6Col.setOnEditCommit(InterestRateTable::term6Handle);
        term12Col.setOnEditCommit(InterestRateTable::term12Handle);
        term24Col.setOnEditCommit(InterestRateTable::term24Handle);
        term36Col.setOnEditCommit(InterestRateTable::term36Handle);
        term60Col.setOnEditCommit(InterestRateTable::term60Handle);
        return columns;
    }


    private static void currentHandle(TableColumn.CellEditEvent<InterestRateTable, String> event) {
        float oldValue = Float.parseFloat(event.getOldValue().replace("%", ""));
        float newValue = Float.parseFloat(event.getNewValue().replace("%", ""));
        InterestRateTable row = event.getRowValue();
        try {
            SqlOperator.getInstance().updateInterest("current", String.valueOf(newValue));
            row.setCurrent(newValue);
        } catch (SQLException e) {//如果输入的数据格式错误
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setCurrent(0);
            row.setCurrent(oldValue);
        }
    }

    private static void term6Handle(TableColumn.CellEditEvent<InterestRateTable, String> event) {
        float oldValue = Float.parseFloat(event.getOldValue().replace("%", ""));
        float newValue = Float.parseFloat(event.getNewValue().replace("%", ""));
        InterestRateTable row = event.getRowValue();
        try {
            SqlOperator.getInstance().updateInterest("term_6", String.valueOf(newValue));
            row.setCurrent(newValue);
        } catch (SQLException e) {//如果输入的数据格式错误
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setCurrent(0);
            row.setCurrent(oldValue);
        }
    }

    private static void term12Handle(TableColumn.CellEditEvent<InterestRateTable, String> event) {
        float oldValue = Float.parseFloat(event.getOldValue().replace("%", ""));
        float newValue = Float.parseFloat(event.getNewValue().replace("%", ""));
        InterestRateTable row = event.getRowValue();
        try {
            SqlOperator.getInstance().updateInterest("term_12", String.valueOf(newValue));
            row.setCurrent(newValue);
        } catch (SQLException e) {//如果输入的数据格式错误
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setCurrent(0);
            row.setCurrent(oldValue);
        }
    }

    private static void term24Handle(TableColumn.CellEditEvent<InterestRateTable, String> event) {
        float oldValue = Float.parseFloat(event.getOldValue().replace("%", ""));
        float newValue = Float.parseFloat(event.getNewValue().replace("%", ""));
        InterestRateTable row = event.getRowValue();
        try {
            SqlOperator.getInstance().updateInterest("term_24", String.valueOf(newValue));
            row.setCurrent(newValue);
        } catch (SQLException e) {//如果输入的数据格式错误
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setCurrent(0);
            row.setCurrent(oldValue);
        }
    }

    private static void term36Handle(TableColumn.CellEditEvent<InterestRateTable, String> event) {
        float oldValue = Float.parseFloat(event.getOldValue().replace("%", ""));
        float newValue = Float.parseFloat(event.getNewValue().replace("%", ""));
        InterestRateTable row = event.getRowValue();
        try {
            SqlOperator.getInstance().updateInterest("term_36", String.valueOf(newValue));
            row.setCurrent(newValue);
        } catch (SQLException e) {//如果输入的数据格式错误
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setCurrent(0);
            row.setCurrent(oldValue);
        }
    }

    private static void term60Handle(TableColumn.CellEditEvent<InterestRateTable, String> event) {
        float oldValue = Float.parseFloat(event.getOldValue().replace("%", ""));
        float newValue = Float.parseFloat(event.getNewValue().replace("%", ""));
        InterestRateTable row = event.getRowValue();
        try {
            SqlOperator.getInstance().updateInterest("term_60", String.valueOf(newValue));
            row.setCurrent(newValue);
        } catch (SQLException e) {//如果输入的数据格式错误
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("更新失败", "数据库错误", info, e);
            row.setCurrent(0);
            row.setCurrent(oldValue);
        }
    }


    public float getCurrent() {
        return current.get();
    }

    public FloatProperty currentProperty() {
        return current;
    }

    public void setCurrent(float current) {
        this.current.set(current);
    }

    public float getTerm6() {
        return term6.get();
    }

    public FloatProperty term6Property() {
        return term6;
    }

    public void setTerm6(float term6) {
        this.term6.set(term6);
    }

    public float getTerm12() {
        return term12.get();
    }

    public FloatProperty term12Property() {
        return term12;
    }

    public void setTerm12(float term12) {
        this.term12.set(term12);
    }

    public float getTerm24() {
        return term24.get();
    }

    public FloatProperty term24Property() {
        return term24;
    }

    public void setTerm24(float term24) {
        this.term24.set(term24);
    }

    public float getTerm36() {
        return term36.get();
    }

    public FloatProperty term36Property() {
        return term36;
    }

    public void setTerm36(float term36) {
        this.term36.set(term36);
    }

    public float getTerm60() {
        return term60.get();
    }

    public FloatProperty term60Property() {
        return term60;
    }

    public void setTerm60(float term60) {
        this.term60.set(term60);
    }
}
