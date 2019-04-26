package app.similarity_checking;

import app.data.Word;
import com.sun.xml.internal.bind.v2.util.EditDistance;

public class EditDistanceWordSimilarity implements WordSimilarity {
    @Override
    public double checkSimilarity(Word word, String text) {

        int editDistance = EditDistance.editDistance(word.getTranslatedWord(), text);

        int distanceThreshold = 10;

        if(editDistance<distanceThreshold){
            return (distanceThreshold-editDistance)/10.0;
        }else{
            return 0;
        }
    }
}
