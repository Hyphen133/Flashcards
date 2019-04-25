package app.managers;

import app.data.Repetition;
import app.data.Word;
import app.repositories.RepetitionRepository;
import app.repositories.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RepetitionMangerImpl implements RepetitionManager {
    @Autowired
    WordRepository wordRepository;

    @Autowired
    RepetitionRepository repetitionRepository;

    @Override
    public void saveRepetition(Word word, double successRate) {
        Repetition repetition = Repetition.builder().date(LocalDateTime.now()).word(word).successRate(successRate).build();
        word.getRepetitions().add(repetition);

        wordRepository.save(word);
        repetitionRepository.save(repetition);

    }
}
