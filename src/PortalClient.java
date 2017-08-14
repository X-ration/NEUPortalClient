import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Scanner;

public class PortalClient {

    private ConnectionManager connectionManager;
    private String username;
    private String password;
    private String online;
    private String description;

    public PortalClient(String username, String password){
        connectionManager = new ConnectionManager();
        this.username = username;
        this.password = password;
        online = "未知";
        description = "";
    }

    public void connect(){
        connectionManager.setUrlAddr("http://ipgw.neu.edu.cn/srun_portal_pc.php?ac_id=1&");
        connectionManager.setFormParas("action=login&username=" + username + "&password=" + password);
        String response = connectionManager.commitRequest();
        Document document = Jsoup.parse(response);
        Element table_login = document.select("table.login").first();
        if(table_login == null){
            online = "否";
            Element info = document.select("form.form-horizontal,form.ng-pristine,form.ng-valid").select("p").first();
            description = "登录失败 " + info.text();
        }
        else {
            online = "是";
            description = "登陆成功";
        }
    }

    public void disconnect(){
        connectionManager.setUrlAddr("http://ipgw.neu.edu.cn/include/auth_action.php");
        connectionManager.setFormParas("action=logout&username=" + username + "&password=" + password);
        String response = connectionManager.commitRequest();
        if(response.equals("")){
            online = "否";
            description = "已断开连接";
        }
        else{
            online = "是";
            description = "断开连接出错 " + response;
        }
    }

    public String status(){
        String s = "状态信息\n";
        s += ("用户：" + username + "\n");
        s += ("在线：" + online + "\n");
        s += description;
        return s;
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Username:");
        String username = scanner.nextLine();
        System.out.println("Password:");
        String password = scanner.nextLine();
        PortalClient portalClient = new PortalClient(username, password);

        boolean ctrl = true;
        while(ctrl) {
            System.out.println("1、连接网络  2、断开网络  3、查看状态  4、退出程序");
            int a;
            try {
                a = Integer.parseInt(scanner.nextLine());
            }catch (NumberFormatException e){
                System.out.println("输入无效！");
                continue;
            }
            switch (a){
                case 1:
                    portalClient.connect();
                    System.out.println(portalClient.status());
                    break;
                case 2:
                    portalClient.disconnect();
                    System.out.println(portalClient.status());
                    break;
                case 3:
                    System.out.println(portalClient.status());break;
                case 4:
                    ctrl = false;break;
                default:
                    break;
            }
        }
    }

}
