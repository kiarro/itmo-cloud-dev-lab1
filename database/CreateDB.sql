CREATE TABLE caves (
    cave_id BIGSERIAL PRIMARY KEY,
    depth REAL NOT NULL,
    number_of_treasures DOUBLE PRECISION NOT NULL,
    CONSTRAINT CHK_NofT CHECK (number_of_treasures>0)
);

CREATE TABLE dragons (
    dragon_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    coordinate_x REAL NOT NULL,
    coordinate_y REAL NOT NULL,
    creation_date TIMESTAMP WITH TIME ZONE NOT NULL,
    age BIGINT NOT NULL,
    weight BIGINT NOT NULL,
    type VARCHAR(15) NOT NULL,
    character VARCHAR(15) NOT NULL,
    cave_id BIGINT NOT NULL,
    CONSTRAINT CHK_drid CHECK (dragon_id>0),
    CONSTRAINT CHK_name CHECK (NOT name=''),
    CONSTRAINT CHK_age CHECK (age>0),
    CONSTRAINT CHK_weight CHECK (weight>0),
    CONSTRAINT FK_cave FOREIGN KEY (cave_id) REFERENCES caves(cave_id),
)  