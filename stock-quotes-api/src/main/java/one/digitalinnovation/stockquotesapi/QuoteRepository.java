package one.digitalinnovation.stockquotesapi;

import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends ReactiveSortingRepository<Quote, Long> {
}
