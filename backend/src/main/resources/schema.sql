CREATE TABLE IF NOT EXISTS member (
    username VARCHAR(50),
    pass VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL,
    active BOOLEAN NOT NULL,

    PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS note (
    n_id CHAR(32),
    user_fk VARCHAR(50) NOT NULL,

    PRIMARY KEY (n_id),
    FOREIGN KEY (user_fk) REFERENCES member(username) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS category (
    c_id CHAR(32),
    c_name VARCHAR(50) NOT NULL,
    c_color VARCHAR(7) NOT NULL,
    note_fk CHAR(32) NOT NULL,

    PRIMARY KEY (c_id),
    FOREIGN KEY (note_fk) REFERENCES note(n_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS months (
    m_id CHAR(32),
    years INT,
    months INT,
    note_fk CHAR(32) NOT NULL,

    PRIMARY KEY (m_id),
    FOREIGN KEY (note_fk) REFERENCES note(n_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS days (
    d_id CHAR(32),
    detail VARCHAR(255) NOT NULL,
    transaction_value DECIMAL(10, 2) NOT NULL,
    note VARCHAR(255),
    noted_date DATE NOT NULL,
    month_fk CHAR(32) NOT NULL,
    category_fk CHAR(32),

    PRIMARY KEY (d_id),
    FOREIGN KEY (month_fk) REFERENCES months(m_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (category_fk) REFERENCES category(c_id) ON DELETE CASCADE ON UPDATE CASCADE
);