package com.rntgroup.rpc.protocols.avro.producer;

import com.rntgroup.rpc.protocols.avro.producer.utils.BookRandomPropertiesUtils;
import com.rntgroup.rpc.protocols.avro.schema.Book;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class BookProducer {

    private static class Callback implements ListenableFutureCallback<SendResult<String, Book>> {

        private final Book book;

        Callback(Book book) {
            this.book = book;
        }

        @Override
        public void onFailure(Throwable ex) {
            System.out.println("Unable to send Book [" + book.getId() + "] due to : " + ex.getMessage());
        }

        @Override
        public void onSuccess(SendResult<String, Book> result) {
            System.out.println("Published a Book [" + book.getId() + "]");
        }
    }

    private static final String TOPIC = "books";

    private final KafkaTemplate<String, Book> kafkaTemplate;

    public BookProducer(KafkaTemplate<String, Book> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRateString = "${scheduling.producer}")
    public void sendRandomBottle() {
        var book = new Book(
                ThreadLocalRandom.current().nextLong(),
                BookRandomPropertiesUtils.getRandomAuthor(),
                BookRandomPropertiesUtils.getRandomTitle(),
                BookRandomPropertiesUtils.getRandomGenre()
        );

        var listenableFuture = kafkaTemplate.send(TOPIC, book);
        listenableFuture.addCallback(new Callback(book));
    }

}
