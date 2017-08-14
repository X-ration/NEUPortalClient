
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionManager {

    private String urlAddr;
    private String formParas;

    public ConnectionManager(){
    }

    public void setUrlAddr(String urlAddr) {
        this.urlAddr = urlAddr;
    }

    public void setFormParas(String formParas) {
        this.formParas = formParas;
    }

    public String commitRequest(){

        String sCurrentLine;
        String sTotalString = "";

        try {

            URL url = new URL(urlAddr);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "8859_1");
            out.write(formParas); // 向页面传递数据。post的关键所在！
            out.flush();
            out.close();
            // 一旦发送成功，用以下方法就可以得到服务器的回应：

            InputStream l_urlStream;
            l_urlStream = connection.getInputStream();
            // 传说中的三层包装阿！
            BufferedReader l_reader = new BufferedReader(new InputStreamReader(
                    l_urlStream));
            while ((sCurrentLine = l_reader.readLine()) != null) {
                sTotalString += sCurrentLine + "\r\n";

            }
        }
        catch (IOException e){
            e.printStackTrace();
            sTotalString = "Error";
        }
        return sTotalString;
    }

}