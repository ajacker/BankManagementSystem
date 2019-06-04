package main;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sqltool.Account;
import sqltool.SqlOperator;
import tool.Alerts;

import java.sql.SQLException;

public class CurrentOutController {
    public Button exitBtn;
    public AnchorPane rootPane;
    public TextField userIdTextField;
    public TextField moneyTextField;
    public TextField remarkTextField;

    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    public void insertCurrentOut(ActionEvent actionEvent) {
        String rows = "(user_id,money,remark)";
        String values = "(" + userIdTextField.getText() + "," + moneyTextField.getText() + ",'" + remarkTextField.getText() + "')";
        try {
            SqlOperator.getInstance().insertOne("活期取款信息表", rows, values);
            MainController controller = MainWindow.fxmlLoader.getController();
            controller.queryCurrentOut(null);
            Account.getInstance().checkLogin();
            Account.getInstance().updateBalance();
        } catch (SQLException e) {
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("插入失败", "数据库错误", info, e);
        }
    }
}
