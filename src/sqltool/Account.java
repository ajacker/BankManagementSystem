package sqltool;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import main.MainWindow;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ajacker
 */
public class Account {
    private enum Identity {
        //未选择或无意义
        nothing,
        //用户
        user,
        //管理员
        admin,
        //超级管理员
        su
    }

    private int id;
    private Identity identity;
    private String username;
    private String password;
    private User user;
    private static final Account ACCOUNT = new Account();

    private Account() {
        this.user = null;
        this.id = 0;
        this.identity = Identity.nothing;
        this.username = "";
        this.password = "";
    }

    public static Account getInstance() {
        return ACCOUNT;
    }

    public float getRate(String type, int month) throws SQLException {
        ResultSet rs = SqlOperator.getInstance().selectAllFrom("关键信息表");
        rs.next();
        if ("定期".equals(type)) {
            String column = "term_" + month;
            return rs.getFloat(column);
        } else {
            return rs.getFloat("current");
        }
    }

    public boolean checkLogin() throws SQLException {
        String sql = "";
        switch (identity) {
            case user:
                sql = "select id from `用户账户` where username=? and password=?";
                break;
            case admin:
                sql = "select id from `管理员账户` where username=? and password=?";
                break;
            case su:
                sql = "select * from `关键信息表` where username=? and password=?";
                break;
            default:
                break;
        }

        SqlOperator op = SqlOperator.getInstance();
        PreparedStatement ps = op.getConnection().prepareStatement(sql);
        ps.setString(1, this.username);
        ps.setString(2, this.password);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            if (identity == Identity.su) {
                this.id = -1;
            } else {
                this.id = rs.getInt(1);
            }
            this.user = new User(id);
            rs.close();
            ps.close();
            return true;
        } else {
            rs.close();
            ps.close();
            return false;
        }
    }

    public void updateBalance() {
        Label currentBalance = (Label) MainWindow.fxmlLoader.getNamespace().get("info_current");
        Label termBalance = (Label) MainWindow.fxmlLoader.getNamespace().get("info_term");
        currentBalance.setText(String.valueOf(user.getCurrentBalance()));
        termBalance.setText(String.valueOf(user.getTermBalance()));

    }

    public void onLogin() {
        Menu service = (Menu) MainWindow.fxmlLoader.getNamespace().get("menu_service");
        service.setDisable(false);
        MenuItem login = (MenuItem) MainWindow.fxmlLoader.getNamespace().get("menu_login");
        login.setDisable(true);
        MenuItem logout = (MenuItem) MainWindow.fxmlLoader.getNamespace().get("menu_logout");
        logout.setDisable(false);
        Label identity = (Label) MainWindow.fxmlLoader.getNamespace().get("info_identity");
        Label username = (Label) MainWindow.fxmlLoader.getNamespace().get("info_username");
        Label name = (Label) MainWindow.fxmlLoader.getNamespace().get("info_name");
        Label sex = (Label) MainWindow.fxmlLoader.getNamespace().get("info_sex");
        Label tel = (Label) MainWindow.fxmlLoader.getNamespace().get("info_tel");
        Label currentBalance = (Label) MainWindow.fxmlLoader.getNamespace().get("info_current");
        Label termBalance = (Label) MainWindow.fxmlLoader.getNamespace().get("info_term");
        Menu manage = (Menu) MainWindow.fxmlLoader.getNamespace().get("manageMenu");
        MenuItem deleteRow = (MenuItem) MainWindow.fxmlLoader.getNamespace().get("deleteRowMenuItem");
        MenuItem changepwd = (MenuItem) MainWindow.fxmlLoader.getNamespace().get("changePwdMenuItem");
        MenuItem userAccountMenuItem = (MenuItem) MainWindow.fxmlLoader.getNamespace().get("userAccountMenuItem");
        MenuItem adminAccountMenuItem = (MenuItem) MainWindow.fxmlLoader.getNamespace().get("adminAccountMenuItem");
        MenuItem calInterestMenuItem = (MenuItem) MainWindow.fxmlLoader.getNamespace().get("calInterestMenuItem");
        MenuItem addUserMenuItem = (MenuItem) MainWindow.fxmlLoader.getNamespace().get("addUserMenuItem");
        Button calBtn = (Button) MainWindow.fxmlLoader.getNamespace().get("calBtn");
        Button deleteBtn = (Button) MainWindow.fxmlLoader.getNamespace().get("deleteBtn");
        Button calInterestBtn = (Button) MainWindow.fxmlLoader.getNamespace().get("calInterestBtn");
        identity.setText(getIdentity());
        username.setText(getUsername());
        AnchorPane paneInfo = (AnchorPane) MainWindow.fxmlLoader.getNamespace().get("paneInfo");
        AnchorPane paneBalance = (AnchorPane) MainWindow.fxmlLoader.getNamespace().get("paneBalance");
        switch (this.identity) {
            case user:
                name.setText(user.getName());
                sex.setText(user.getSex());
                tel.setText(user.getTel());
                changepwd.setDisable(false);
                currentBalance.setText(String.valueOf(user.getCurrentBalance()));
                termBalance.setText(String.valueOf(user.getTermBalance()));
                calBtn.setVisible(true);
                addUserMenuItem.setDisable(true);
                break;
            case admin:
                name.setText(user.getName());
                sex.setText(user.getSex());
                tel.setText(user.getTel());
                manage.setVisible(true);
                userAccountMenuItem.setDisable(false);
                changepwd.setDisable(false);
                paneBalance.setVisible(false);
                deleteRow.setDisable(false);
                //calBtn.setVisible(true);
                deleteBtn.setVisible(true);
                addUserMenuItem.setDisable(false);
                break;
            case su:
                manage.setVisible(true);
                userAccountMenuItem.setDisable(false);
                adminAccountMenuItem.setDisable(false);
                calInterestMenuItem.setDisable(false);
                changepwd.setDisable(false);
                paneInfo.setVisible(false);
                paneBalance.setVisible(false);
                deleteRow.setDisable(false);
                //calBtn.setVisible(true);
                deleteBtn.setVisible(true);
                calInterestBtn.setVisible(true);
                addUserMenuItem.setDisable(false);
                break;
            default:
        }


    }

    public void onLogout() {
        this.id = 0;
        this.user = null;
        this.identity = Identity.nothing;
        this.username = "";
        this.password = "";
        String str = "NULL";
        AnchorPane paneInfo = (AnchorPane) MainWindow.fxmlLoader.getNamespace().get("paneInfo");
        paneInfo.setVisible(true);
        AnchorPane panebalance = (AnchorPane) MainWindow.fxmlLoader.getNamespace().get("paneBalance");
        panebalance.setVisible(true);
        Label identity = (Label) MainWindow.fxmlLoader.getNamespace().get("info_identity");
        Label username = (Label) MainWindow.fxmlLoader.getNamespace().get("info_username");
        Label name = (Label) MainWindow.fxmlLoader.getNamespace().get("info_name");
        Label sex = (Label) MainWindow.fxmlLoader.getNamespace().get("info_sex");
        Label tel = (Label) MainWindow.fxmlLoader.getNamespace().get("info_tel");
        Label currentBalance = (Label) MainWindow.fxmlLoader.getNamespace().get("info_current");
        Label termBalance = (Label) MainWindow.fxmlLoader.getNamespace().get("info_term");
        Menu manage = (Menu) MainWindow.fxmlLoader.getNamespace().get("manageMenu");
        MenuItem deleteRow = (MenuItem) MainWindow.fxmlLoader.getNamespace().get("deleteRowMenuItem");
        MenuItem changepwd = (MenuItem) MainWindow.fxmlLoader.getNamespace().get("changePwdMenuItem");
        MenuItem userAccountMenuItem = (MenuItem) MainWindow.fxmlLoader.getNamespace().get("userAccountMenuItem");
        MenuItem adminAccountMenuItem = (MenuItem) MainWindow.fxmlLoader.getNamespace().get("adminAccountMenuItem");
        MenuItem calInterestMenuItem = (MenuItem) MainWindow.fxmlLoader.getNamespace().get("calInterestMenuItem");
        MenuItem addUserMenuItem = (MenuItem) MainWindow.fxmlLoader.getNamespace().get("addUserMenuItem");
        Button calBtn = (Button) MainWindow.fxmlLoader.getNamespace().get("calBtn");
        Button deleteBtn = (Button) MainWindow.fxmlLoader.getNamespace().get("deleteBtn");
        Button calInterestBtn = (Button) MainWindow.fxmlLoader.getNamespace().get("calInterestBtn");

        identity.setText(str);
        username.setText(str);
        name.setText(str);
        sex.setText(str);
        tel.setText(str);
        currentBalance.setText(str);
        termBalance.setText(str);
        changepwd.setDisable(true);
        deleteRow.setDisable(true);
        manage.setVisible(false);
        userAccountMenuItem.setDisable(true);
        adminAccountMenuItem.setDisable(true);
        calInterestMenuItem.setDisable(true);
        calBtn.setVisible(false);
        deleteBtn.setVisible(false);
        calInterestBtn.setVisible(false);
        addUserMenuItem.setDisable(true);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentity() {
        switch (identity) {
            case user:
                return "普通用户";
            case admin:
                return "银行管理员";
            case su:
                return "超级管理员";
            default:
                return "啥也不是";
        }
    }

    public void setIdentity(String identity) {
        switch (identity) {
            case "用户":
                this.identity = Identity.user;
                break;
            case "管理员":
                this.identity = Identity.admin;
                break;
            case "su":
                this.identity = Identity.su;
                break;
            default:
                this.identity = Identity.nothing;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
