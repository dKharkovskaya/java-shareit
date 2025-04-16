CREATE TABLE IF NOT EXISTS users (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(512) NOT NULL,
  CONSTRAINT pk_user PRIMARY KEY (id),
  CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS items
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name        VARCHAR(255)                            NOT NULL,
    description VARCHAR(255)                            NOT NULL,
    available   BOOLEAN,
    owner_id    BIGINT,
    request_id  BIGINT,
    CONSTRAINT pk_item_items PRIMARY KEY (id),
    CONSTRAINT fk_owner_id_items FOREIGN KEY (owner_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS item_requests (
    id LONG GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    description VARCHAR(250),
    requestor LONG NOT NULL REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    created DATETIME DEFAULT (now())
);

CREATE TABLE IF NOT EXISTS bookings
(
    id              BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    start_date TIMESTAMP                               NOT NULL,
    end_date   TIMESTAMP                               NOT NULL,
    item_id         BIGINT                                  NOT NULL,
    booker_id       BIGINT                                  NOT NULL,
    status          VARCHAR(20) DEFAULT 'WAITING'           NOT NULL,
    CONSTRAINT pk_booking PRIMARY KEY (id),
    CONSTRAINT fk_item_id_bookings FOREIGN KEY (item_id) REFERENCES items (id) ON DELETE CASCADE,
    CONSTRAINT fk_booker_id_bookings FOREIGN KEY (booker_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS comments
(
    id                BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    text              VARCHAR(255)                            NOT NULL,
    item_id           BIGINT                                  NOT NULL,
    author_id         BIGINT                                  NOT NULL,
    created           TIMESTAMP                               NOT NULL,
    CONSTRAINT pk_comments PRIMARY KEY (id),
    CONSTRAINT fk_item_id_comments FOREIGN KEY (item_id) REFERENCES items (id) ON DELETE CASCADE,
    CONSTRAINT fk_author_id_comments FOREIGN KEY (author_id) REFERENCES users (id) ON DELETE CASCADE
);