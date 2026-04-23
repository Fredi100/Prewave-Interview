CREATE TABLE IF NOT EXISTS edge (
    from_id INTEGER NOT NULL,
    to_id INTEGER NOT NULL,
    PRIMARY KEY (from_id, to_id)
);

INSERT INTO edge (from_id, to_id) VALUES
    (1, 2),
    (1, 3),
    (2, 4),
    (2, 5),
    (3, 6)
ON CONFLICT DO NOTHING;
