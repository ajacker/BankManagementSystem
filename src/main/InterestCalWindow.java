package main;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * @author ajacker
 */
public class InterestCalWindow extends Application {
    static Stage stage;
    static FXMLLoader fxmlLoader;

    @Override
    public void start(Stage primaryStage) throws Exception {
        fxmlLoader = new FXMLLoader(getClass().getResource("interestcal.fxml"));
        Parent root = fxmlLoader.load();
        stage = primaryStage;
        //初始化选择框逻辑
        ChoiceBox typeChoiceBox = (ChoiceBox) root.lookup("#typeChoiceBox");
        ChoiceBox durationChoiceBox = (ChoiceBox) root.lookup("#durationChoiceBox");
        typeChoiceBox.getSelectionModel().selectedItemProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
            if ("定期".equals(newValue)) {
                durationChoiceBox.setDisable(false);
            } else {
                durationChoiceBox.setDisable(true);
                durationChoiceBox.getSelectionModel().clearSelection();
            }
        });

        //初始化为今日
        DatePicker inDatePicker = (DatePicker) root.lookup("#inDatePicker");
        DatePicker outDatePicker = (DatePicker) root.lookup("#outDatePicker");
        inDatePicker.setValue(LocalDate.now());
        outDatePicker.setValue(LocalDate.now());

        primaryStage.setTitle("利息计算器");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 329, 303));
        primaryStage.show();


    }
}
