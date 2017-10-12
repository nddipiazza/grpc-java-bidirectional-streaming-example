import com.example.BiDirectionalExampleService;
import com.example.ExampleServiceGrpc;
import io.grpc.stub.StreamObserver;

public class ExampleServiceGrpcImpl extends ExampleServiceGrpc.ExampleServiceImplBase {
    @Override
    public StreamObserver<BiDirectionalExampleService.RequestCall> connect(StreamObserver<BiDirectionalExampleService.ResponseCall> responseObserver) {
        System.out.println("Connecting stream observer");
        StreamObserver<BiDirectionalExampleService.RequestCall> so = new StreamObserver<BiDirectionalExampleService.RequestCall>() {
            @Override
            public void onNext(BiDirectionalExampleService.RequestCall value) {
                System.out.println("onNext from server");
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                responseObserver.onNext(BiDirectionalExampleService.ResponseCall.getDefaultInstance());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("on error");
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("on completed");
            }
        };
        return so;
    }
}
