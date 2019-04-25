package app.managers;

import app.data.Association;
import app.data.Word;
import app.repositories.AssociationRepository;
import app.repositories.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssociationManagerImpl implements AssociationManager {
    @Autowired
    AssociationRepository associationRepository;

    @Autowired
    WordRepository wordRepository;


    @Override
    public void addAssociationForWord(Word word, String associationWord) {
        Association association = Association.builder().associationWord(associationWord).word(word).build();
        word.getAssociations().add(association);
        wordRepository.save(word);
        associationRepository.save(association);    //not sure if doesn't get cascaded
    }

    @Override
    public List<Association> getAllAssociationsForWord(Word word) {
        return word.getAssociations();
    }

    @Override
    public void deleteAssociationFromWord(Word word, String associationWord) {
        Association association = word.getAssociations().stream().filter(x -> x.getAssociationWord() == associationWord).findFirst().get();
        word.getAssociations().remove(association);
        wordRepository.save(word);
        associationRepository.delete(association);

    }

}
