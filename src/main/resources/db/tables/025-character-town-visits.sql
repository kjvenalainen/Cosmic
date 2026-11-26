CREATE TABLE charactertownvisits
(
    characterid INT NOT NULL,
    mapid       INT NOT NULL,
    PRIMARY KEY (characterid, mapid),
    FOREIGN KEY (characterid) REFERENCES characters (id) ON DELETE CASCADE
);
