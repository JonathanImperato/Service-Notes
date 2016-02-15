DROP TABLE IF EXISTS note;

CREATE TABLE note (
    _id INTEGER PRIMARY KEY AUTOINCREMENT,
    title VARCHAR(50) NULL,
    content TEXT NOT NULL,
    content2 TEXT,
    emails TEXT,
    phone INTEGER,
    created_at BIGINT NOT NULL,
    updated_at BIGINT NOT NULL,
    UNIQUE (created_at)
);