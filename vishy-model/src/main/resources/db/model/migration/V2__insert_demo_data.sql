
INSERT INTO customer (type, name) VALUES ('DEMO', 'demo customer');
INSERT INTO project (customer_id, type, name, description) VALUES (1, 'DEMO', 'demo project', 'demo project');
INSERT INTO experiment (project_id, type, name, description) VALUES (1, 'DEMO1', 'demo experiment', 'demo experiment');
INSERT INTO experiment (project_id, type, name, description) VALUES (1, 'DEMO2', 'demo experiment', 'demo experiment');
