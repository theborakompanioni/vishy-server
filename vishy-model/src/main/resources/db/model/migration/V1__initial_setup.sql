CREATE TABLE customer (
  id         BIGSERIAL,
  created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  type       VARCHAR(32)  NOT NULL,
  name       VARCHAR(128) NOT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE project (
  id          BIGSERIAL,
  customer_id BIGSERIAL REFERENCES customer (ID),
  created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  type        VARCHAR(32)  NOT NULL,
  name        VARCHAR(128) NOT NULL,
  description VARCHAR(1024),
  PRIMARY KEY (id)
);


CREATE TABLE experiment (
  id          BIGSERIAL,
  project_id  BIGSERIAL REFERENCES project (ID),
  created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  type        VARCHAR(32)  NOT NULL,
  name        VARCHAR(128) NOT NULL,
  description VARCHAR(1024),
  PRIMARY KEY (id)
);

CREATE TABLE vishy_openmrc_request (
  id   BIGSERIAL,
  type VARCHAR(32)    NOT NULL,
  json VARCHAR(10000) NOT NULL,
  PRIMARY KEY (id)
);
