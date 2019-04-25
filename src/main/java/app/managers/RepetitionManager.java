package app.managers;

import app.data.Word;

public interface RepetitionManager {
    void saveRepetition(Word word, double successRate);
}
