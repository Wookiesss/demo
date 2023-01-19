import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ATMSystem {
    public static void main(String[] args) {
        ArrayList<Account> accounts = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        while (true) {
            homepage();
            int input = sc.nextInt();
            switch (input) {
                case 1:
                    login(accounts, sc);
                    break;
                case 2:
                    register(accounts, sc);
                    break;
                default:
                    System.out.println("输入有误，请重新输入：");
                    break;
            }
        }
    }
    private static void homepage(){
        System.out.println("***********************************");
        System.out.println("****     欢迎使用QG·ATM系统      ****");
        System.out.println("****       请选择相应功能        ****");
        System.out.println("****         1.账号登录         ****");
        System.out.println("****         2.账号开户         ****");
        System.out.println("***********************************");
    }

    private static void login(ArrayList<Account> accounts, Scanner sc) {
        //判断集合中是否有账号，无账号直接返回提示开户
        if(accounts.size()==0){
            System.out.println("当前系统无任何账号，请先开户！");
            return;
        }
        //如存在账号，进行登录操作
        System.out.println("***********账号登录***********");
        while(true){
            System.out.println("请输入登录卡号：");
            String id = sc.next();
            Account account = check(accounts,id);
            //判断卡号是否存在
            //卡号存在
            if( account!=null ){
                //输入密码
                System.out.println("请输入登录密码：");
                String password = sc.next();
                //判断密码是否正确
                if(account.getPassword().equals(password)){
                    System.out.println("登录成功，当前登录用户名为："+account.getName()+"先生/女生，卡号为："+account.getId());
                    commandMenu(sc,account,accounts);
                    return;
                }else{
                    System.out.println("密码错误，请重新输入");
                }

            }
            //卡号不存在
            else{
                System.out.println("输入卡号有误，请重新输入：");
            }

        }
    }

    //操作菜单
    private static void commandMenu(Scanner sc, Account account, ArrayList<Account> accounts){
        while(true){
            System.out.println("***********************************");
            System.out.println("****     欢迎使用QG·ATM系统      ****");
            System.out.println("****       请选择相应功能         ****");
            System.out.println("****      1.查询   2.存款        ****");
            System.out.println("****      3.取款   4.转账        ****");
            System.out.println("****      5.改密   6.注销        ****");
            System.out.println("****      7.退出                ****");
            System.out.println("***********************************");
            int input = sc.nextInt();
            switch (input){
                case 1:
                    show(account);
                    break;
                case 2:
                    deposit(sc,account);
                    break;
                case 3:
                    draw(sc,account);
                    break;
                case 4:
                    transfer(sc,account,accounts);
                    break;
                case 5:
                    updataPassword(sc,account);
                    break;
                case 6:
                    delete(sc,account,accounts);
                    return;
                case 7:
                    System.out.println("成功退出系统！");
                    return;
                default:
                    System.out.println("输入有误，请重新输入：");
                    break;
            }
        }
    }

    //查询账户信息
    private static void show(Account account){
        System.out.println("\n当前账户信息如下：");
        System.out.println("户主："+account.getName());
        System.out.println("卡号："+account.getId());
        System.out.println("余额："+account.getMoney());
        System.out.println("限额："+account.getLimit());
    }

    //存款功能
    private static void deposit(Scanner sc, Account account) {
        System.out.println("请输入存款金额：");
        double add = sc.nextDouble();
        //更新对象中余额信息
        account.setMoney(account.getMoney() + add);
        System.out.println("存款成功！");
        //展示存款后的账户信息
        show(account);
    }

    //取款功能
    private static void draw(Scanner sc, Account account) {
        //判断余额是否为0
        if(account.getMoney()<=0){
            System.out.println("抱歉，您的账户余额为0，无法取款！");
            return;
        }else{
            while(true) {
                //输入取款金额
                System.out.println("请输入取款金额，您的账户单次操作限额为：" + account.getLimit());
                double reduce = sc.nextDouble();
                //判断取款金额是否合法
                if (reduce > account.getLimit()){
                    System.out.println("您当前取款金额超过单次操作限额，请重新输入！");
                }else if(reduce < 0){
                    System.out.println("没有白嫖这个操作哦！");
                }else if(reduce > account.getMoney()) {
                    System.out.println("取款金额大于账户金额！请重新输入！");
                } else{
                    System.out.println("取款成功！");
                    account.setMoney(account.getMoney() - reduce);
                    //展示取款后的账户信息
                    show(account);
                    return;
                }
            }
        }
    }

    //转账功能
    private static void transfer(Scanner sc, Account account, ArrayList<Account> accounts) {
        //判断转账方账户余额是否合法
        if(account.getMoney()<=0){
            System.out.println("当前账户余额不足，无法转账！");
            return;
        }
        while(true){
            //输入收款方卡号
            System.out.println("请输入收款方账户卡号：");
            String payeeId = sc.next();

            //判断收款方卡号是否合法
            if(payeeId.equals(account.getId())){
                System.out.println("不能为自己转账，请重新输入对方账户卡号");
                continue;
            }
            //创建收款方对象
            Account payee = check(accounts,payeeId);
            if(payee == null){
                System.out.println("您输入的卡号不存在，请检查是否有误并重新输入");
            }else{
                //收款方卡号合法，进行户主姓名校验
                String payeeName = payee.getName();
                String name = "*" + payeeName.substring(1);
                System.out.println("为确保转账安全，请输入校验收款方姓氏"+name);
                String input = sc.next();
                //校验输入姓氏是否正确
                if(input.charAt(0)==payeeName.charAt(0) ){
                    //进行转账
                    while(true){
                        System.out.println("请输入转账金额：您的账户单次操作限额为：" + account.getLimit());
                        System.out.println("您当前账户余额为" + account.getMoney());
                        //判断转账金额是否合法2
                        double transferMoney = sc.nextDouble();
                        if(transferMoney > account.getLimit()){
                            System.out.println("您当前转账金额超过单次操作限额，请重新输入！");
                        }else if(transferMoney > account.getMoney()) {
                            System.out.println("转账金额大于账户金额！请重新输入！");
                        } else if (transferMoney < 0) {
                            System.out.println("输入金额有误，请重新输入");
                        } else{
                            //更新转账方余额
                            account.setMoney(account.getMoney() - transferMoney);
                            //更新收款方余额
                            payee.setMoney(payee.getMoney() + transferMoney);
                            System.out.println("转账成功");
                            //展示转账后的账户信息
                            show(account);
                            return;
                        }
                    }
                }else{
                    System.out.println("校验失败！");
                }

            }
        }
    }

    //修改密码功能
    private static void updataPassword(Scanner sc, Account account) {
        while(true){
            System.out.println("请输入当前账户密码");
            String oldPassword = sc.next();
            if(oldPassword.equals(account.getPassword())){
                while(true){
                    System.out.println("请输入新密码");
                    String newPassword = sc.next();
                    System.out.println("请再次确定密码");
                    String confirmPassword = sc.next();
                    //校验两次密码是否相同
                    if(newPassword.equals(confirmPassword)){
                        account.setPassword(newPassword);
                        System.out.println("修改密码成功！");
                        return;
                    }else{
                        System.out.println("两次密码不一致，请重新输入！");
                    }
                }
            }else{
                System.out.println("密码有误，请重新输入！");
            }
        }
    }

    //注销账户功能
    private static void delete(Scanner sc, Account account, ArrayList<Account> accounts) {
        System.out.println("风险操作！！！请确定您是否要进行注销账户操作？Y/N");
        while(true){
            String op = sc.next();
            if (op.equals("Y")){
                System.out.println("为确保账户安全，请输入账户密码");
                //校验密码是否正确
                while(true){
                    String password = sc.next();
                    if(password.equals(account.getPassword())){
                        //密码正确，进行校验余额
                        if (account.getMoney() > 0){
                            System.out.println("您当前账户仍有余额，请先取款后再进行注销操作！");
                            return;
                        }else{
                            accounts.remove(account);
                            System.out.println("注销成功！期待您的再次使用！");
                            return;
                        }
                    }else{
                        //密码错误
                        System.out.println("密码错误，请重新输入！");
                    }
                }
            }else if(op.equals("N")){
                System.out.println("取消注销账户操作成功！");
                return;
            }else{
                System.out.println("输入指令有误，请重新输入！");
            }
        }
    }
    private static void register(ArrayList<Account> accounts, Scanner sc) {
        //创建对象
        Account account = new Account();
        //键盘输入并将相应的值写入对象
        System.out.println("请输入注册用户名");
        String name = sc.next();
        account.setName(name);
        //确定密码
        while (true) {
            System.out.println("请输入密码");
            String password = sc.next();
            System.out.println("请再次确定密码");
            String confirmPassword = sc.next();
            //两次密码一致，给account对象设置密码值
            if (confirmPassword.equals(password)) {
                account.setPassword(password);
                break;
            } else {
                System.out.println("两次密码不一致，请重新输入：");
            }
        }
        System.out.println("请设置单次操作限额：");
        double limit = sc.nextDouble();
        account.setLimit(limit);
        //生成随机8位卡号并确定唯一性
        Random r = new Random();
        StringBuilder id ;
        while (true) {
            //生成卡号
            id = new StringBuilder();
            for (int i = 0; i < 8; i++) {
                id.append(r.nextInt(10));
            }
            //检查卡号是否唯一
            if( check(accounts,id.toString())==null)
                break;
            else continue;
            }
        //将卡号写入对象
        account.setId(id.toString());
        //将用户名、密码、限额、密码写入集合
        accounts.add(account);
        System.out.println(name+"先生/女士,您的账号开户成功，卡号为"+id+"，请您妥善保管");
    }

    //检查卡号是否存在
    private static Account check(ArrayList<Account> accounts,String id){
        for (int i = 0; i < accounts.size(); i++) {
            Account temp = accounts.get(i);
            if(temp.getId().equals(id))
                return accounts.get(i);
        }
        return null;
    }
}