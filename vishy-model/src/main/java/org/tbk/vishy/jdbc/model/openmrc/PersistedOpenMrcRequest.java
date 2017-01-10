package org.tbk.vishy.jdbc.model.openmrc;

import com.github.theborakompanioni.openmrc.OpenMrc;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "vishy_openmrc_request")
public class PersistedOpenMrcRequest {

    public static PersistedOpenMrcRequestBuilder builder() {
        return new PersistedOpenMrcRequestBuilder();
    }

    public static PersistedOpenMrcRequestBuilder create(OpenMrc.Request request) {
        final PersistedOpenMrcRequestBuilder builder = PersistedOpenMrcRequest.builder()
                .type(request.getType().name());

        SummaryContext.create(request).ifPresent(builder::summaryContext);
        InitialContext.create(request).ifPresent(builder::initialContext);
        return builder;
    }

    public PersistedOpenMrcRequest() {
    }

    public PersistedOpenMrcRequest(Long id, String type, InitialContext initialContext, SummaryContext summaryContext) {
        this.id = id;
        this.type = type;
        this.initialContext = initialContext;
        this.summaryContext = summaryContext;
    }

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

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

        public PersistedOpenMrcRequest.PersistedOpenMrcRequestBuilder initialContext(InitialContext initialContext) {
            this.initialContext = initialContext;
            return this;
        }

        public PersistedOpenMrcRequest.PersistedOpenMrcRequestBuilder summaryContext(SummaryContext summaryContext) {
            this.summaryContext = summaryContext;
            return this;
        }

        public PersistedOpenMrcRequest build() {
            return new PersistedOpenMrcRequest(id, type, initialContext, summaryContext);
        }

        public String toString() {
            return "org.tbk.vishy.jdbc.model.openmrc.PersistedOpenMrcRequest.PersistedOpenMrcRequestBuilder(id=" + this.id + ", type=" + this.type + ", initialContext=" + this.initialContext + ", summaryContext=" + this.summaryContext + ")";
        }
    }
}
