package app.repositories.query_results;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordSuccessfulRepetitions {
    private String word;
    private int successfulRepetitionCount;
}
