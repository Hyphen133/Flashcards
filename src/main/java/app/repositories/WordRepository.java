package app.repositories;

import app.data.Word;
import app.repositories.query_results.WordReviewDays;
import app.repositories.query_results.WordSuccessfulRepetitions;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;


public interface WordRepository extends CrudRepository<Word, Integer> {
    Word findOneByOriginalWordAndLanguage(String originalWord, String language);



}
