package main;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sqltool.Account;

import java.sql.SQLException;

public class LoginController {
    public TextField tfUsername;
    public PasswordField tfPassword;

    public void closeLoginWindow(ActionEvent actionEvent) {
        LoginWindow.stage.close();
    }

    public void onLoginPressed(ActionEvent actionEvent) {
        Account account = Account.getInstance();
        account.setUsername(tfUsername.getText());
        account.setPassword(tfPassword.getText());
        boolean result = false;
        try {
            result = account.checkLogin();
            System.out.println(result);
        } catch (SQLException e) {
            System.out.println("数据库错误");
            e.printStackTrace();
        }
        if (result) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, String.format("登录成功！%n身份：%s%nid:%010d", account.getIdentity(), account.getId()));
            alert.setHeaderText("登录成功");
            alert.showAndWait();
            LoginWindow.stage.close();
            Account.getInstance().onLogin();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "登录失败！\n请检查身份和账户密码！");
            alert.setHeaderText("登录失败");
            alert.showAndWait();
        }
    }
}
