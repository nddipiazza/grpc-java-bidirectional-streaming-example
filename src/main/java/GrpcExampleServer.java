import com.google.common.util.concurrent.MoreExecutors;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.netty.NettyServerBuilder;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;

public class GrpcExampleServer {
    private static Executor executor;

    public static void main(String [] args) throws IOException, InterruptedException {

        ServerBuilder builder = NettyServerBuilder.forAddress(new InetSocketAddress("localhost", 50000));
        executor = MoreExecutors.directExecutor();
        builder.executor(executor);
        Server server = builder.addService(new ExampleServiceGrpcImpl()).build();

        server.start();

        System.out.println("Server has started");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            server.shutdown();
        }));

        server.awaitTermination();
    }
}
