package app.repetition_algorithm.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeightedWord {
    private String word;
    private double weight;

    public static int compareByWeightDescending(WeightedWord w1, WeightedWord w2) {
        return (int)Math.signum(w1.getWeight() - w2.getWeight());
    }
}
