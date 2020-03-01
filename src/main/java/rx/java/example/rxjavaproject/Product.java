package rx.java.example.rxjavaproject;

public class Product {

    public String getReviews() throws InterruptedException {

        Thread.currentThread().sleep(3000);
        System.out.println("Getting product reviews..." + Thread.currentThread().getName());
        return "reviews";
    }

    public String getPromotions() throws InterruptedException {

        Thread.currentThread().sleep(3000);
        System.out.println("Getting promotions ...."+ Thread.currentThread().getName());
        return "promotions";
    }

    public String getProductRecommendations() throws InterruptedException {

        Thread.currentThread().sleep(3000);
        System.out.println("Getting product recommendations"+ Thread.currentThread().getName());
        return "recommendations";
    }
}
