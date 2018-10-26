import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class HeartbeatCheckHttp2 {
    public static void main(String [] args) throws Exception {
        for (int i=0; i<50000; ++i) {
            Thread.sleep(400L);
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress("localhost", 50000), 5000);
                System.out.println("Connected");
            } catch (Exception ex) {

                System.out.println("Not connected");
            }
            Thread.sleep(TimeUnit.SECONDS.toMillis(2));
        }
    }
}
