CREATE SEQUENCE IF NOT EXISTS article_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS category_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS user_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS website_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE articles
(
    id            BIGINT NOT NULL,
    creation_date VARCHAR(255),
    name          VARCHAR(255),
    url           VARCHAR(255),
    CONSTRAINT articles_pkey PRIMARY KEY (id)
);

CREATE TABLE categories
(
    id   BIGINT NOT NULL,
    name VARCHAR(255),
    CONSTRAINT categories_pkey PRIMARY KEY (id)
);

CREATE TABLE categories_of_article
(
    article_id  BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    CONSTRAINT categories_of_article_pkey PRIMARY KEY (article_id, category_id)
);

CREATE TABLE category_of_user
(
    category_id BIGINT NOT NULL,
    user_id     BIGINT NOT NULL,
    enabled     BOOLEAN not null DEFAULT true,
    CONSTRAINT category_of_user_pkey PRIMARY KEY (category_id, user_id)
);

CREATE TABLE favourite_articles_of_user
(
    article_id BIGINT NOT NULL,
    user_id    BIGINT NOT NULL,
    CONSTRAINT favourite_articles_of_user_pkey PRIMARY KEY (article_id, user_id)
);

CREATE TABLE flyway_schema_history
(
    installed_rank INTEGER       NOT NULL,
    version        VARCHAR(50),
    description    VARCHAR(200)  NOT NULL,
    type           VARCHAR(20)   NOT NULL,
    script         VARCHAR(1000) NOT NULL,
    checksum       INTEGER,
    installed_by   VARCHAR(100)  NOT NULL,
    installed_on   TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    execution_time INTEGER       NOT NULL,
    success        BOOLEAN       NOT NULL,
    CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank)
);

CREATE TABLE users
(
    is_short_description_enabled BOOLEAN NOT NULL,
    is_subscribe_enabled         BOOLEAN NOT NULL,
    id                           BIGINT  NOT NULL,
    name                         VARCHAR(255),
    telegram_id                  VARCHAR(255),
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE website_of_user
(
    user_id    BIGINT NOT NULL,
    website_id BIGINT NOT NULL,
    CONSTRAINT website_of_user_pkey PRIMARY KEY (user_id, website_id)
);

CREATE TABLE websites
(
    id  BIGINT NOT NULL,
    url VARCHAR(255),
    CONSTRAINT websites_pkey PRIMARY KEY (id)
);

CREATE INDEX flyway_schema_history_s_idx ON flyway_schema_history (success);

ALTER TABLE categories_of_article
    ADD CONSTRAINT fk5v5o952fy8h2501w2b7hwh66u FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE NO ACTION;

ALTER TABLE category_of_user
    ADD CONSTRAINT fk9glqdwxu0ob5m9nhex7ga608i FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE NO ACTION;

ALTER TABLE favourite_articles_of_user
    ADD CONSTRAINT fkafp6e3bisvlq09151t70hxw0x FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE NO ACTION;

ALTER TABLE website_of_user
    ADD CONSTRAINT fkaw1yus7iihkyuhkwvkk14jjdc FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE NO ACTION;

ALTER TABLE website_of_user
    ADD CONSTRAINT fkc9vkcbet9yjyv7druilwrf386 FOREIGN KEY (website_id) REFERENCES websites (id) ON DELETE NO ACTION;

ALTER TABLE category_of_user
    ADD CONSTRAINT fkegnswei26y3fh72q81ut1u3nc FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE NO ACTION;

ALTER TABLE categories_of_article
    ADD CONSTRAINT fkj5guhja4b9uiaibnm93eajusn FOREIGN KEY (article_id) REFERENCES articles (id) ON DELETE NO ACTION;

ALTER TABLE favourite_articles_of_user
    ADD CONSTRAINT fkqrv815qidql08cx0sydwq89pb FOREIGN KEY (article_id) REFERENCES articles (id) ON DELETE NO ACTION;