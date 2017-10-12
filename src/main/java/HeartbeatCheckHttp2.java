import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class HeartbeatCheckHttp2 {
    public static void main(String [] args) throws Exception {
        for (int i=0; i<50000; ++i) {
            Thread.sleep(400L);
            Socket socket = new Socket();
            try {
                socket.connect(new InetSocketAddress("localhost", 50000));
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.closeQuietly(socket);
            }
        }
    }
}
