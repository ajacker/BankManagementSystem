package main;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sqltool.Account;
import sqltool.SqlOperator;
import tool.Alerts;

import java.sql.SQLException;

public class TermInController {
    public Button exitBtn;
    public AnchorPane rootPane;
    public TextField userIdTextField;
    public TextField moneyTextField;
    public TextField remarkTextField;
    public ChoiceBox durationChoiceBox;

    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    public void insertTermIn(ActionEvent actionEvent) {
        int selectedIndex = durationChoiceBox.getSelectionModel().getSelectedIndex();
        int duration = 0;
        switch (selectedIndex) {
            case -1:
                Alert alert = new Alert(Alert.AlertType.ERROR, "请选择定期限！");
                alert.setHeaderText("插入失败");
                alert.show();
                return;
            case 0:
                duration = 6;
                break;
            case 1:
                duration = 12;
                break;
            case 2:
                duration = 24;
                break;
            case 3:
                duration = 36;
                break;
            case 4:
                duration = 60;
                break;
            default:
        }
        String rows = "(user_id,money,duration,remark)";
        String values = "(" + userIdTextField.getText() + "," + moneyTextField.getText() + ",'" + duration + "','" + remarkTextField.getText() + "')";
        try {
            SqlOperator.getInstance().insertOne("定期存款信息表", rows, values);
            MainController controller = MainWindow.fxmlLoader.getController();
            controller.queryTermIn(null);
            Account.getInstance().checkLogin();
            Account.getInstance().updateBalance();

        } catch (SQLException e) {
            String info = "SQLState:" + e.getSQLState() + "\n" + "ErrorCode:" + String.valueOf(e.getErrorCode()) + "\n" + "Message:" + e.getMessage();
            Alerts.expectionAlert("插入失败", "数据库错误", info, e);
        }
    }
}
