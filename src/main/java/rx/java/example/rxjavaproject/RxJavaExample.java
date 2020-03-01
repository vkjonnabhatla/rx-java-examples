package rx.java.example.rxjavaproject;

import io.reactivex.Observable;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.Assert;

public class RxJavaExample {
    public static void main(String[] args[]){
        Observable<String> observable = Observable.just("Hello");
        observable.subscribe(s -> System.out.println(s));
        //Assert.isTrue(result.equals("Hello"));
    }
}
