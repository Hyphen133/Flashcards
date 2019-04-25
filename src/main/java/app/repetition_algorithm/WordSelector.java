package app.repetition_algorithm;

import app.data.Word;

import java.util.List;


public interface WordSelector {
    List<Word> nextWordsToReview(int wordCount) throws InsufficientWordCountException;
}
