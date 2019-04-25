package app.config;

import app.data.*;
import app.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SampleDataLoader {

    @Autowired
    WordRepository wordRepository;

    @Autowired
    AssociationRepository associationRepository;

    @Autowired
    RepetitionRepository repetitionRepository;

    @Autowired
    ReviewAlgorithmRepository reviewAlgorithmRepository;

    @Autowired
    ReviewDayRepository reviewDayRepository;

    @Autowired
    private EntityManager em;


    public SampleDataLoader() {
    }

    public void load1() {


        //0,1,2
        ReviewAlgorithm reviewAlgorithm = ReviewAlgorithm.builder().name("Algorithm1").build();
        reviewAlgorithm.setReviewDays(
                Arrays.asList(ReviewDay.builder().daysAfterBeginning(0).reviewAlgorithm(reviewAlgorithm).build(),
                        ReviewDay.builder().daysAfterBeginning(1).reviewAlgorithm(reviewAlgorithm).build(),
                        ReviewDay.builder().daysAfterBeginning(2).reviewAlgorithm(reviewAlgorithm).build()));

        //!!!!!     This demo sets 4 days from start, we can move to day 5 after start to see what it will propose
        int startedDaysAgo = 5;
        LocalDateTime startDate = LocalDateTime.now().minusHours(1).minusDays(startedDaysAgo);


        Word w1 = Word.builder().originalWord("Word1").language("us").build();
        w1.setRepetitions(Arrays.asList(
                Repetition.builder().successRate(0.6).date(startDate.plusMinutes(10)).word(w1).build(),
                Repetition.builder().successRate(0.7).date(startDate.plusDays(2)).word(w1).build(),
                Repetition.builder().successRate(0.3).date(startDate.plusDays(3)).word(w1).build()
        ));


        Word w2 = Word.builder().originalWord("Word2").language("us").build();
        w2.setRepetitions(Arrays.asList(
                Repetition.builder().successRate(0.2).date(startDate.plusMinutes(10)).word(w2).build(),
                Repetition.builder().successRate(0.3).date(startDate.plusDays(1)).word(w2).build(),
                Repetition.builder().successRate(0.7).date(startDate.plusDays(2)).word(w2).build(),
                Repetition.builder().successRate(0.6).date(startDate.plusDays(3)).word(w2).build(),
                Repetition.builder().successRate(0.6).date(startDate.plusDays(4)).word(w2).build()
        ));

        Word w3 = Word.builder().originalWord("Word3").language("us").build();
        w3.setRepetitions(Arrays.asList(
                Repetition.builder().successRate(0.2).date(startDate.plusMinutes(10)).word(w3).build(),
                Repetition.builder().successRate(0.3).date(startDate.plusDays(2)).word(w3).build(),
                Repetition.builder().successRate(0.5).date(startDate.plusDays(3)).word(w3).build()
        ));


        Word w4 = Word.builder().originalWord("Word4").language("us").build();
        w4.setRepetitions(Arrays.asList(
                Repetition.builder().successRate(0.7).date(startDate.plusMinutes(10)).word(w4).build(),
                Repetition.builder().successRate(0.9).date(startDate.plusDays(1)).word(w4).build(),
                Repetition.builder().successRate(0.6).date(startDate.plusDays(3)).word(w4).build(),
                Repetition.builder().successRate(0.7).date(startDate.plusDays(4)).word(w4).build()
        ));

        Word w5 = Word.builder().originalWord("Word5").language("us").build();
        w5.setRepetitions(Arrays.asList(
                Repetition.builder().successRate(0.3).date(startDate.plusMinutes(10)).word(w5).build(),
                Repetition.builder().successRate(0.5).date(startDate.plusDays(3)).word(w5).build()
        ));

        Word w6 = Word.builder().originalWord("Word6").language("us").build();
        w6.setRepetitions(Arrays.asList(
                Repetition.builder().successRate(0.7).date(startDate.plusMinutes(10)).word(w6).build(),
                Repetition.builder().successRate(0.7).date(startDate.plusDays(2)).word(w6).build()
        ));

        Word w7 = Word.builder().originalWord("Word7").language("us").build();
        w7.setRepetitions(Arrays.asList(
                Repetition.builder().successRate(0.2).date(startDate.plusMinutes(10)).word(w7).build(),
                Repetition.builder().successRate(0.5).date(startDate.plusDays(1)).word(w7).build(),
                Repetition.builder().successRate(0.8).date(startDate.plusDays(3)).word(w7).build(),
                Repetition.builder().successRate(0.9).date(startDate.plusDays(4)).word(w7).build()
        ));

        Word w8 = Word.builder().originalWord("Word8").language("us").build();
        w8.setRepetitions(Arrays.asList(
                Repetition.builder().successRate(0.4).date(startDate.plusMinutes(10)).word(w8).build()
        ));

        reviewAlgorithmRepository.save(reviewAlgorithm);
        wordRepository.saveAll(Arrays.asList(w1, w2, w3, w4, w5, w6, w7, w8));

    }

    public void load() {
        Word w1 = new Word();
        w1.setLanguage("us");
        w1.setOriginalWord("OriginalWord1");
        w1.setTranslatedWord("TranslatedWord1");

        Word w2 = new Word();
        w2.setLanguage("us");
        w2.setOriginalWord("OriginalWord2");
        w2.setTranslatedWord("TranslatedWord2");


        Word w3 = new Word();
        w3.setLanguage("us");
        w3.setOriginalWord("OriginalWord3");
        w3.setTranslatedWord("TranslatedWord3");


        Word w4 = new Word();
        w4.setLanguage("us");
        w4.setOriginalWord("OriginalWord4");
        w4.setTranslatedWord("TranslatedWord4");


        Word w5 = new Word();
        w5.setLanguage("us");
        w5.setOriginalWord("OriginalWord5");
        w5.setTranslatedWord("TranslatedWord5");


        Association a1 = new Association();
        a1.setAssociationWord("Association1");
        a1.setWord(w1);
        Association a2 = new Association();
        a2.setAssociationWord("Association2");
        a2.setWord(w1);
        Association a3 = new Association();
        a3.setAssociationWord("Association3");
        a3.setWord(w1);
        Association a4 = new Association();
        a4.setAssociationWord("Association4");
        a4.setWord(w2);
        Association a5 = new Association();
        a5.setAssociationWord("Association5");
        a5.setWord(w2);

        w1.setAssociations(Arrays.asList(a1, a2, a3));
        w2.setAssociations(Arrays.asList(a4, a5));

        int daysAgo = 2;
        LocalDateTime startDate = LocalDateTime.now().minusHours(1).minusDays(daysAgo);


        Repetition w1rep0 = Repetition.builder().date(startDate.plusMinutes(10)).successRate(0.6).word(w1).build();
        Repetition w1rep1 = Repetition.builder().date(startDate.plusDays(2)).successRate(0.7).word(w1).build();

        Repetition w2rep0 = Repetition.builder().date(startDate.plusDays(1)).successRate(0.3).word(w2).build();
        Repetition w2rep1n1 = Repetition.builder().date(startDate.plusDays(2)).successRate(0.7).word(w2).build();
        Repetition w2rep1n2 = Repetition.builder().date(startDate.plusDays(2)).successRate(0.6).word(w2).build();
        Repetition w2rep1n3 = Repetition.builder().date(startDate.plusDays(2)).successRate(0.5).word(w2).build();

        Repetition w3rep0 = Repetition.builder().date(startDate.plusDays(2)).successRate(0.3).word(w3).build();

        Repetition w4rep0 = Repetition.builder().date(startDate.plusMinutes(10)).successRate(0.7).word(w4).build();
        Repetition w4rep1 = Repetition.builder().date(startDate.plusDays(1)).successRate(0.9).word(w4).build();

        w1.setRepetitions(Arrays.asList(w1rep0, w1rep1));
        w2.setRepetitions(Arrays.asList(w2rep0, w2rep1n1, w2rep1n2, w2rep1n3));
        w3.setRepetitions(Arrays.asList(w3rep0));
        w4.setRepetitions(Arrays.asList(w4rep0, w4rep1));


        wordRepository.saveAll(Arrays.asList(w1, w2, w3, w4, w5));


        ReviewAlgorithm algorithm1 = new ReviewAlgorithm();
        algorithm1.setName("Algorithm1");

        ReviewDay day1 = new ReviewDay();
        day1.setDaysAfterBeginning(0);
        day1.setReviewAlgorithm(algorithm1);

        ReviewDay day2 = new ReviewDay();
        day2.setDaysAfterBeginning(1);
        day2.setReviewAlgorithm(algorithm1);

        ReviewDay day3 = new ReviewDay();
        day3.setDaysAfterBeginning(3);
        day3.setReviewAlgorithm(algorithm1);

        ReviewDay day4 = new ReviewDay();
        day4.setDaysAfterBeginning(7);
        day4.setReviewAlgorithm(algorithm1);

        algorithm1.setReviewDays(Arrays.asList(day1, day2, day3, day4));

        reviewAlgorithmRepository.save(algorithm1);

        associationRepository.saveAll(Arrays.asList(a1, a2, a3, a4, a5));
        repetitionRepository.saveAll(Arrays.asList(w1rep0, w1rep1, w2rep0, w2rep1n1, w2rep1n2, w2rep1n3, w3rep0, w4rep0, w4rep1));


    }


}
