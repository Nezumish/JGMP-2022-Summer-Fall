package com.rntgroup.reactive.programming.impl.service.impl.decathlon;

import com.rntgroup.reactive.programming.impl.model.SportModel;
import com.rntgroup.reactive.programming.impl.service.SportsOriginService;
import com.rntgroup.reactive.programming.impl.service.impl.decathlon.dto.DecathlonSportsDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class DecathlonService implements SportsOriginService {

    private static final String DECATHLON_SPORTS_LINK = "https://sports.api.decathlon.com/sports";
    private final WebClient webClient;

    public DecathlonService(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Flux<SportModel> getSportsInfo() {
        return webClient.get()
                .uri(DECATHLON_SPORTS_LINK)
                .retrieve()
                .bodyToMono(DecathlonSportsDto.class)
                .flatMapIterable(DecathlonSportsDto::getData)
                .map(dto -> new SportModel(dto.getId(), dto.getAttributes().getName()))
                .doOnComplete(() -> System.out.println("Done fetching Sports from Decathlon"));
    }

}
