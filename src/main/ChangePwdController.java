package main;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sqltool.Account;
import sqltool.SqlOperator;
import tool.Alerts;

import java.sql.SQLException;

@SuppressWarnings("ALL")
public class ChangePwdController {
    public AnchorPane rootPane;
    public TextField usernameTextField;
    public TextField oldpwdTextField;
    public TextField newpwdTextField;

    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }


    public void changepwd(ActionEvent actionEvent) {
        String id = String.valueOf(Account.getInstance().getId());
        if (oldpwdTextField.getText().equals(Account.getInstance().getPassword())) {
            switch (Account.getInstance().getIdentity()) {
                case "普通用户":
                    try {
                        SqlOperator.getInstance().updateOne(id, "username", usernameTextField.getText(), "用户账户");
                    } catch (SQLException e) {
                        String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
                        Alerts.expectionAlert("修改用户名失败", "数据库错误", info, e);
                        return;
                    }
                    try {
                        SqlOperator.getInstance().updateOne(id, "password", newpwdTextField.getText(), "用户账户");
                    } catch (SQLException e) {
                        String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
                        Alerts.expectionAlert("修改密码失败", "数据库错误", info, e);
                        return;
                    }
                    try {
                        Account.getInstance().checkLogin();
                        Account.getInstance().onLogin();
                    } catch (SQLException e) {
                        String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
                        Alerts.expectionAlert("更新信息失败", "数据库错误", info, e);
                        return;
                    }
                    break;
                case "银行管理员":
                    try {
                        SqlOperator.getInstance().updateOne(id, "username", usernameTextField.getText(), "管理员账户");
                    } catch (SQLException e) {
                        String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
                        Alerts.expectionAlert("修改用户名失败", "数据库错误", info, e);
                        return;
                    }
                    try {
                        SqlOperator.getInstance().updateOne(id, "password", newpwdTextField.getText(), "管理员账户");
                    } catch (SQLException e) {
                        String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
                        Alerts.expectionAlert("修改密码失败", "数据库错误", info, e);
                        return;
                    }
                    try {
                        Account.getInstance().checkLogin();
                        Account.getInstance().onLogin();
                    } catch (SQLException e) {
                        String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
                        Alerts.expectionAlert("更新信息失败", "数据库错误", info, e);
                        return;
                    }
                    break;
                case "超级管理员":
                    try {
                        SqlOperator.getInstance().updateOne("username", usernameTextField.getText(), "关键信息表");
                    } catch (SQLException e) {
                        String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
                        Alerts.expectionAlert("修改用户名失败", "数据库错误", info, e);
                        return;
                    }
                    try {
                        SqlOperator.getInstance().updateOne("password", newpwdTextField.getText(), "关键信息表");
                    } catch (SQLException e) {
                        String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
                        Alerts.expectionAlert("修改密码失败", "数据库错误", info, e);
                        return;
                    }
                    try {
                        Account.getInstance().checkLogin();
                        Account.getInstance().onLogin();
                    } catch (SQLException e) {
                        String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
                        Alerts.expectionAlert("更新信息失败", "数据库错误", info, e);
                        return;
                    }
                    break;
                default:
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "修改成功！");
            alert.setHeaderText(null);
            alert.showAndWait();
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "旧密码错误！");
            alert.setHeaderText("修改失败");
            alert.show();
        }
    }
}
