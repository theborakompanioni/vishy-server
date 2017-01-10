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
@Builder()
@Entity
@Table(name = "vishy_openmrc_percentage_report")
public class PercentageReport {
    public static Optional<PercentageReport> create(OpenMrc.Request request) {
        return Optional.ofNullable(request)
                .filter(OpenMrc.Request::hasInitial)
                .map(OpenMrc.Request::getInitial)
                .map(OpenMrc.InitialContext::getState)
                .map(s -> PercentageReport.builder()
                        .percentage(s.getPercentage())
                        .visible(s.getVisible())
                        .fullyVisible(s.getFullyvisible())
                        .hidden(s.getHidden())
                        .build());
    }

    @Tolerate
    public PercentageReport() {
    }

    @Id
    @SequenceGenerator(
            name = "vishy_openmrc_percentage_report_id_gen",
            sequenceName = "vishy_openmrc_percentage_report_id_seq")
    @GeneratedValue(
            strategy=SEQUENCE, generator = "vishy_openmrc_percentage_report_id_gen")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "percentage", updatable = false, nullable = false)
    private float percentage;

    @Column(name = "visible", updatable = false, nullable = false)
    private boolean visible;

    @Column(name = "fully_visible", updatable = false, nullable = false)
    private boolean fullyVisible;

    @Column(name = "hidden", updatable = false, nullable = false)
    private boolean hidden;
}
