package org.tbk.vishy.jdbc.model.openmrc;

import com.github.theborakompanioni.openmrc.OpenMrc;
import com.github.theborakompanioni.openmrc.VishyOpenMrcExtensions.Vishy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.google.common.base.Preconditions.checkArgument;

@Data
@Entity
@Table(name = "vishy_openmrc_request")
@NoArgsConstructor
@AllArgsConstructor
public class PersistedOpenMrcRequest {

    public static PersistedOpenMrcRequestBuilder builder() {
        return new PersistedOpenMrcRequestBuilder();
    }

    public static PersistedOpenMrcRequestBuilder create(OpenMrc.Request request) {
        checkArgument(request.hasExtension(Vishy.vishy), "request is missing extension 'vishy'");

        final Vishy vishyDetails = request.getExtension(Vishy.vishy);

        final PersistedOpenMrcRequestBuilder builder = PersistedOpenMrcRequest.builder()
                .projectId(Long.parseLong(vishyDetails.getProjectId()))
                .experimentId(Long.parseLong(vishyDetails.getExperimentId()))
                .type(request.getType().name());

        SummaryContext.create(request).ifPresent(builder::summaryContext);
        InitialContext.create(request).ifPresent(builder::initialContext);
        return builder;
    }

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "project_id", updatable = false)
    private long projectId;

    @Column(name = "experiment_id", updatable = false)
    private long experimentId;

    @Column(name = "type")
    private String type;


    //@Column(name = "json")
    //private String json;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "initial_id")
    private InitialContext initialContext;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "summary_id")
    private SummaryContext summaryContext;

    public static class PersistedOpenMrcRequestBuilder {
        private Long id;
        private String type;
        private InitialContext initialContext;
        private SummaryContext summaryContext;
        private long projectId;
        private long experimentId;

        PersistedOpenMrcRequestBuilder() {
        }

        public PersistedOpenMrcRequest.PersistedOpenMrcRequestBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PersistedOpenMrcRequest.PersistedOpenMrcRequestBuilder type(String type) {
            this.type = type;
            return this;
        }

        public PersistedOpenMrcRequest.PersistedOpenMrcRequestBuilder projectId(long projectId) {
            this.projectId = projectId;
            return this;
        }

        public PersistedOpenMrcRequest.PersistedOpenMrcRequestBuilder experimentId(long experimentId) {
            this.experimentId = experimentId;
            return this;
        }

        public PersistedOpenMrcRequest.PersistedOpenMrcRequestBuilder initialContext(InitialContext initialContext) {
            this.initialContext = initialContext;
            return this;
        }

        public PersistedOpenMrcRequest.PersistedOpenMrcRequestBuilder summaryContext(SummaryContext summaryContext) {
            this.summaryContext = summaryContext;
            return this;
        }

        public PersistedOpenMrcRequest build() {
            return new PersistedOpenMrcRequest(id, projectId, experimentId, type, initialContext, summaryContext);
        }

        public String toString() {
            return "org.tbk.vishy.jdbc.model.openmrc.PersistedOpenMrcRequest.PersistedOpenMrcRequestBuilder(id=" + this.id + ", type=" + this.type + ", initialContext=" + this.initialContext + ", summaryContext=" + this.summaryContext + ")";
        }
    }
}
