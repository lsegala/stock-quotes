package one.digitalinnovation.stockquotesapi;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalDateTime;

@Log4j2
@Service
@Transactional
@AllArgsConstructor
public class QuoteGenerator {
    private final QuoteRepository quoteRepository;
    private final ConnectionFactoryInitializer connectionFactoryInitializer;

    @PostConstruct
    public void init(){
        Flux.generate(() -> {
            Quote initialQuote = initialQuote();
            return Tuples.of(initialQuote, createNewQuote(initialQuote));
        }, (state, sink) -> {
            sink.next(state.getT1());
            return Tuples.of(state.getT2(), createNewQuote(state.getT1()));
        })
        .delaySubscription(Duration.ofMillis(3000))
        .subscribe(log::info);
    }

    private Quote createNewQuote(Quote previousQuote) {
        Quote newQuote = Quote.builder()
                .openValue(previousQuote.getOpenValue() + new RandomDataGenerator().nextUniform(-0.1, 0.1))
                .closeValue(previousQuote.getCloseValue() + new RandomDataGenerator().nextUniform(-0.1, 0.1))
                .symbol(previousQuote.getSymbol())
                .timestamp(LocalDateTime.now())
                .build();
        quoteRepository.save(newQuote)
                .subscribe();
        return newQuote;
    }

    private Quote initialQuote() {
        Quote quote = Quote.builder()
                .openValue(0.2)
                .closeValue(0.2)
                .symbol("TESTE")
                .timestamp(LocalDateTime.now())
                .build();
        quoteRepository.save(quote)
                .subscribe();
        return quote;
    }
}
