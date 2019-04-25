package app.managers;

import app.data.Association;
import app.data.Word;

import java.util.List;

public interface AssociationManager {
    void addAssociationForWord(Word word, String associationWord);
    List<Association> getAllAssociationsForWord(Word word);
    void deleteAssociationFromWord(Word word, String associationWord);
}
