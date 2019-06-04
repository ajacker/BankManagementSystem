package main;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sqltool.SqlOperator;
import tool.Alerts;

import java.sql.SQLException;

public class AddUserController {
    public AnchorPane rootPane;
    public TextField nameTextField;
    public ChoiceBox sexChoiceBox;
    public TextField telTextField;
    public TextField usernameTextField;
    public PasswordField passwordTextField;
    public CheckBox isAdminCheckBox;

    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    public void addUser(ActionEvent actionEvent) {
        int selectedIndex = sexChoiceBox.getSelectionModel().getSelectedIndex();
        String sex = "男";
        switch (selectedIndex) {
            case -1:
                Alert alert = new Alert(Alert.AlertType.ERROR, "请选择性别！");
                alert.setHeaderText("插入失败");
                alert.show();
                return;
            case 0:
                sex = "男";
                break;
            case 1:
                sex = "女";
                break;
            default:
        }
        String rows = "(name,sex,tel,username,password)";
        String values = "('" + nameTextField.getText() + "','" + sex + "','" + telTextField.getText() + "','" +
                usernameTextField.getText() + "','" + passwordTextField.getText() + "')";
        String table = "用户信息表";
        if (isAdminCheckBox.isSelected()) {
            table = "管理员信息表";
        }
        try {
            SqlOperator.getInstance().insertOne(table, rows, values);
            MainController controller = MainWindow.fxmlLoader.getController();
            if (isAdminCheckBox.isSelected()){
                controller.adminAccount(null);
            }else {
                controller.userAccount(null);
            }

        } catch (SQLException e) {
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("插入失败", "数据库错误", info, e);
        }
    }
}
