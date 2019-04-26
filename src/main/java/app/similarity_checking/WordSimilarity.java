package app.similarity_checking;

import app.data.Word;

public interface WordSimilarity{
    double checkSimilarity(Word word, String text);
}