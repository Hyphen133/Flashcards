package app.data;

import app.repositories.query_results.WordSuccessfulRepetitions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Word {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "word_id")
	private int id;
	@Column(name = "original_word")
	private String originalWord;
	@Column(name = "translated_word")
	private String translatedWord;
	@Column(name = "language")
	private String language;
	@Column(name = "image_path")
	private String imagePath;
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "word")
	private List<Association> associations;
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "word")
	private List<Repetition> repetitions;

}
