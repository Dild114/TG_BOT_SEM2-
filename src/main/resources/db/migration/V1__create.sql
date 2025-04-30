CREATE SEQUENCE IF NOT EXISTS article_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS category_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS user_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS website_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE users (
   id                           BIGINT NOT NULL,
   name                         VARCHAR(255),
   telegram_id                  VARCHAR(255),
   is_short_description_enabled BOOLEAN NOT NULL,
   is_subscribe_enabled         BOOLEAN NOT NULL,
   message_storage_time_day     BIGINT NOT NULL,
   count_element_on_table       BIGINT NOT NULL,
   CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE categories (
   id      BIGINT NOT NULL,
   name    VARCHAR(255),
   user_id BIGINT NOT NULL,
   is_enabled BOOLEAN NOT NULL,
   CONSTRAINT categories_pkey PRIMARY KEY (id),
   CONSTRAINT fk_category_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE websites (
   id      BIGINT NOT NULL,
   url     VARCHAR(255),
   user_id BIGINT NOT NULL,
   is_enabled BOOLEAN NOT NULL,
   CONSTRAINT websites_pkey PRIMARY KEY (id),
   CONSTRAINT fk_website_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE articles (
   id            BIGINT NOT NULL,
   name          VARCHAR(255),
   url           VARCHAR(255),
   creation_date TIMESTAMP NOT NULL DEFAULT NOW(),
   website_id    BIGINT NOT NULL,
   category_id   BIGINT NOT NULL,
   CONSTRAINT articles_pkey PRIMARY KEY (id),
   CONSTRAINT fk_article_website FOREIGN KEY (website_id) REFERENCES websites(id) ON DELETE CASCADE,
   CONSTRAINT fk_article_category FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);

-- пусть избранное пока останется
-- CREATE TABLE favourite_articles_of_user (
--     article_id BIGINT NOT NULL,
--     user_id    BIGINT NOT NULL,
--     CONSTRAINT favourite_articles_of_user_pkey PRIMARY KEY (article_id, user_id),
--     CONSTRAINT fk_fav_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
--     CONSTRAINT fk_fav_article FOREIGN KEY (article_id) REFERENCES articles(id) ON DELETE CASCADE
-- );


-- DELETE FROM articles
-- WHERE creation_date < NOW() - INTERVAL 'N days'; удаление статьи