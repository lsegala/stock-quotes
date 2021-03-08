package one.digitalinnovation.stockquotesapi;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Builder
@Table("quote")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Quote {
    @Id
    private Long id;
    private String symbol;
    private Double openValue;
    private Double closeValue;
    private LocalDateTime timestamp;
}
