package app.repositories.query_results;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordReviewDays {
    String word;
    int reviewDays;
}
