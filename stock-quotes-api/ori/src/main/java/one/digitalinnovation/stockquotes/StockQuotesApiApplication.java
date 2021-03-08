package one.digitalinnovation.stockquotes;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Date;
import java.util.Random;

import static java.lang.String.format;

@Log4j2
@EnableScheduling
@SpringBootApplication
public class StockQuotesApiApplication {
	@Autowired
	private QuoteRepository quoteRepository;

	public static void main(String[] args) {
		SpringApplication.run(StockQuotesApiApplication.class, args);
	}

	@Transactional
	@Scheduled(fixedDelay = 1000)
	public void generateRandomQuotes(){
		log.info(format("generating %s", quoteRepository.findFirstBySymbolOrderByTimestampDesc("TESTE")
			.map(this::createNewQuote)
			.orElseGet(this::initializeQuote)));
	}

	private Quote createNewQuote(Quote quote) {
		return quoteRepository.save(Quote.builder()
				.openValue(quote.getOpenValue() + new RandomDataGenerator().nextUniform(-0.10, 0.10))
				.closeValue(quote.getCloseValue() + new RandomDataGenerator().nextUniform(-0.10, 0.10))
				.symbol(quote.getSymbol())
				.timestamp(new Date())
				.build());
	}

	private Quote initializeQuote() {
		return quoteRepository.save(Quote.builder()
				.openValue(0.222222)
				.closeValue(0.222222)
				.symbol("TESTE")
				.timestamp(new Date())
				.build());
	}
}
