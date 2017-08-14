import java.util.Scanner;

public class PortalClient {

    private ConnectionManager connectionManager;
    private String username;
    private String password;

    public PortalClient(String username, String password){
        connectionManager = new ConnectionManager();
        this.username = username;
        this.password = password;
    }

    public void connect(){
        connectionManager.setUrlAddr("http://ipgw.neu.edu.cn/srun_portal_pc.php?ac_id=1&");
        connectionManager.setFormParas("action=login&username=" + username + "&password=" + password);
        connectionManager.commitRequest();
    }

    public void disconnect(){
        connectionManager.setUrlAddr("http://ipgw.neu.edu.cn/include/auth_action.php");
        connectionManager.setFormParas("action=logout&username=" + username + "&password=" + password);
        connectionManager.commitRequest();
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
            System.out.println("1、连接网络  2、断开网络  3、退出程序");
            int a;
            try {
                a = Integer.parseInt(scanner.nextLine());
            }catch (NumberFormatException e){
                System.out.println("输入无效！");
                continue;
            }
            switch (a){
                case 1:
                    portalClient.connect();break;
                case 2:
                    portalClient.disconnect();break;
                case 3:
                    ctrl = false;break;
                default:
                    break;
            }
        }
    }

}
