CREATE SEQUENCE IF NOT EXISTS article_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS category_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS user_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS website_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE articles
(
    category_id BIGINT,
    id          BIGINT NOT NULL,
    website_id  BIGINT,
    name        VARCHAR(255),
    url         VARCHAR(255),
    CONSTRAINT articles_pkey PRIMARY KEY (id)
);

CREATE TABLE categories
(
    id   BIGINT NOT NULL,
    name VARCHAR(255),
    CONSTRAINT categories_pkey PRIMARY KEY (id)
);

CREATE TABLE category_of_user
(
    category_id BIGINT NOT NULL,
    user_id     BIGINT NOT NULL,
    CONSTRAINT category_of_user_pkey PRIMARY KEY (category_id, user_id)
);

CREATE TABLE website_of_user
(
    user_id    BIGINT NOT NULL,
    website_id BIGINT NOT NULL,
    CONSTRAINT website_of_user_pkey PRIMARY KEY (user_id, website_id)
);

ALTER TABLE articles
    ADD CONSTRAINT fk7i4rryg7kqwyyrr08temnc71e FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE NO ACTION;

ALTER TABLE category_of_user
    ADD CONSTRAINT fkegnswei26y3fh72q81ut1u3nc FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE NO ACTION,
    ADD COLUMN enabled BOOLEAN DEFAULT true;
