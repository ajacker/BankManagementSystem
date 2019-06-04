package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import sqltool.Account;

/**
 * @author ajacker
 */
public class MainWindow extends Application {
    public static Stage stage;
    public static Parent root;
    public static FXMLLoader fxmlLoader;

    @Override
    public void start(Stage primaryStage) throws Exception {
        fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
        root = fxmlLoader.load();
        stage = primaryStage;
        TableView tableView = (TableView) root.lookup("#tableview");
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Account.getInstance().onLogout();
        primaryStage.setTitle("银行储蓄管理系统");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
