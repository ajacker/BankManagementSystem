package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sqltool.Account;

/**
 * @author ajacker
 */
public class TermOutWindow extends Application {
    static Stage stage;
    static FXMLLoader fxmlLoader;

    @Override
    public void start(Stage primaryStage) throws Exception {
        fxmlLoader = new FXMLLoader(getClass().getResource("termout.fxml"));
        Parent root = fxmlLoader.load();
        stage = primaryStage;

        TextField userIdTextField = (TextField) root.lookup("#userIdTextField");
        if ("普通用户".equals(Account.getInstance().getIdentity())) {
            userIdTextField.setDisable(true);
            userIdTextField.setText(String.format("%010d", Account.getInstance().getId()));
        }
        primaryStage.setTitle("办理定期取款");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.show();


    }
}
