package app.tts;

import app.data.Word;

import java.util.List;

public interface TextToSpeech {
    List<Word> nextWordsToReview(int wordCount);
}
