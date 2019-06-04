package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sqltool.Account;

/**
 * @author ajacker
 */
public class ChangePwdWindow extends Application {
    static Stage stage;
    static FXMLLoader fxmlLoader;

    @Override
    public void start(Stage primaryStage) throws Exception {
        fxmlLoader = new FXMLLoader(getClass().getResource("changepwd.fxml"));
        Parent root = fxmlLoader.load();
        stage = primaryStage;

        TextField username = (TextField) root.lookup("#usernameTextField");
        username.setText(Account.getInstance().getUsername());

        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setTitle("修改登录信息");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.show();


    }
}
