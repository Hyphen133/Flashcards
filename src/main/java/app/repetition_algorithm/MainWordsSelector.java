package app.repetition_algorithm;

import app.data.Word;
import app.repositories.RepetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Random;

@Service
public class MainWordsSelector implements WordSelector {

    @Autowired
    NewWordsSelector newWordsSelector;

    @Autowired
    ThresholdedSpacedRepetitionAlgorithm thresholdedSpacedRepetitionAlgorithm;

    @Autowired
    EntityManager em;

    @Autowired
    RepetitionRepository repetitionRepository;

    @Value("${flashcards.reviewAlgorithm.numberOfWordsForSuccessfulDailyRepetition}")
    private int numberOfWordsForSuccessfulDailyRepetition;

    @Value("${flashcards.reviewAlgorithm.successfulSingleRepetitionThreshold}")
    private double successfulSingleRepetitionThreshold;


    //Matters only to first few days
    private int initialReviewCounts = 10;

    //Returns to zero whenever it reaches formula calculated value then new words will be returned
    //TODO -> Store counter somewhere
    private int reviewCounter = 0;

    public static final int LEARNT_WORD_PERCENTAGE_MAX_ADDITIONAL_DAYS = 5;
    public static final int ON_TIME_REPETITION_PERCENTAGE_MAX_ADDITIONAL_DAYS = 3;
    public static final int INITIAL_NEW_REVIEWS_COUNT = 5;


    @Override
    public List<Word> nextWordsToReview(int wordCount) throws InsufficientWordCountException {

        //Formula idea
        // (1) should output frequency ->  like every n-th day do new words
        // (2) during first repetitions should focus on diving new words
        // (3) during last repetitions should focus on diving new words

        //Formula factors
        //  - intialNewWordReviewsCounts  -> start with new words only
        //  - percentageOfLearntWords
        //  - onTimeRepetitonPercentage  -> used to add more reviews whenever needed

        //Forumula
        //Up to 5 additional days depending on learn wordPercentage
        //Up to 3 additional days depending on onTimeRepetitionPercentage
        //1 +  to have starting threshold
        // reviewCounterThreshold =  1 + (5 * (1-percentageOfLearntWords) + 3*(1-onTimeRepetitonPercentage)_




//        System.out.println("COLW " + countOfLearntWords);


        double onTimeRepetitonPercentage = 1.0;


        if (initialReviewCounts <= INITIAL_NEW_REVIEWS_COUNT) {
            initialReviewCounts++;
            return newWordsSelector.nextWordsToReview(wordCount);
        }


        //Next review (not intial)
        reviewCounter++;



        //--------------------  Calculating percentage of learnt words
        Query q = em.createNativeQuery(
                "SELECT COUNT(word.original_word) FROM word\n" +
                        "WHERE word.word_id IN(\n" +
                        "\tSELECT DISTINCT repetition.fk_word_id FROM repetition\n" +
                        ")");

        List<Integer> qResult = q.getResultList();
        int countOfLearntWords = qResult.get(0);

        double percentageOfLearntWords = ((double) countOfLearntWords)/repetitionRepository.count();



        //--------------------  Calculating percentage of on time repetition words
        Query q1 = em.createNativeQuery("SELECT COUNT(*)  FROM word\n" +
                "LEFT OUTER JOIN \n" +
                "\t(SELECT fk_word_id, (MAX(COUNT(repetition.success_rate)-:daily_successful_repetitions +1,0))/MAX(COUNT(repetition.success_rate)-:daily_successful_repetitions +1,1) as successful_repetition_count,\n" +
                "\tround(julianday(datetime('now')) - julianday(datetime((SUBSTR(repetition_date, 1,LENGTH(repetition_date)-3)), 'unixepoch'))) as days_from_start\n" +
                "\tFROM repetition\n" +
                "\tWHERE success_rate > :success_param\n" +
                "\tGROUP BY fk_word_id, days_from_start\n" +
                "\tHAVING successful_repetition_count > 0\n" +
                "\t) as X\n" +
                        "ON word.word_id = X.fk_word_id");

        q1.setParameter("success_param", successfulSingleRepetitionThreshold);
        q1.setParameter("daily_successful_repetitions", numberOfWordsForSuccessfulDailyRepetition);


        List<Integer> q1Result = q1.getResultList();
        int countOfOnTimeRepetitions = q1Result.get(0);

        double onTimeRepetitionPercentage = (double) (countOfOnTimeRepetitions)/countOfLearntWords;




        double reviewCounterThreshold =
                1 + (int) (LEARNT_WORD_PERCENTAGE_MAX_ADDITIONAL_DAYS * (1-percentageOfLearntWords))
                        + (int) (ON_TIME_REPETITION_PERCENTAGE_MAX_ADDITIONAL_DAYS * (1-onTimeRepetitonPercentage));


        if (reviewCounter >= reviewCounterThreshold) {
            //Reset counter
            reviewCounter = 0;
            return newWordsSelector.nextWordsToReview(wordCount);
        } else {
            return thresholdedSpacedRepetitionAlgorithm.nextWordsToReview(wordCount);
        }
    }
}
