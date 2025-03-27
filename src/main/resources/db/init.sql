CREATE TABLE post (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    image_path TEXT,
    text TEXT NOT NULL,
    likes_count INT DEFAULT 0
);

CREATE TABLE tag (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE post_tag (
    post_id BIGINT REFERENCES post(id) ON DELETE CASCADE,
    tag_id BIGINT REFERENCES tag(id) ON DELETE CASCADE,
    PRIMARY KEY (post_id, tag_id)
);

CREATE TABLE comment (
    id BIGSERIAL PRIMARY KEY,
    post_id BIGINT REFERENCES post(id) ON DELETE CASCADE,
    text TEXT NOT NULL
);
