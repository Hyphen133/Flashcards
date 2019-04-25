package app.repetition_algorithm;

import app.config.Config;
import app.data.Word;
import app.repositories.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewWordsSelector implements WordSelector {
    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private EntityManager em;

    @Override
    public List<Word> nextWordsToReview(int wordCount) throws InsufficientWordCountException {

        Query query = em.createNativeQuery(
                "SELECT word.original_word FROM word\n" +
                        "WHERE word.word_id NOT IN (\n" +
                        "\tSELECT DISTINCT repetition.fk_word_id FROM repetition\n" +
                        ")\n" +
                        "AND word.language = ?");

        query.setParameter(1, Config.LANGUAGE);

        List<String> wordList = query.getResultList();
        Collections.shuffle(wordList);

        if(wordList.size() < wordCount){
            throw new InsufficientWordCountException("There are only " + wordList.size() + " words left, this is less than needed " + wordCount);

        }

        List<String> wordSublist = wordList.subList(0,wordCount);

        return wordSublist.stream().map(x -> wordRepository.findOneByOriginalWordAndLanguage(x, Config.LANGUAGE)).collect(Collectors.toList());
    }
}
