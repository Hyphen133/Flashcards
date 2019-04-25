package app.repetition_algorithm.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDaysSummary {
    String word;
    int expectedDays;
    int successfulDays;
}
