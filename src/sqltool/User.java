package sqltool;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class User {
    private int id;
    private Timestamp createTime;
    private String name;
    private String sex;
    private String tel;
    private float currentBalance;
    private float termBalance;

    public User(int id) {
        ResultSet rs = SqlOperator.getInstance().selectAllFrom("用户信息表", id);
        try {
            if (rs.next()) {
                this.id = rs.getInt("id");
                this.createTime = rs.getTimestamp("create_time");
                this.name = rs.getString("name");
                this.sex = rs.getString("sex");
                this.tel = rs.getString("tel");
                this.currentBalance = rs.getFloat("current_balance");
                this.termBalance = rs.getFloat("term_balance");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            this.id = 0;
            this.createTime = null;
            this.name = "";
            this.sex = "";
            this.tel = "";
            this.currentBalance = 0;
            this.termBalance = 0;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public float getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(float currentBalance) {
        this.currentBalance = currentBalance;
    }

    public float getTermBalance() {
        return termBalance;
    }

    public void setTermBalance(float termBalance) {
        this.termBalance = termBalance;
    }
}
