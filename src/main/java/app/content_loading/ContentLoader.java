package app.content_loading;

import app.data.Association;
import app.data.Word;

import java.util.List;

public interface ContentLoader {
    void addAssociationForWord(Word word, String associationWord);
    List<Association> getAllAssociationsForWord(Word word);
    void deleteAssocationFromWord(Word word, String associationWord);
}
