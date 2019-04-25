package app.repetition_algorithm;

import app.repetition_algorithm.data.WeightedWord;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
@AllArgsConstructor
public class WordWeightedHistogram {
    private List<WeightedWord> weightedWords;

    public WordWeightedHistogram() {
        weightedWords = new ArrayList<>();
    }



    public void remove(WeightedWord weightedWord) {
        weightedWords.remove(weightedWord);
    }

    public List<WeightedWord> sample(int sampleSize) {
        double weightSum = 0;
        for (WeightedWord weightedWord : weightedWords) {
            weightSum += weightedWord.getWeight();
        }

        Random random = new Random();
        List<WeightedWord> sampledWords = new ArrayList<>();

        for (int i = 0; i < sampleSize; i++) {
            //Value from which the object will be selected
            double randomValue = random.nextDouble() * weightSum;

            //Find element
            int index=0;
            double currentWeightSum = 0;

            while(currentWeightSum < randomValue && index < weightedWords.size()){
                currentWeightSum += weightedWords.get(index).getWeight();
                index++;
            }

            sampledWords.add(weightedWords.get(index-1));

            //Remove word not to have duplicates
            weightSum -= weightedWords.get(index-1).getWeight();
            remove(weightedWords.get(index-1));
        }

        return sampledWords;
    }

}
