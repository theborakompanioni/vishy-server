CREATE TABLE customer (
  id         BIGSERIAL    NOT NULL,
  created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  type       VARCHAR(32)  NOT NULL,
  name       VARCHAR(128) NOT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE project (
  id          BIGSERIAL    NOT NULL,
  customer_id BIGSERIAL REFERENCES customer (ID),
  created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  type        VARCHAR(32)  NOT NULL,
  name        VARCHAR(128) NOT NULL,
  description VARCHAR(1024),
  PRIMARY KEY (id)
);


CREATE TABLE experiment (
  id          BIGSERIAL    NOT NULL,
  project_id  BIGSERIAL REFERENCES project (ID),
  created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  type        VARCHAR(32)  NOT NULL,
  name        VARCHAR(128) NOT NULL,
  description VARCHAR(1024),
  PRIMARY KEY (id)
);
