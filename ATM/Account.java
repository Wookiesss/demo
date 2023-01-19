public class Account {
    private String id;//卡号
    private String name;//用户名
    private String password;//密码
    private double money;//余额
    private double limit;//每次提现限额

    //创建标准Javabean
    public Account() {
    }

    public Account(String id, String name, String password, double money, double limit) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.money = money;
        this.limit = limit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public String toString() {
        return "Account{id = " + id + ", name = " + name + ", password = " + password + ", money = " + money + ", limit = " + limit + "}";
    }
}
