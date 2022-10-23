package com.rntgroup.reactive.programming.impl.subscriber;

import com.rntgroup.reactive.programming.impl.model.SportModel;
import com.rntgroup.reactive.programming.impl.repository.SportReactiveRepository;
import com.rntgroup.reactive.programming.impl.service.SportsOriginService;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class SportModelSubscriber implements Subscriber<SportModel> {

    private static final Integer REQUEST_SIZE = 20;
    private static final AtomicLong BATCH_COUNT = new AtomicLong(0);

    private final SportReactiveRepository sportReactiveRepository;
    private AtomicReference<Subscription> subscriptionReference;

    public SportModelSubscriber(SportReactiveRepository sportReactiveRepository,
                                SportsOriginService sportsOriginService) {
        this.sportReactiveRepository = sportReactiveRepository;

        sportsOriginService.getSportsInfo().subscribe(this);
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        System.out.println("Trying to download sports info...");
        subscriptionReference = new AtomicReference<>(subscription);

        getSportsBatch();
    }

    private void getSportsBatch() {
        BATCH_COUNT.set(0);
        subscriptionReference.get().request(REQUEST_SIZE);
    }

    @Override
    public void onNext(SportModel sportModel) {
        sportReactiveRepository.save(sportModel).subscribe();
        System.out.println("Saved: " + sportModel);

        BATCH_COUNT.incrementAndGet();

        if (BATCH_COUNT.get() == REQUEST_SIZE) {
            System.out.printf("Obtained sport entries: %d \n", BATCH_COUNT.get());
            getSportsBatch();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println(throwable);
    }

    @Override
    public void onComplete() {
        System.out.println("Completed saving sports info");
    }

}
