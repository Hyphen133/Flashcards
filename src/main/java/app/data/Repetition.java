package app.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Repetition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "repetition_id")
    private int id;
//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "repetition_date", columnDefinition="DATETIME(3)")
    private LocalDateTime date;
    @Column(name = "successRate")
    private double successRate;
    @ManyToOne
    @JoinColumn(name = "fk_word_id")
    private Word word;

}
