package main;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import sqltool.Account;
import sqltool.SqlOperator;
import tables.*;
import tool.Alerts;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ajacker
 */
@SuppressWarnings("ALL")
public class MainController {
    public TableView tableview;
    public MenuItem menu_logout;
    public MenuItem menu_login;
    public Menu menu_service;

    public void exitApplication(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void loginAccount(ActionEvent actionEvent) {
        LoginWindow window = new LoginWindow();
        try {
            window.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void insertCurrentIn(ActionEvent actionEvent) {
        CurrentInWindow window = new CurrentInWindow();
        try {
            window.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertCurrentOut(ActionEvent actionEvent) {
        CurrentOutWindow window = new CurrentOutWindow();
        try {
            window.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertTermIn(ActionEvent actionEvent) {
        TermInWindow window = new TermInWindow();
        try {
            window.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertTermOut(ActionEvent actionEvent) {
        TermOutWindow window = new TermOutWindow();
        try {
            window.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openInterestCal(ActionEvent actionEvent) {
        InterestCalWindow window = new InterestCalWindow();
        try {
            window.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteRow(ActionEvent actionEvent) {

        Object firstSelectedItem = tableview.getSelectionModel().getSelectedItem();
        if (firstSelectedItem == null) {
            return;
        }
        if (firstSelectedItem instanceof CurrentInTable) {
            ObservableList<CurrentInTable> selectedItems = tableview.getSelectionModel().getSelectedItems();
            ObservableList<CurrentInTable> allItems = tableview.getItems();
            StringBuffer ids = new StringBuffer();
            ids.append("(");
            for (int i = 0; i < selectedItems.size(); i++) {
                CurrentInTable item = selectedItems.get(i);
                if (i == 0) {
                    ids.append(item.getId());
                } else {
                    ids.append(",").append(item.getId());
                }
            }
            ids.append(")");
            try {
                SqlOperator.getInstance().deleteSome("活期存款信息表", ids.toString());
                allItems.removeAll(selectedItems);
            } catch (SQLException e) {
                String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
                Alerts.expectionAlert("删除失败", "数据库错误", info, e);
            }
        } else if (firstSelectedItem instanceof CurrentOutTable) {
            ObservableList<CurrentOutTable> selectedItems = tableview.getSelectionModel().getSelectedItems();
            ObservableList<CurrentOutTable> allItems = tableview.getItems();
            StringBuffer ids = new StringBuffer();
            ids.append("(");
            for (int i = 0; i < selectedItems.size(); i++) {
                CurrentOutTable item = selectedItems.get(i);
                if (i == 0) {
                    ids.append(item.getId());
                } else {
                    ids.append(",").append(item.getId());
                }
            }
            ids.append(")");
            try {
                SqlOperator.getInstance().deleteSome("活期取款信息表", ids.toString());
                allItems.removeAll(selectedItems);
            } catch (SQLException e) {
                String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
                Alerts.expectionAlert("删除失败", "数据库错误", info, e);
            }
        } else if (firstSelectedItem instanceof TermInTable) {
            ObservableList<TermInTable> selectedItems = tableview.getSelectionModel().getSelectedItems();
            ObservableList<TermInTable> allItems = tableview.getItems();
            StringBuffer ids = new StringBuffer();
            ids.append("(");
            for (int i = 0; i < selectedItems.size(); i++) {
                TermInTable item = selectedItems.get(i);
                if (i == 0) {
                    ids.append(item.getId());
                } else {
                    ids.append(",").append(item.getId());
                }
            }
            ids.append(")");
            try {
                SqlOperator.getInstance().deleteSome("定期存款信息表", ids.toString());
                allItems.removeAll(selectedItems);
            } catch (SQLException e) {
                String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
                Alerts.expectionAlert("删除失败", "数据库错误", info, e);
            }
        } else if (firstSelectedItem instanceof TermOutTable) {
            ObservableList<TermOutTable> selectedItems = tableview.getSelectionModel().getSelectedItems();
            ObservableList<TermOutTable> allItems = tableview.getItems();
            StringBuffer ids = new StringBuffer();
            ids.append("(");
            for (int i = 0; i < selectedItems.size(); i++) {
                TermOutTable item = selectedItems.get(i);
                if (i == 0) {
                    ids.append(item.getId());
                } else {
                    ids.append(",").append(item.getId());
                }
            }
            ids.append(")");
            try {
                SqlOperator.getInstance().deleteSome("定期取款信息表", ids.toString());
                allItems.removeAll(selectedItems);
            } catch (SQLException e) {
                String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
                Alerts.expectionAlert("删除失败", "数据库错误", info, e);
            }
        } else if (firstSelectedItem instanceof UserInfoTable) {
            ObservableList<UserAccountTable> selectedItems = tableview.getSelectionModel().getSelectedItems();
            ObservableList<UserAccountTable> allItems = tableview.getItems();
            StringBuffer ids = new StringBuffer();
            ids.append("(");
            for (int i = 0; i < selectedItems.size(); i++) {
                UserAccountTable item = selectedItems.get(i);
                if (i == 0) {
                    ids.append(item.getId());
                } else {
                    ids.append(",").append(item.getId());
                }
            }
            ids.append(")");
            try {
                SqlOperator.getInstance().deleteSome("用户信息表", ids.toString());
                allItems.removeAll(selectedItems);
            } catch (SQLException e) {
                String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
                Alerts.expectionAlert("删除失败", "数据库错误", info, e);
            }
        } else if (firstSelectedItem instanceof AdminInfoTable) {
            ObservableList<AdminAccountTable> selectedItems = tableview.getSelectionModel().getSelectedItems();
            ObservableList<AdminAccountTable> allItems = tableview.getItems();
            StringBuffer ids = new StringBuffer();
            ids.append("(");
            for (int i = 0; i < selectedItems.size(); i++) {
                AdminAccountTable item = selectedItems.get(i);
                if (i == 0) {
                    ids.append(item.getId());
                } else {
                    ids.append(",").append(item.getId());
                }
            }
            ids.append(")");
            try {
                SqlOperator.getInstance().deleteSome("管理员信息表", ids.toString());
                allItems.removeAll(selectedItems);
            } catch (SQLException e) {
                String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
                Alerts.expectionAlert("删除失败", "数据库错误", info, e);
            }
        } else {
            return;
        }
    }

    public void queryCurrentIn(ActionEvent actionEvent) throws SQLException {
        ResultSet rs = null;
        switch (Account.getInstance().getIdentity()) {
            case "普通用户":
                rs = SqlOperator.getInstance().selectAllFrom("活期存款信息表", Account.getInstance().getId());
                break;
            case "银行管理员":
                tableview.setEditable(true);
                rs = SqlOperator.getInstance().selectAllFrom("活期存款信息表");
                break;
            case "超级管理员":
                tableview.setEditable(true);
                rs = SqlOperator.getInstance().selectAllFrom("活期存款信息表");
                break;
            default:
        }
        ObservableList<Object> list = FXCollections.observableArrayList();
        if (rs != null) {
            while (rs.next()) {
                CurrentInTable table = new CurrentInTable(rs.getInt(1), rs.getInt(2), rs.getString(3)
                        , rs.getFloat(4), rs.getFloat(5), rs.getString(6));
                list.add(table);
            }
        } else {
            System.out.println("null");
        }
        if (rs != null) {
            rs.close();
        }
        tableview.getColumns().setAll(CurrentInTable.getColumns());
        tableview.setItems(list);
    }

    public void queryCurrentOut(ActionEvent actionEvent) throws SQLException {
        ResultSet rs = null;
        switch (Account.getInstance().getIdentity()) {
            case "普通用户":
                rs = SqlOperator.getInstance().selectAllFrom("活期取款信息表", Account.getInstance().getId());
                break;
            case "银行管理员":
                tableview.setEditable(true);
                rs = SqlOperator.getInstance().selectAllFrom("活期取款信息表");
                break;
            case "超级管理员":
                tableview.setEditable(true);
                rs = SqlOperator.getInstance().selectAllFrom("活期取款信息表");
                break;
            default:
        }
        ObservableList<Object> list = FXCollections.observableArrayList();
        if (rs != null) {
            while (rs.next()) {
                CurrentOutTable table = new CurrentOutTable(rs.getInt(1), rs.getInt(2), rs.getString(3)
                        , rs.getFloat(4), rs.getString(5));
                list.add(table);
            }
        } else {
            System.out.println("null");
        }
        if (rs != null) {
            rs.close();
        }
        tableview.getColumns().setAll(CurrentOutTable.getColumns());
        tableview.setItems(list);
    }

    public void queryTermIn(ActionEvent actionEvent) throws SQLException {
        ResultSet rs = null;
        switch (Account.getInstance().getIdentity()) {
            case "普通用户":
                rs = SqlOperator.getInstance().selectAllFrom("定期存款信息表", Account.getInstance().getId());
                break;
            case "银行管理员":
                tableview.setEditable(true);
                rs = SqlOperator.getInstance().selectAllFrom("定期存款信息表");
                break;
            case "超级管理员":
                tableview.setEditable(true);
                rs = SqlOperator.getInstance().selectAllFrom("定期存款信息表");
                break;
            default:
        }
        ObservableList<Object> list = FXCollections.observableArrayList();
        if (rs != null) {
            while (rs.next()) {
                TermInTable table = new TermInTable(rs.getInt(1), rs.getInt(2), rs.getString(3)
                        , rs.getFloat(4), rs.getInt(5), rs.getFloat(6), rs.getString(7));
                list.add(table);
            }
        } else {
            System.out.println("null");
        }
        if (rs != null) {
            rs.close();
        }
        tableview.getColumns().setAll(TermInTable.getColumns());
        tableview.setItems(list);
    }

    public void queryTermOut(ActionEvent actionEvent) throws SQLException {
        ResultSet rs = null;
        switch (Account.getInstance().getIdentity()) {
            case "普通用户":
                rs = SqlOperator.getInstance().selectAllFrom("定期取款信息表", Account.getInstance().getId());
                break;
            case "银行管理员":
                tableview.setEditable(true);
                rs = SqlOperator.getInstance().selectAllFrom("定期取款信息表");
                break;
            case "超级管理员":
                tableview.setEditable(true);
                rs = SqlOperator.getInstance().selectAllFrom("定期取款信息表");
                break;
            default:
        }
        ObservableList<Object> list = FXCollections.observableArrayList();
        if (rs != null) {
            while (rs.next()) {
                TermOutTable table = new TermOutTable(rs.getInt(1), rs.getInt(2), rs.getInt(3)
                        , rs.getString(4), rs.getFloat(5), rs.getString(6));
                list.add(table);
            }
        } else {
            System.out.println("null");
        }
        if (rs != null) {
            rs.close();
        }
        tableview.getColumns().setAll(TermOutTable.getColumns());
        tableview.setItems(list);
    }

    public void queryCurrentInUnclear(ActionEvent actionEvent) throws SQLException {
        ResultSet rs = null;
        switch (Account.getInstance().getIdentity()) {
            case "普通用户":
                rs = SqlOperator.getInstance().selectAllFrom("未结算活期账单", Account.getInstance().getId());
                break;
            case "银行管理员":
                tableview.setEditable(true);
                rs = SqlOperator.getInstance().selectAllFrom("未结算活期账单");
                break;
            case "超级管理员":
                tableview.setEditable(true);
                rs = SqlOperator.getInstance().selectAllFrom("未结算活期账单");
                break;
            default:
        }
        ObservableList<Object> list = FXCollections.observableArrayList();
        if (rs != null) {
            while (rs.next()) {
                CurrentInUnclearTable table = new CurrentInUnclearTable(rs.getInt(1), rs.getString(2), rs.getInt(3)
                        , rs.getFloat(4), rs.getFloat(5));
                list.add(table);
            }
        } else {
            System.out.println("null data");
        }
        if (rs != null) {
            rs.close();
        }
        tableview.getColumns().setAll(CurrentInUnclearTable.getColumns());
        tableview.setItems(list);
    }

    public void queryTermInUnclear(ActionEvent actionEvent) throws SQLException {
        ResultSet rs = null;
        switch (Account.getInstance().getIdentity()) {
            case "普通用户":
                rs = SqlOperator.getInstance().selectAllFrom("未取款定期账单", Account.getInstance().getId());
                break;
            case "银行管理员":
                tableview.setEditable(true);
                rs = SqlOperator.getInstance().selectAllFrom("未取款定期账单");
                break;
            case "超级管理员":
                tableview.setEditable(true);
                rs = SqlOperator.getInstance().selectAllFrom("未取款定期账单");
                break;
            default:
        }
        ObservableList<Object> list = FXCollections.observableArrayList();
        if (rs != null) {
            while (rs.next()) {
                TermInUnclearTable table = new TermInUnclearTable(rs.getInt(1), rs.getString(2), rs.getInt(3)
                        , rs.getFloat(4), rs.getFloat(5));
                list.add(table);
            }
        } else {
            System.out.println("null");
        }
        if (rs != null) {
            rs.close();
        }
        tableview.getColumns().setAll(TermInUnclearTable.getColumns());
        tableview.setItems(list);
    }

    public void updateRate(ActionEvent actionEvent) throws SQLException {
        ResultSet rs = SqlOperator.getInstance().selectAllFrom("利率");
        if ("超级管理员".equals(Account.getInstance().getIdentity())) {
            tableview.setEditable(true);
        }
        ObservableList<Object> list = FXCollections.observableArrayList();
        if (rs != null) {
            while (rs.next()) {
                InterestRateTable table = new InterestRateTable(rs.getFloat(1), rs.getFloat(2), rs.getFloat(3)
                        , rs.getFloat(4), rs.getFloat(5), rs.getFloat(6));
                list.add(table);
            }
        } else {
            System.out.println("null");
        }
        if (rs != null) {
            rs.close();
        }
        tableview.getColumns().setAll(InterestRateTable.getColumns());
        tableview.setItems(list);
    }

    public void userAccount(ActionEvent actionEvent) throws SQLException {
        ResultSet rs = SqlOperator.getInstance().selectAllFrom("用户信息表");
        ObservableList<Object> list = FXCollections.observableArrayList();
        if (rs != null) {
            while (rs.next()) {
                UserInfoTable table = new UserInfoTable(rs.getInt(1), rs.getString(2), rs.getString(3)
                        , rs.getString(4), rs.getString(5), rs.getString(8), rs.getString(9));
                list.add(table);
            }
        } else {
            System.out.println("null");
        }
        if (rs != null) {
            rs.close();
        }
        tableview.setEditable(true);
        tableview.getColumns().setAll(UserInfoTable.getColumns());
        tableview.setItems(list);
    }

    public void adminAccount(ActionEvent actionEvent) throws SQLException {
        ResultSet rs = SqlOperator.getInstance().selectAllFrom("管理员信息表");
        ObservableList<Object> list = FXCollections.observableArrayList();
        if (rs != null) {
            while (rs.next()) {
                AdminInfoTable table = new AdminInfoTable(rs.getInt(1), rs.getString(2), rs.getString(3)
                        , rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
                list.add(table);
            }
        } else {
            System.out.println("null");
        }
        if (rs != null) {
            rs.close();
        }
        tableview.setEditable(true);
        tableview.getColumns().setAll(AdminInfoTable.getColumns());
        tableview.setItems(list);
    }

    public void logoutAccount(ActionEvent actionEvent) {
        Account.getInstance().onLogout();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "注销成功！请重新登录！");
        alert.setHeaderText("注销");
        alert.show();
        tableview.setEditable(false);
        tableview.setItems(null);
        tableview.getColumns().remove(0, tableview.getColumns().size());
        menu_service.setDisable(true);
        menu_logout.setDisable(true);
        menu_login.setDisable(false);
    }

    public void calInterest(ActionEvent actionEvent) {
        try {
            SqlOperator.getInstance().callMethod();
            queryTermIn(null);
        } catch (SQLException e) {
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("结算失败", "数据库错误", info, e);
        }
    }

    public void changePassword(ActionEvent actionEvent) {
        ChangePwdWindow window = new ChangePwdWindow();
        try {
            window.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addUserInfo(ActionEvent actionEvent) {
        AddUserWindow window = new AddUserWindow();
        try {
            window.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
