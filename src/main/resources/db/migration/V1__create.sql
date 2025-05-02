CREATE SEQUENCE IF NOT EXISTS article_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS category_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS website_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE users (
                       chat_id BIGINT PRIMARY KEY NOT NULL,
                       brief_content_of_articles_status BOOLEAN NOT NULL DEFAULT FALSE,
                       message_storage_time_day BIGINT NOT NULL,
                       count_strings_in_one_page INT NOT NULL DEFAULT 5,
                       count_articles_in_one_request INT NOT NULL DEFAULT 3
);

CREATE TABLE categories (
                            id      BIGINT NOT NULL,
                            name    VARCHAR(255),
                            user_id BIGINT NOT NULL,
                            is_enabled BOOLEAN NOT NULL,
                            CONSTRAINT categories_pkey PRIMARY KEY (id),
                            CONSTRAINT fk_category_user FOREIGN KEY (user_id) REFERENCES users(chat_id) ON DELETE CASCADE
);

CREATE TABLE websites (
                          source_id   BIGINT NOT NULL DEFAULT nextval('website_id_seq'),
                          name        VARCHAR(255),
                          url         VARCHAR(255),
                          is_enabled  BOOLEAN NOT NULL,
                          user_id     BIGINT NOT NULL,
                          CONSTRAINT websites_pkey PRIMARY KEY (source_id),
                          CONSTRAINT fk_website_user FOREIGN KEY (user_id) REFERENCES users(chat_id) ON DELETE CASCADE
);

CREATE TABLE articles (
                          id            BIGINT NOT NULL,
                          name          VARCHAR(255),
                          url           VARCHAR(255),
                          creation_date TIMESTAMP NOT NULL DEFAULT NOW(),
                          website_id    BIGINT,
                          category_id   BIGINT,
                          user_id       BIGINT NOT NULL,
                          CONSTRAINT articles_pkey PRIMARY KEY (id),
                          CONSTRAINT fk_article_user FOREIGN KEY (user_id) REFERENCES users(chat_id) ON DELETE CASCADE,
                          CONSTRAINT fk_article_category FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL
);

CREATE TABLE messages (
                          chat_id BIGINT NOT NULL,
                          message_id INT NOT NULL,
                          is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
                          is_have_inline_keyboard BOOLEAN NOT NULL DEFAULT FALSE,
                          is_have_reply_keyboard BOOLEAN NOT NULL DEFAULT FALSE,
                          PRIMARY KEY (chat_id, message_id),
                          CONSTRAINT fk_message_user FOREIGN KEY (chat_id) REFERENCES users(chat_id) ON DELETE CASCADE
);

ALTER TABLE users
ADD COLUMN state VARCHAR(255),
ADD COLUMN temp_source_name VARCHAR(255),
ADD COLUMN temp_view_mode VARCHAR(255);