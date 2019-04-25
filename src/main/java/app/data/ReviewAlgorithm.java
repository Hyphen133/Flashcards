package app.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class ReviewAlgorithm {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "review_algorithm_id")
    private int id;
    @Column(name = "algorithm_name")
    private String name;

    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "reviewAlgorithm")
    private List<ReviewDay> reviewDays;

    public int getExpectedReviewDays(int daysFromReviewStart){
        int count = 0;
        for (ReviewDay reviewDay : reviewDays) {
            if(reviewDay.getDaysAfterBeginning() <= daysFromReviewStart){
                count++;
            }
        }

        return count;
    }
}
