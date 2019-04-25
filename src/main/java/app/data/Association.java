package app.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Association {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "association_id")
    private int id;
    @Column(name = "association_word")
    private String associationWord;
    @ManyToOne
    @JoinColumn(name = "fk_word_id")
    private Word word;
}
