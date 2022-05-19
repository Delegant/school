CREATE TABLE car (
                     id BIGINT PRIMARY KEY,
                     brand TEXT NOT NULL,
                     model TEXT NOT NULL,
                     price INTEGER NOT NULL
);

CREATE TABLE driver (
                        id BIGINT PRIMARY KEY,
                        name TEXT NOT NULL,
                        age INTEGER NOT NULL,
                        license BOOLEAN NOT NULL,
                        car_id BIGINT REFERENCES car (id)
);
