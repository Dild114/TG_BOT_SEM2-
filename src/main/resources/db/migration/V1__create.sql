CREATE TABLE users (
                       chat_id BIGINT PRIMARY KEY NOT NULL,
                       brief_content_of_articles_status BOOLEAN NOT NULL DEFAULT FALSE,
                       message_storage_time_day BIGINT NOT NULL,
                       count_strings_in_one_page INT NOT NULL DEFAULT 5,
                       count_articles_in_one_request INT NOT NULL DEFAULT 3
);

CREATE TABLE categories (
                            id BIGINT NOT NULL,
                            name VARCHAR(255),
                            user_id BIGINT NOT NULL,
                            is_enabled BOOLEAN NOT NULL,
                            CONSTRAINT categories_pkey PRIMARY KEY (id),
                            CONSTRAINT fk_category_user FOREIGN KEY (user_id) REFERENCES users(chat_id) ON DELETE CASCADE
);

CREATE TABLE websites (
                          id BIGINT NOT NULL,
                          url VARCHAR(255),
                          user_id BIGINT NOT NULL,
                          is_enabled BOOLEAN NOT NULL,
                          CONSTRAINT websites_pkey PRIMARY KEY (id),
                          CONSTRAINT fk_website_user FOREIGN KEY (user_id) REFERENCES users(chat_id) ON DELETE CASCADE
);

CREATE TABLE articles (
                          id BIGINT NOT NULL,
                          name VARCHAR(255),
                          url VARCHAR(255),
                          creation_date TIMESTAMP NOT NULL DEFAULT NOW(),
                          website_id BIGINT NOT NULL,
                          category_id BIGINT NOT NULL,
                          user_id BIGINT NOT NULL,
                          CONSTRAINT articles_pkey PRIMARY KEY (id),
                          CONSTRAINT fk_article_website FOREIGN KEY (website_id) REFERENCES websites(id) ON DELETE CASCADE,
                          CONSTRAINT fk_article_category FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE,
                          CONSTRAINT fk_article_user FOREIGN KEY (user_id) REFERENCES users(chat_id) ON DELETE CASCADE
);