package app.similarity_checking;

import app.data.Word;
import  com.swabunga.spell.engine.EditDistance;
;

public class EditDistanceWordSimilarity implements WordSimilarity {
    @Override
    public double checkSimilarity(Word word, String text) {

        int editDistance = EditDistance.getDistance(word.getTranslatedWord(), text);

        int distanceThreshold = 10;

        if(editDistance<distanceThreshold){
            return (distanceThreshold-editDistance)/((double)(distanceThreshold));
        }else{
            return 0;
        }
    }
}
