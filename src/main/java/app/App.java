package app;

import app.config.Config;
import app.config.SampleDataLoader;
import app.repetition_algorithm.MainWordsSelector;
import app.repetition_algorithm.NewWordsSelector;
import app.repetition_algorithm.ThresholdedSpacedRepetitionAlgorithm;
import app.repositories.ReviewAlgorithmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class App implements CommandLineRunner {

    @Autowired
    ThresholdedSpacedRepetitionAlgorithm srAlgorithm;

    @Autowired
    NewWordsSelector newWordsSelector;

    @Autowired
    SampleDataLoader loader;

    @Autowired
    MainWordsSelector mainWordsSelector;

    @Autowired
    ReviewAlgorithmRepository reviewAlgorithmRepository;

    @Autowired
    Config config;


    public static void main(String[] args){
//        https://www.baeldung.com/spring-boot-sqlite
//        https://stackoverflow.com/questions/17587753/does-hibernate-fully-support-sqlite
//        https://stackoverflow.com/questions/42890375/spring-boot-configure-sqlite-database
//        https://thoughts-on-java.org/ultimate-guide-association-mappings-jpa-hibernate/
//        https://stackoverflow.com/questions/45566547/database-error-right-and-full-outer-joins-are-not-currently-supported
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(Config.LANGUAGE);
//        loader.load1();
        mainWordsSelector.nextWordsToReview(1);
//        List<Word> wordList = srAlgorithm.nextWordsToReview(3);
//        for (Word word : wordList) {
//            System.out.println(word.getOriginalWord());
//        }
//        System.out.println();

//        List<Word> wordList1 = newWordsSelector.nextWordsToReview(1);
//        System.out.println(newWordsSelector.nextWordsToReview(1).size());
//        for (Word word : wordList1) {
//            System.out.println(word.getOriginalWord());
//        }

    }
}
