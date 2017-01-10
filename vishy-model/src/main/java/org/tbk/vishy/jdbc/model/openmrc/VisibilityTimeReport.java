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
@Table(name = "vishy_openmrc_visibility_time_report")
public class VisibilityTimeReport {
    public static Optional<VisibilityTimeReport> create(OpenMrc.Request request) {
        return Optional.ofNullable(request)
                .filter(OpenMrc.Request::hasSummary)
                .map(OpenMrc.Request::getSummary)
                .map(s -> VisibilityTimeReport.builder()
                        .percentageMax(s.getReport().getPercentage().getMinimum())
                        .percentageMax(s.getReport().getPercentage().getMaximum())
                        .durationInMillis(s.getReport().getDuration())
                        .timeHidden(s.getReport().getTimeHidden())
                        .timeVisible(s.getReport().getTimeVisible())
                        .timeFullyVisible(s.getReport().getTimeFullyVisible())
                        .timeRelativeVisible(s.getReport().getTimeRelativeVisible())
                        .build());
    }

    @Tolerate
    public VisibilityTimeReport() {
    }

    @Id
    @SequenceGenerator(
            name = "vishy_openmrc_visibility_time_report_id_gen",
            sequenceName = "vishy_openmrc_visibility_time_report_id_seq")
    @GeneratedValue(
            strategy=SEQUENCE,
            generator = "vishy_openmrc_visibility_time_report_id_gen")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "percentage_min", updatable = false, nullable = false)
    private float percentageMin;

    @Column(name = "percentage_max", updatable = false, nullable = false)
    private float percentageMax;

    @Column(name = "duration", updatable = false, nullable = false)
    private long durationInMillis;

    @Column(name = "time_hidden", updatable = false, nullable = false)
    private long timeHidden;

    @Column(name = "time_visible", updatable = false, nullable = false)
    private long timeVisible;

    @Column(name = "time_fully_visible", updatable = false, nullable = false)
    private long timeFullyVisible;

    @Column(name = "time_relative_visible", updatable = false, nullable = true)
    private Long timeRelativeVisible;

}
