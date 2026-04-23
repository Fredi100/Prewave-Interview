CREATE TABLE IF NOT EXISTS edge (
    from_id INTEGER NOT NULL,
    to_id INTEGER NOT NULL,
    PRIMARY KEY (from_id, to_id)
);
