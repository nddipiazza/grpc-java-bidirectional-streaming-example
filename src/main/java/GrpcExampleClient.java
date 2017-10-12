import com.example.BiDirectionalExampleService;
import com.example.ExampleServiceGrpc;
import com.google.common.util.concurrent.MoreExecutors;
import io.grpc.*;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;

public class GrpcExampleClient {
    public static void main(String [] args) throws IOException, InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50000).usePlaintext(true).build();
        ExampleServiceGrpc.ExampleServiceStub service = ExampleServiceGrpc.newStub(channel);
        AtomicReference<StreamObserver<BiDirectionalExampleService.RequestCall>> requestObserverRef = new AtomicReference<>();
        CountDownLatch finishedLatch = new CountDownLatch(1);
        StreamObserver<com.example.BiDirectionalExampleService.RequestCall> observer = service.connect(new StreamObserver<BiDirectionalExampleService.ResponseCall>() {
            @Override
            public void onNext(BiDirectionalExampleService.ResponseCall value) {
                System.out.println("onNext from client");
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                requestObserverRef.get().onNext(BiDirectionalExampleService.RequestCall.getDefaultInstance());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("on error");
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("on completed");
                finishedLatch.countDown();
            }
        });
        requestObserverRef.set(observer);
        observer.onNext(BiDirectionalExampleService.RequestCall.getDefaultInstance());
        finishedLatch.await();
        observer.onCompleted();
    }
}
