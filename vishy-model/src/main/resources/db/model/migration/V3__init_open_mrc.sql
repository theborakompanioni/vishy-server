CREATE TABLE vishy_openmrc_percentage_report (
  id            BIGSERIAL NOT NULL,
  percentage    FLOAT     NOT NULL DEFAULT 0,
  fully_visible BOOLEAN   NOT NULL DEFAULT FALSE,
  visible       BOOLEAN   NOT NULL DEFAULT FALSE,
  hidden        BOOLEAN   NOT NULL DEFAULT FALSE,
  PRIMARY KEY (id)
);

CREATE TABLE vishy_openmrc_initial_context (
  id                   BIGSERIAL NOT NULL,
  percentage_report_id BIGINT    NOT NULL REFERENCES vishy_openmrc_percentage_report (ID),
  PRIMARY KEY (id)
);

CREATE TABLE vishy_openmrc_visibility_time_report (
  id                    BIGSERIAL NOT NULL,
  duration              INTEGER   NOT NULL DEFAULT 0,
  percentage_min        FLOAT     NOT NULL DEFAULT 0,
  percentage_max        FLOAT     NOT NULL DEFAULT 0,
  time_hidden           INTEGER   NOT NULL DEFAULT 0,
  time_visible          INTEGER   NOT NULL DEFAULT 0,
  time_fully_visible    INTEGER   NOT NULL DEFAULT 0,
  time_relative_visible INTEGER            DEFAULT 0,
  PRIMARY KEY (id)
);

CREATE TABLE vishy_openmrc_summary_context (
  id             BIGSERIAL NOT NULL,
  time_report_id BIGINT    NOT NULL REFERENCES vishy_openmrc_visibility_time_report (ID),
  PRIMARY KEY (id)
);

CREATE TABLE vishy_openmrc_request (
  id         BIGSERIAL   NOT NULL,
  project_id BIGINT NOT NULL REFERENCES project (ID),
  experiment_id BIGINT NOT NULL REFERENCES experiment (ID),
  type       VARCHAR(32) NOT NULL,
  json       VARCHAR(10000), -- TODO: try to remove or use jsonb
  initial_id BIGINT REFERENCES vishy_openmrc_initial_context (ID),
  summary_id BIGINT REFERENCES vishy_openmrc_summary_context (ID),
  PRIMARY KEY (id)
);


