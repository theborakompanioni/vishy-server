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
@Table(name = "vishy_openmrc_summary_context")
public class SummaryContext {
    public static Optional<SummaryContext> create(OpenMrc.Request request) {
        return Optional.ofNullable(request)
                .filter(OpenMrc.Request::hasSummary)
                .flatMap(VisibilityTimeReport::create)
                .map(timeReport -> SummaryContext.builder()
                        .timeReport(timeReport)
                        .build());
    }

    @Tolerate
    public SummaryContext() {
    }

    @Id
    @SequenceGenerator(
            name = "vishy_openmrc_summary_context_id_gen",
            sequenceName = "vishy_openmrc_summary_context_id_seq")
    @GeneratedValue(
            strategy = SEQUENCE, generator = "vishy_openmrc_summary_context_id_gen")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "time_report_id")
    private VisibilityTimeReport timeReport;

    @OneToOne(mappedBy = "summaryContext")
    private PersistedOpenMrcRequest request;
}
