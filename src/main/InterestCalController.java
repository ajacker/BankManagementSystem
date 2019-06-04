package main;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sqltool.Account;

import java.sql.SQLException;
import java.time.LocalDate;

/**
 * @author ajacker
 */
@SuppressWarnings("ALL")
public class InterestCalController {
    public AnchorPane rootPane;
    public ChoiceBox durationChoiceBox;
    public ChoiceBox typeChoiceBox;
    public TextField moneyTextField;
    public DatePicker inDatePicker;
    public DatePicker outDatePicker;
    public Label rateLabel;
    public Label interestLabel;

    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }


    public void calculateInterest(ActionEvent actionEvent) {
        LocalDate inDate = inDatePicker.getValue();
        LocalDate outDate = outDatePicker.getValue();
        if (outDate.isBefore(inDate)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "取款日期不能在存款日期之前！");
            alert.setHeaderText("计算错误");
            alert.show();
            return;
        }
        //金额
        float money;
        try {
            money = Float.valueOf(moneyTextField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "金额格式错误！");
            alert.setHeaderText("计算错误");
            alert.show();
            return;
        }
        if (typeChoiceBox.getSelectionModel().getSelectedIndex() == -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "请选择存款类型！");
            alert.setHeaderText("计算错误");
            alert.show();
            return;
        }
        //实际天数
        int days = (int) (outDate.toEpochDay() - inDate.toEpochDay());


        String type = (String) typeChoiceBox.getSelectionModel().getSelectedItem();
        float currentRate = 0;
        try {
            currentRate = Account.getInstance().getRate("活期", 0);
            rateLabel.setText(String.format("%.2f%%", currentRate));
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "获取利率失败！");
            alert.setHeaderText("计算错误");
            alert.show();
            return;
        }
        float interest;
        if ("定期".equals(type)) {
            int duration = 0;
            switch (durationChoiceBox.getSelectionModel().getSelectedIndex()) {
                case -1:
                    Alert alert = new Alert(Alert.AlertType.ERROR, "请选择定期限！");
                    alert.setHeaderText("计算错误");
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
            //存够定期所需的天数
            int termDays = duration * 30;
            float termRate = 0;
            try {
                termRate = Account.getInstance().getRate((String) typeChoiceBox.getSelectionModel().getSelectedItem(), duration);
                rateLabel.setText(String.format("%.2f%%", termRate));
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "获取利率失败！");
                alert.setHeaderText("计算错误");
                alert.show();
                return;
            }
            //没存够定期按照活期算
            if (days < termDays) {
                interest = currentRate / 360 / 100 * days * money;
            } else {
                interest = termRate / 360 / 100 * termDays * money;
                interest += currentRate / 360 / 100 * (days - termDays) * money;
            }
        } else {
            interest = currentRate / 360 / 100 * days * money;
        }
        interestLabel.setText(String.format("%.2f", interest));

    }
}
