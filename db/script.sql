create table USERS (
    id INT NOT NULL,
    fname TEXT NOT NULL,
    lname TEXT NOT NULL,
    login CHAR(20) NOT NULL,
    pass_hash TEXT NOT NULL,
    is_blocked SMALLINT NOT NULL,
    PRIMARY KEY (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

insert into USERS (id, fname, lname, login, pass_hash, is_blocked) values (174947681, 'root', 'root', 'root', 'e10adc3949ba59abbe56e057f20f883e', 0); -- Password is 123456

CREATE UNIQUE INDEX idx_users_login
    ON USERS (login);


create table CONTEXTS (
     id INT NOT NULL AUTO_INCREMENT,
     name TEXT NOT NULL,
     user_id INT not null,
     is_archived SMALLINT not NULL,
     PRIMARY KEY (id),
     FOREIGN KEY (user_id) REFERENCES USERS(id)  ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

create table LISTS (
     id INT NOT NULL AUTO_INCREMENT,
     name TEXT NOT NULL,
     creation DATETIME not null,  
     context_id INT NOT NULL,
     is_archived SMALLINT NOT NULL,
     is_pinned SMALLINT NOT NULL,
     PRIMARY KEY (id),
     FOREIGN KEY (context_id) REFERENCES CONTEXTS(id)  ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

create table FLIGHT_PLANS (
     id INT NOT NULL AUTO_INCREMENT,
     creation DATETIME not null,
     user_id INT not null,
     PRIMARY KEY (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

ALTER TABLE FLIGHT_PLANS ADD UNIQUE (user_id);

create table TASKS (
     id INT NOT NULL AUTO_INCREMENT,
     summary TEXT NOT NULL,
     details TEXT,
     creation DATETIME not null,  
     deadline DATETIME,  
     redline DATETIME,  
     is_cancelled SMALLINT not null,     
     is_finished SMALLINT not null,
     is_awaited SMALLINT not null,
     is_paused SMALLINT not null,
     is_delegated SMALLINT not null,
     runtime_note TEXT,
     list_id INT not null,
     effort INT NOT NULL,
     PRIMARY KEY (id),
     FOREIGN KEY (list_id) REFERENCES LISTS(id) ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

create table HINTS 
(
    id             INT NOT NULL AUTO_INCREMENT,
    task_id        INT NOT NULL,
    last_update    DATETIME not null,  
    hint_condition TEXT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (task_id) REFERENCES TASKS(id) ON DELETE CASCADE 
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE INDEX idx_hints_tasks
    using BTREE
    ON HINTS (task_id);

create table TASKS_TO_FLIGHT_PLANS 
(
    id      INT NOT NULL AUTO_INCREMENT,
    task_id INT NOT NULL,
    plan_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (task_id) REFERENCES TASKS(id) ON DELETE CASCADE,
    FOREIGN KEY (plan_id) REFERENCES FLIGHT_PLANS(id) ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

ALTER TABLE TASKS_TO_FLIGHT_PLANS ADD UNIQUE (task_id, plan_id);

create table REFERENCED_OBJECTS
(
    id INT NOT NULL AUTO_INCREMENT,
    name        TEXT NOT NULL,
    creation DATETIME NOT NULL,
    is_archived SMALLINT NOT NULL,
    context_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (context_id) REFERENCES CONTEXTS(id)  ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=utf8;


create table NOTES 
(
    id INT NOT NULL AUTO_INCREMENT,
    note    TEXT NOT NULL,
    description TEXT NOT NULL,
    type    TEXT NOT NULL,
    creation DATETIME NOT NULL,
    PRIMARY KEY (id)
)  ENGINE=INNODB DEFAULT CHARSET=utf8;


create table NOTES_TO_OBJECTS
(
    id INT NOT NULL AUTO_INCREMENT,
    note_id INT NOT NULL,
    object_id INT NOT NULL,
    PRIMARY KEY (id),    
    FOREIGN KEY (object_id) REFERENCES REFERENCED_OBJECTS(id)  ON DELETE CASCADE,
    FOREIGN KEY (note_id) REFERENCES NOTES(id)  ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

ALTER TABLE NOTES_TO_OBJECTS ADD UNIQUE (note_id, object_id);

create table FILES
(
    id INT NOT NULL AUTO_INCREMENT,
    name        TEXT NOT NULL,
    description TEXT NOT NULL,
    mime_type   TEXT NOT NULL,
    creation DATETIME NOT NULL,
    PRIMARY KEY (id)
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

create table FILE_BODIES
(
    id          INT NOT NULL AUTO_INCREMENT,    
    file_id     INT NOT NULL,    
    data        LONGBLOB NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (file_id) REFERENCES FILES(id)  ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=utf8;


create table FILES_TO_OBJECTS
(
    id INT NOT NULL AUTO_INCREMENT,
    file_id INT NOT NULL,
    object_id INT NOT NULL,
    PRIMARY KEY (id),    
    FOREIGN KEY (object_id) REFERENCES REFERENCED_OBJECTS(id)  ON DELETE CASCADE,
    FOREIGN KEY (file_id) REFERENCES FILES(id)  ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

ALTER TABLE FILES_TO_OBJECTS ADD UNIQUE (file_id, object_id);

---- CASH FLOW

create table VALUABLE_OBJECTS
(
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT not null,
    is_used_in_balance  SMALLINT NOT NULL, 
    is_debt  SMALLINT DEFAULT 0 NOT NULL, 
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES USERS(id)  ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

create table ASSETS
(
    id INT NOT NULL AUTO_INCREMENT,
    name        TEXT NOT NULL,
    is_archived SMALLINT NOT NULL,    
    valuable_id INT NOT NULL,
    asset_class TEXT,
    PRIMARY KEY (id),    
    FOREIGN KEY (valuable_id) REFERENCES VALUABLE_OBJECTS(id)  ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

create table TARGETS
(
    id INT NOT NULL AUTO_INCREMENT,
    name        TEXT NOT NULL,
    is_archived SMALLINT NOT NULL,        
    valuable_id INT NOT NULL,
    PRIMARY KEY (id),    
    FOREIGN KEY (valuable_id) REFERENCES VALUABLE_OBJECTS(id)  ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

create table COSTS
(
    id INT NOT NULL AUTO_INCREMENT,
    valuable_id INT NOT NULL,
    actuation_date DATETIME NOT NULL,
    buy  DECIMAL(15,5),
    sell DECIMAL(15,5),
    exchange_buy  DECIMAL(15,5),
    exchange_sell DECIMAL(15,5),
    PRIMARY KEY (id),
    FOREIGN KEY (valuable_id) REFERENCES VALUABLE_OBJECTS(id)  ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

create table OPERATIONS
(
    id INT NOT NULL AUTO_INCREMENT,
    type        TEXT NOT NULL,
    comment     TEXT NOT NULL,
    operation_date DATETIME NOT NULL,
    amount  DECIMAL(15,5) NOT NULL,
    cost_id INT NOT NULL,
    PRIMARY KEY (id),    
    FOREIGN KEY (cost_id) REFERENCES COSTS(id)  ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

create table HISTORICAL_VALUES
(
    id INT NOT NULL AUTO_INCREMENT,
    value_tag   TEXT,
    fix_date DATETIME NOT NULL,
    val  DECIMAL(30,10) NOT NULL,
    object_id INT,
    user_id INT not null,
    PRIMARY KEY (id),    
    FOREIGN KEY (user_id) REFERENCES USERS(id)  ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

-- PEOPLE 

create table PERSONS
(
    id INT NOT NULL AUTO_INCREMENT,
    name   TEXT NOT NULL,
    lname   TEXT,
    pname   TEXT,
    birth_date DATETIME,
    PRIMARY KEY (id)
)  ENGINE=INNODB DEFAULT CHARSET=utf8;



create table PERSON_GROUPS
(
    id INT NOT NULL AUTO_INCREMENT,
    owner INT NOT NULL,
    name   TEXT NOT NULL,
    is_archived SMALLINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (owner) REFERENCES USERS(id)  ON DELETE CASCADE    
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

create table PERSONS_TO_GROUPS
(
    id INT NOT NULL AUTO_INCREMENT,
    person_id INT NOT NULL,
    group_id INT NOT NULL,
    PRIMARY KEY (id),    
    FOREIGN KEY (person_id) REFERENCES PERSONS(id)  ON DELETE CASCADE,
    FOREIGN KEY (group_id) REFERENCES PERSON_GROUPS(id)  ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

ALTER TABLE PERSONS_TO_GROUPS ADD UNIQUE (person_id, group_id);

create table ATTRIBUTE_TYPES
(
    id          INT NOT NULL AUTO_INCREMENT,
    name        TEXT NOT NULL,
    token       TEXT NOT NULL,
    view_type   TEXT NOT NULL,
    is_email    SMALLINT NOT NULL,
    PRIMARY KEY (id)
)  ENGINE=INNODB DEFAULT CHARSET=utf8;


create table ATTRIBUTES
(
    id INT NOT NULL AUTO_INCREMENT,
    person_id INT NOT NULL,
    type_id INT NOT NULL,
    val   TEXT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (person_id) REFERENCES PERSONS(id)  ON DELETE CASCADE,
    FOREIGN KEY (type_id) REFERENCES ATTRIBUTE_TYPES(id)  ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

insert into ATTRIBUTE_TYPES (id, name, view_type, token, is_email) VALUES (null, 'Address', 'text', 'address', 0);
insert into ATTRIBUTE_TYPES (id, name, view_type, token, is_email) VALUES (null, 'AOL IM',  'text', 'aim', 0);
insert into ATTRIBUTE_TYPES (id, name, view_type, token, is_email) VALUES (null, 'Blog', 'href', 'blog', 0);
insert into ATTRIBUTE_TYPES (id, name, view_type, token, is_email) VALUES (null, 'E-Mail',  'mailto', 'email', 1);
insert into ATTRIBUTE_TYPES (id, name, view_type, token, is_email) VALUES (null, 'ICQ',  'text', 'icq', 0);
insert into ATTRIBUTE_TYPES (id, name, view_type, token, is_email) VALUES (null, 'Jabber',  'text', 'jabber', 0);
insert into ATTRIBUTE_TYPES (id, name, view_type, token, is_email) VALUES (null, 'Mobile',  'text', 'mobile', 0);
insert into ATTRIBUTE_TYPES (id, name, view_type, token, is_email) VALUES (null, 'Mobile (Work)',  'text', 'mobile', 0);
insert into ATTRIBUTE_TYPES (id, name, view_type, token, is_email) VALUES (null, 'Moi Krug',  'href', 'moi_krug', 0);
insert into ATTRIBUTE_TYPES (id, name, view_type, token, is_email) VALUES (null, 'MSN IM',  'text', 'msn', 0);
insert into ATTRIBUTE_TYPES (id, name, view_type, token, is_email) VALUES (null, 'Orkut',  'href', 'orkut', 0);
insert into ATTRIBUTE_TYPES (id, name, view_type, token, is_email) VALUES (null, 'Phone',  'text', 'phone', 0);
insert into ATTRIBUTE_TYPES (id, name, view_type, token, is_email) VALUES (null, 'Phone (Work)',  'text', 'phone', 0);
insert into ATTRIBUTE_TYPES (id, name, view_type, token, is_email) VALUES (null, 'Position',  'text', 'position', 0);
insert into ATTRIBUTE_TYPES (id, name, view_type, token, is_email) VALUES (null, 'RSS Feed',  'href', 'rss', 0);
insert into ATTRIBUTE_TYPES (id, name, view_type, token, is_email) VALUES (null, 'Skype',  'text', 'skype', 0);
insert into ATTRIBUTE_TYPES (id, name, view_type, token, is_email) VALUES (null, 'Textual note',  'text', 'info', 0);
insert into ATTRIBUTE_TYPES (id, name, view_type, token, is_email) VALUES (null, 'VKontakte',  'href', 'vkontakte', 0);
insert into ATTRIBUTE_TYPES (id, name, view_type, token, is_email) VALUES (null, 'Home Page',  'href', 'web_page', 0);
insert into ATTRIBUTE_TYPES (id, name, view_type, token, is_email) VALUES (null, 'Yahoo! IM',  'text', 'yahoo', 0);
insert into ATTRIBUTE_TYPES (id, name, view_type, token, is_email) VALUES (null, 'Professionali',  'href', 'professionals', 0);
insert into ATTRIBUTE_TYPES (id, name, view_type, token, is_email) VALUES (null, 'Facebook',  'href', 'facebook', 0);
insert into ATTRIBUTE_TYPES (id, name, view_type, token, is_email) VALUES (null, 'LinkedIn',  'href', 'linkedin', 0);
insert into ATTRIBUTE_TYPES (id, name, view_type, token, is_email) VALUES (null, 'Twitter',  'href', 'twitter', 0);

create table MAIL_LISTS
(
    id INT NOT NULL AUTO_INCREMENT,
    owner INT NOT NULL,
    is_archived SMALLINT NOT NULL,
    name   TEXT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (owner) REFERENCES USERS(id)  ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

create table PERSONS_TO_MAIL_LISTS
(
    id INT NOT NULL AUTO_INCREMENT,
    person_id INT NOT NULL,
    mail_list_id INT NOT NULL,
    attribute_value TEXT,
    attribute_id INT,
    PRIMARY KEY (id),    
    FOREIGN KEY (person_id) REFERENCES PERSONS(id)  ON DELETE CASCADE,
    FOREIGN KEY (mail_list_id) REFERENCES MAIL_LISTS(id)  ON DELETE CASCADE,
    FOREIGN KEY (attribute_id) REFERENCES ATTRIBUTES(id) ON DELETE SET NULL ON UPDATE RESTRICT
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

------- INBOX ---------

create table INBOX
(
    id      INT NOT NULL AUTO_INCREMENT,
    owner   INT NOT NULL,
    content TEXT NOT NULL,
    creation DATETIME not null,  
    PRIMARY KEY (id),
    FOREIGN KEY (owner) REFERENCES USERS(id)  ON DELETE CASCADE    
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

create table AGENTS
(
    id      INT NOT NULL AUTO_INCREMENT,
    owner   INT NOT NULL,
    jid TEXT NOT NULL,
    host_name TEXT NOT NULL,
    password TEXT NOT NULL,
    port   INT NOT NULL,
    is_secure SMALLINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (owner) REFERENCES USERS(id)  ON DELETE CASCADE    
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

insert into AGENTS (owner, jid, host_name, password, port, is_secure) VALUES (174947681, 'zeextor@gmail.com', 'talk.google.com', '****', 5223, true);

CREATE FUNCTION LS_DAYS_UNTIL (event DATETIME, since DATETIME)
RETURNS INT DETERMINISTIC
BEGIN
    DECLARE nextDateForEvent DATETIME;

    IF DATE(event) < DATE(since) THEN
        SET nextDateForEvent = DATE(event) + INTERVAL ((YEAR(since)-YEAR(event))) YEAR;
        IF nextDateForEvent < since THEN
            SET nextDateForEvent = nextDateForEvent + INTERVAL 1 YEAR;
        END IF;
    ELSE
        SET nextDateForEvent = DATE(event);
    END IF;

    return DATEDIFF(nextDateForEvent, DATE(since));
END
                      