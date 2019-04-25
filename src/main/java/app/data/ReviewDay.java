package app.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class ReviewDay {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "review_day_id")
    private int id;
    @Column(name = "days_after_beginning")
    private int daysAfterBeginning;
    @ManyToOne
    @JoinColumn(name = "fk_review_algorithm_id")
    private ReviewAlgorithm reviewAlgorithm;

}
