package rx.java.example.rxjavaproject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.websocket.RemoteEndpoint;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import rx.util.async.*;

@SpringBootApplication
public class RxJavaProjectApplication {

	public static void main(String[] args) throws InterruptedException {
		//main();
		observable();
		//SpringApplication.run(RxJavaProjectApplication.class, args);
	}

	public static void observable(){
		Observable<String> observable = Observable.just("Hello");
		observable.subscribe(s -> System.out.println(s));
		System.out.println("End of method");

		/*Observable.just(1,2,3,4,5)
				.zipWith(Observable.interval(1000, TimeUnit.MILLISECONDS), (item, interval) -> item)
				.subscribe(System.out.println(item));
*/
		System.out.println("End of method - 2");


		List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));

		Observable intValue = Observable.zip(
				Observable.interval(1000, TimeUnit.MILLISECONDS).take(5),
				Observable.fromArray(1, 2, 3, 4, 5),
				(aLong, integer) -> getIntegerValue(aLong,integer));
	//	intValue.subscribe(s -> System.out.println(s));
		intValue.blockingSubscribe(s -> System.out.println(s));
		System.out.println("End of method - 3");
	}

	public static int getIntegerValue(long l, int i ){
		System.out.println(i + ":: "+ Thread.currentThread().getName());
		return i;
	}

	public static void main() throws InterruptedException {
		Product product = new Product();
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		Observable<String> reviews = Observable.just(product.getReviews());

		Observable<String> promotions = Observable.just(product.getPromotions());

		Observable<String> recommendations = Observable.just(product.getProductRecommendations());

		Observable<String> productString = Observable.zip(
				reviews.subscribeOn(Schedulers.from(executorService)).onErrorReturn(error -> {
					System.out.println("Reviews error block :: "+ Thread.currentThread().getName());
					return reviews.blockingSingle();
				}),
				promotions.subscribeOn(Schedulers.from(executorService)).onErrorReturn(error -> {
					System.out.println("promotions error block :: " + Thread.currentThread().getName());
					return promotions.blockingSingle();
				}),
				recommendations.subscribeOn(Schedulers.from(executorService)).onErrorReturn(error -> {
					System.out.println("recommendations error block :: " + Thread.currentThread().getName());
					return recommendations.blockingSingle();
				}), RxJavaProjectApplication::finalReturnValue);



		/*Observable.zip(
				reviews,
				promotions,
				recommendations,(str1, str2, str3) -> finalReturnValue(str1, str2, str3)).subscribe(e -> System.out.println(e));*/

		productString.subscribeOn(Schedulers.from(executorService)).blockingSubscribe(e -> System.out.println(e));
		//String finalString = productString.blockingSubscribe().;
		//System.out.println("final string :: "+ finalString);
	}

	public static void mainWithAsynch() throws InterruptedException {
		Product product = new Product();
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		Observable<String> reviews = Observable.just(product.getReviews());

		Observable<String> promotions = Observable.just(product.getPromotions());

		Observable<String> recommendations = Observable.just(product.getProductRecommendations());



		Observable<String> productString = Observable.zip(
				reviews.subscribeOn(Schedulers.from(executorService)).onErrorReturn(error -> {
					System.out.println("Reviews error block :: "+ Thread.currentThread().getName());
					return reviews.blockingSingle();
				}),
				promotions.subscribeOn(Schedulers.from(executorService)).onErrorReturn(error -> {
					System.out.println("promotions error block :: " + Thread.currentThread().getName());
					return promotions.blockingSingle();
				}),
				recommendations.subscribeOn(Schedulers.from(executorService)).onErrorReturn(error -> {
					System.out.println("recommendations error block :: " + Thread.currentThread().getName());
					return recommendations.blockingSingle();
				}), RxJavaProjectApplication::finalReturnValue);



		/*Observable.zip(
				reviews,
				promotions,
				recommendations,(str1, str2, str3) -> finalReturnValue(str1, str2, str3)).subscribe(e -> System.out.println(e));*/

		productString.subscribeOn(Schedulers.from(executorService)).blockingSubscribe(e -> System.out.println(e));
		//String finalString = productString.blockingSubscribe().;
		//System.out.println("final string :: "+ finalString);
	}

	public static String finalReturnValue(String reviews, String promotions, String recommendations){
		System.out.println(reviews + " : " + promotions + " : " + recommendations);
		return reviews + " : " + promotions + " : " + recommendations;
	}
}
