package app.repetition_algorithm;

import app.config.Config;
import app.data.ReviewAlgorithm;
import app.data.Word;
import app.repetition_algorithm.data.ReviewDaysSummary;
import app.repetition_algorithm.data.WeightedWord;
import app.repositories.ReviewAlgorithmRepository;
import app.repositories.WordRepository;
import app.repositories.query_results.WordReviewDays;
import app.repositories.query_results.WordSuccessfulRepetitions;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@NoArgsConstructor
public class ThresholdedSpacedRepetitionAlgorithm implements WordSelector {

    @Value("${flashcards.reviewAlgorithm.numberOfWordsForSuccessfulDailyRepetition}")
    private int numberOfWordsForSuccessfulDailyRepetition;
    @Value("${flashcards.reviewAlgorithm.successfulSingleRepetitionThreshold}")
    private double successfulSingleRepetitionThreshold;

    @Value("${flashcards.reviewAlgorithm.percentageLimit}")
    private double percentageLimit;


    private ReviewAlgorithm reviewAlgorithm;

    @Autowired
    private EntityManager em;

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private EntityManager manager;

    @Autowired
    public ThresholdedSpacedRepetitionAlgorithm(ReviewAlgorithmRepository reviewAlgorithmRepository, @Value("${flashcards.reviewAlgorithm.algorthmName}") String reviewAlgorithmName) {
        reviewAlgorithm = reviewAlgorithmRepository.findOneByName(reviewAlgorithmName);
    }

    public List<Word> nextWordsToReview(int wordCount) throws InsufficientWordCountException {

        //!!!! Excluding thredshold    (threshold, 1]
        Query q1 = em.createNativeQuery(
                "SELECT word.original_word, CAST(SUM(IFNULL(successful_repetition_count,0))as int)  FROM word\n" +
                        "LEFT OUTER JOIN \n" +
                        "\t(SELECT fk_word_id, (MAX(COUNT(repetition.success_rate)-:daily_successful_repetitions +1,0))/MAX(COUNT(repetition.success_rate)-:daily_successful_repetitions +1,1) as successful_repetition_count,\n" +
                        "\tround(julianday(datetime('now')) - julianday(datetime((SUBSTR(repetition_date, 1,LENGTH(repetition_date)-3)), 'unixepoch'))) as days_from_start\n" +
                        "\tFROM repetition\n" +
                        "\tWHERE success_rate > :success_param\n" +
                        "\tGROUP BY fk_word_id, days_from_start\n" +
                        "\tHAVING successful_repetition_count > 0\n" +
                        "\t) as X\n" +
                        "ON word.word_id = X.fk_word_id\n" +
                        "GROUP BY word.word_id");

        q1.setParameter("success_param", successfulSingleRepetitionThreshold);
        q1.setParameter("daily_successful_repetitions", numberOfWordsForSuccessfulDailyRepetition);


        List<Object[]> successfulRepetitionsQueryResult = q1.getResultList();

        Query q2 = em.createNativeQuery(
                "SELECT word.original_word , IFNULL(MAX(julianday(datetime('now')) - julianday(datetime((SUBSTR(repetition_date, 1,LENGTH(repetition_date)-3)), 'unixepoch'))),0) as days_from_start\n" +
                        "FROM word\n" +
                        "LEFT OUTER JOIN repetition ON word.word_id = repetition.fk_word_id \n" +
                        "GROUP BY fk_word_id");

        List<Object[]> wordReviewDaysQueryResult = q2.getResultList();


        //-------------------   Explicit mapping    -------------------------
        List<WordSuccessfulRepetitions> wordSuccessfulRepetitionsList = new ArrayList<>();
        for (Object[] objects : successfulRepetitionsQueryResult) {
            wordSuccessfulRepetitionsList.add(new WordSuccessfulRepetitions((String) objects[0], (int) objects[1]));
        }

        List<WordReviewDays> wordReviewDaysList = new ArrayList<>();
        for (Object[] obj : wordReviewDaysQueryResult) {
        //!!!!!! CUTTING TO INT
            wordReviewDaysList.add(new WordReviewDays((String) obj[0], (int)(float) obj[1]));
        }




        //-------------------------------------------   Selecting Words ---------------------------------------

        // -------------- Uniform version
//        List<ReviewDaysSummary> reviewDaysSummaryList = new ArrayList<>();
//        for (WordSuccessfulRepetitions wordSuccessfulRepetitions : wordSuccessfulRepetitionsList) {
//            String word = wordSuccessfulRepetitions.getWord();
//            int expectedDays = reviewAlgorithm.getExpectedReviewDays(wordReviewDaysList.stream().filter(x -> x.getWord().equals(wordSuccessfulRepetitions.getWord())).findFirst().get().getReviewDays());
//            int successfulRepetitions = wordSuccessfulRepetitions.getSuccessfulRepetitionCount();
//            reviewDaysSummaryList.add(new ReviewDaysSummary(word, expectedDays, successfulRepetitions));
//        }
//
//        List<Word> wordList = reviewDaysSummaryList.stream().filter(x -> x.getSuccessfulDays() < x.getExpectedDays()).map(x -> wordRepository.findOneByOriginalWordAndLanguage(x.getWord(), Config.LANGUAGE)).collect(Collectors.toList());
//        Collections.shuffle(wordList);

        // -------------- Weighted version
        List<WeightedWord> weightedWordList = new ArrayList<>();
        for (WordSuccessfulRepetitions wordSuccessfulRepetitions : wordSuccessfulRepetitionsList) {
            String word = wordSuccessfulRepetitions.getWord();
            int expectedDays = reviewAlgorithm.getExpectedReviewDays(wordReviewDaysList.stream().filter(x -> x.getWord().equals(wordSuccessfulRepetitions.getWord())).findFirst().get().getReviewDays());
            int successfulRepetitions = wordSuccessfulRepetitions.getSuccessfulRepetitionCount();

            //Do some weighting function (f.ex (abs(x-y))^3)
            weightedWordList.add(new WeightedWord(word, Math.pow(Math.abs(expectedDays-successfulRepetitions),2)));
        }

        //----------------- Limit to x% of most occuring (or all if small number)
        //Check if there are enough words
        if(weightedWordList.size() < wordCount){
            throw new InsufficientWordCountException("There are only " + weightedWordList.size() + " words left, this is less than needed " + wordCount);
        }

        //Check if limited amount won't be too little
        int limitedWordCount = ((int)(weightedWordList.size() * percentageLimit) < wordCount) ? weightedWordList.size() : (int)(weightedWordList.size() * percentageLimit) ;

        //Sorting weighted words in descending order and get top values
        weightedWordList.sort(new Comparator<WeightedWord>() {
            @Override
            public int compare(WeightedWord o1, WeightedWord o2) {
                //Descending order
                return Double.compare(o2.getWeight(), o1.getWeight());
            }
        });
        List<WeightedWord> topWeightedWords = weightedWordList.subList(0, limitedWordCount);


        //Get sample
        WordWeightedHistogram histogram = new WordWeightedHistogram(topWeightedWords);
        List<Word> wordList = histogram.sample(wordCount).stream().map(x -> wordRepository.findOneByOriginalWordAndLanguage( x.getWord(),Config.LANGUAGE)).collect(Collectors.toList());


        System.out.println(successfulRepetitionsQueryResult.size());
        System.out.println(wordReviewDaysQueryResult.size());



        return wordList.subList(0, wordCount);
    }
}
