package org.tbk.vishy.jdbc.model.openmrc;

import com.github.theborakompanioni.openmrc.OpenMrc;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.util.Optional;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@Builder
@Entity
@Table(name = "vishy_openmrc_initial_context")
public class InitialContext {

    public static Optional<InitialContext> create(OpenMrc.Request request) {
        return Optional.ofNullable(request)
                .filter(OpenMrc.Request::hasInitial)
                .flatMap(PercentageReport::create)
                .map(timeReport -> InitialContext.builder()
                        .percentageReport(timeReport)
                        .build());
    }

    @Tolerate
    public InitialContext() {
    }

    @Id
    @SequenceGenerator(
            name = "vishy_openmrc_initial_context_id_gen",
            sequenceName = "vishy_openmrc_initial_context_id_seq")
    @GeneratedValue(
            strategy = SEQUENCE, generator = "vishy_openmrc_initial_context_id_gen")
    @Column(name = "id", updatable = false, insertable = false, nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "percentage_report_id")
    private PercentageReport percentageReport;
}
