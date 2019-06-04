package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import sqltool.Account;

/**
 * @author ajacker
 */
public class AddUserWindow extends Application {
    static Stage stage;
    static FXMLLoader fxmlLoader;

    @Override
    public void start(Stage primaryStage) throws Exception {
        fxmlLoader = new FXMLLoader(getClass().getResource("adduser.fxml"));
        Parent root = fxmlLoader.load();
        stage = primaryStage;
        CheckBox isadmin = (CheckBox) root.lookup("#isAdminCheckBox");
        if ("超级管理员".equals(Account.getInstance().getIdentity())) {
            isadmin.setVisible(true);
        } else {
            isadmin.setVisible(false);
        }

        primaryStage.setTitle("开户");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 300, 238));
        primaryStage.show();

    }
}
