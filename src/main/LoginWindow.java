package main;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sqltool.Account;

public class LoginWindow extends Application {
    static Stage stage;
    static FXMLLoader fxmlLoader;

    @Override
    public void start(Stage primaryStage) throws Exception {
        fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = fxmlLoader.load();
        ChoiceBox choiceBox = (ChoiceBox) root.lookup("#identityChoiceBox");
        choiceBox.getSelectionModel().selectedItemProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
            Account.getInstance().setIdentity(newValue);
        });

        stage = primaryStage;
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setTitle("登录");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.show();


    }
}
