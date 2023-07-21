DROP TABLE IF EXISTS genres CASCADE;

CREATE TABLE genres (
                        id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                        name varchar NOT NULL UNIQUE
);

DROP TABLE IF EXISTS authors CASCADE;

CREATE TABLE authors (
    --                        обязательные поля
                         id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                         name varchar NOT NULL UNIQUE,
                         dateOfBirth varchar NOT NULL,
    --                         необязательные поля
                         dateOfDeath varchar,
                         description varchar
);

INSERT INTO authors(name, dateOfBirth, dateOfDeath, description) VALUES
    ('Джейн Остен', '1939', '1945', 'Информация о писателе.Информация о писателе.Информация о писателе.Информация о писателе.'),
    ('Джордж Оруэлл', '1939', '1945', ''),
    ('Фрэнсис Скотт Фицджеральд', '1939', '1945', 'Информация о писателе.Информация о писателе.Информация о писателе.Информация о писателе.'),
    ('Луиза Мэй Олкотт', '1939', '1945', ''),
    ('Маргарет Митчелл', '1939', '', ''),
    ('Дж. Д. Сэлинджер', '1939', '', 'Информация о писателе.Информация о писателе.Информация о писателе.Информация о писателе.'),
    ('Марк Твен', '1939', '1945', ''),
    ('С. Л. Клайв', '1939', '', '');

DROP TABLE IF EXISTS books CASCADE;

CREATE TABLE books (
--                        обязательные поля
                       id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                       name varchar NOT NULL UNIQUE,
                       genre_id int NOT NULL REFERENCES genres(id) ON DELETE CASCADE,
                       author_id int NOT NULL REFERENCES authors(id) ON DELETE CASCADE,
                       status varchar NOT NULL,
                       year int NOT NULL check ( year > 0  AND year < 2050),
--                         необязательные поля
                       description varchar

);

DROP TABLE IF EXISTS clients CASCADE;

CREATE TABLE clients (
                         id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
--      обязательные поля
                         name varchar NOT NULL UNIQUE,
                         age int NOT NULL check (age > 0 AND age < 111),
                         email varchar NOT NULL UNIQUE,
                         sex varchar NOT NULL,
                         phoneNumber varchar UNIQUE NOT NULL,
--      необязательные поля
                         deliveryAddress varchar,
                         description varchar,
                         favoriteGenre varchar

);

INSERT INTO genres (name) VALUES
                              ('Роман'),
                              ('Антиутопия'),
                              ('Драма'),
                              ('Сатира'),
                              ('Фэнтези'),
                              ('Ужасы'),
                              ('Комедия');

INSERT INTO books (name, genre_id, author_id, year,description, status) VALUES
                                                     ('Гордость и предубеждение', (SELECT id FROM genres WHERE name = 'Роман'), (SELECT id FROM authors WHERE name = 'Джейн Остен'), 1813, '', 'Свободна'),
                                                     ('1984', (SELECT id FROM genres WHERE name = 'Антиутопия'), (SELECT id FROM authors WHERE name = 'Джордж Оруэлл'), 1948, '', 'Свободна'),
                                                     ('Великий Гэтсби', (SELECT id FROM genres WHERE name = 'Драма'), (SELECT id FROM authors WHERE name = 'Фрэнсис Скотт Фицджеральд'), 1926, '', 'Свободна'),
                                                     ('Маленькие женщины', (SELECT id FROM genres WHERE name = 'Драма'), (SELECT id FROM authors WHERE name = 'Луиза Мэй Олкотт'), 1868, '', 'Взята'),
                                                     ('Унесенные ветром', (SELECT id FROM genres WHERE name = 'Драма'), (SELECT id FROM authors WHERE name = 'Маргарет Митчелл'), 1936, '', 'Свободна'),
                                                     ('Скотный двор', (SELECT id FROM genres WHERE name = 'Сатира'), (SELECT id FROM authors WHERE name = 'Джордж Оруэлл'), 1945, '', 'Свободна'),
                                                     ('Над пропастью во ржи', (SELECT id FROM genres WHERE name = 'Роман'), (SELECT id FROM authors WHERE name = 'Дж. Д. Сэлинджер'), 1951, '', 'Свободна'),
                                                     ('Приключения Гекльберри Финна', (SELECT id FROM genres WHERE name = 'Роман'), (SELECT id FROM authors WHERE name = 'Марк Твен'), 1884, '', 'Свободна'),
                                                     ('Хроники Нарнии', (SELECT id FROM genres WHERE name = 'Фэнтези'), (SELECT id FROM authors WHERE name = 'С. Л. Клайв'), 1950, '', 'Взята');

INSERT INTO clients (name, age, email, sex, phoneNumber,favoriteGenre, description) VALUES
                                                             ('Березнев Никита', 20, 'bernikcooldude@yandex.ru', 'Мужчина', '89031111112', '-', '-'),
                                                             ('Дин Норрис', 34, 'dnorris@yandex.ru', 'Мужчина', '89031111114', '-', '-'),
                                                             ('Мишель Томпсон', 16, 'mthompson@yandex.ru', 'Женщина', '89031111115', '-', '-'),
                                                             ('Дженнифер Лоуренз', 16, 'jlawrense@gmail.ru', 'Женщина', '89031111611', '-', '-'),
                                                             ('Скарлетт Йохансон', 16, 'scarlet@gmail.ru', 'Женщина', '89031111117', '-', '-'),
                                                             ('Крис Эванс', 35, 'kevans@gmail.ru', 'Мужчина', '89031111811', '-', '-'),
                                                             ('Хью Джекман', 20, 'hughy@gmail.ru', 'Мужчина', '89031111511', '-', '-'),
                                                             ('Мэтью Макконахи', 20, 'mattewmc@mail.ru', 'Мужчина', '89231111111', '-', '-');

DROP TABLE IF EXISTS orders CASCADE;

CREATE TABLE orders (
                        id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                        client_id int NOT NULL REFERENCES clients(id) ON DELETE CASCADE,
                        book_id int NOT NULL UNIQUE REFERENCES books(id) ON DELETE CASCADE
);

INSERT INTO orders (client_id, book_id) VALUES
((SELECT id from clients WHERE name = 'Хью Джекман'), (SELECT id from books WHERE name = 'Маленькие женщины')),
((SELECT id from clients WHERE name = 'Хью Джекман'), (SELECT id from books WHERE name = 'Хроники Нарнии'));