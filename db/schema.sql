DROP TABLE IF EXISTS TDT_PROPERTY_IMG;
DROP TABLE IF EXISTS TDT_PROPERTY_INFO;
DROP TABLE IF EXISTS TDT_USER_INFO;
DROP TABLE IF EXISTS TDT_NEWS;
DROP TABLE IF EXISTS TDT_CONTACT;
DROP TABLE IF EXISTS TDT_BOOKING;
DROP TABLE IF EXISTS TDT_STORAGE;
DROP TABLE IF EXISTS TDT_PROPERTY;
DROP TABLE IF EXISTS TDT_PROPERTY_TYPE;
DROP TABLE IF EXISTS TDT_USER;
DROP TABLE IF EXISTS TDT_ROLE;

CREATE TABLE TDT_ROLE
(
    ROLE_ID NUMBER(19,0) GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    ROLE_NAME VARCHAR2(64 CHAR) NOT NULL,
    IS_DELETED CHAR(1 CHAR) DEFAULT '0' NOT NULL,
    CREATE_USER_ID VARCHAR2(64 CHAR) NOT NULL,
    CREATE_DATETIME DATE NOT NULL,
    LASTUP_USER_ID VARCHAR2(64 CHAR),
    LASTUP_DATETIME DATE,
	PRIMARY KEY(ROLE_ID),
	CONSTRAINT UQ_TDT_ROLE_01 UNIQUE(ROLE_NAME)
);
INSERT INTO TDT_ROLE (ROLE_ID, ROLE_NAME, IS_DELETED, CREATE_USER_ID, CREATE_DATETIME, LASTUP_USER_ID, LASTUP_DATETIME) VALUES
(1, 'ADMIN', '0', '1', '2022-01-01', '', ''),
(2, 'USER', '0', '1', '2022-01-01', '', '');


CREATE TABLE TDT_USER
(
    USER_ID NUMBER(19,0) GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    EMAIL VARCHAR2(64 CHAR) NOT NULL,
    PASSWORD VARCHAR2(200 CHAR) NOT NULL,
    ROLE_ID  NUMBER(19,0) NOT NULL,
    APPROVE_STATUS VARCHAR2(10 CHAR) NOT NULL,
    IS_DELETED CHAR(1 CHAR) DEFAULT '0' NOT NULL,
    CREATE_USER_ID VARCHAR2(64 CHAR) NOT NULL,
    CREATE_DATETIME DATE NOT NULL,
    LASTUP_USER_ID VARCHAR2(64 CHAR),
    LASTUP_DATETIME DATE,
	PRIMARY KEY(USER_ID),
	CONSTRAINT UQ_TDT_USER_01 UNIQUE(EMAIL)
);
INSERT INTO TDT_USER (USER_ID, EMAIL, PASSWORD, ROLE_ID, APPROVE_STATUS, IS_DELETED, CREATE_USER_ID, CREATE_DATETIME, LASTUP_USER_ID, LASTUP_DATETIME) VALUES
(1, 'admin@gmail.com', '123456', 1, 'APPROVE', '0', '1', '2022-01-01', '', '');

CREATE TABLE TDT_USER_INFO
(
    USER_INFO_ID NUMBER(19,0) GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    USER_ID NUMBER(19,0) NOT NULL,
    STD_ID VARCHAR2(64 CHAR),
    NAME VARCHAR2(64 CHAR) NOT NULL,
    ADDRESS VARCHAR2(200 CHAR),
    ID_CARD VARCHAR2(64 CHAR),
    F_CARD VARCHAR2(200 CHAR),
    B_CARD VARCHAR2(200 CHAR),
    PORTRAIT VARCHAR2(200 CHAR),
    PHONE VARCHAR2(64 CHAR) NOT NULL,
    IS_DELETED CHAR(1 CHAR) DEFAULT '0' NOT NULL,
    LASTUP_USER_ID VARCHAR2(64 CHAR),
    LASTUP_DATETIME DATE,
    PRIMARY KEY(USER_INFO_ID),
    CONSTRAINT UQ_USER_INFO_01 UNIQUE(STD_ID,ID_CARD,PHONE)
);
INSERT INTO TDT_USER_INFO (USER_INFO_ID, USER_ID, STD_ID, NAME, ADDRESS, ID_CARD, F_CARD, B_CARD, PORTRAIT, PHONE, IS_DELETED, LASTUP_USER_ID, LASTUP_DATETIME) VALUES
(1, 1, '', 'ADMIN', '', '', '', '', '', '0900000000', '0', '', '');


CREATE TABLE TDT_PROPERTY_TYPE
(
    TYPE_ID NUMBER(19,0) GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    NAME VARCHAR2(64 CHAR) NOT NULL,
    CREATE_USER_ID VARCHAR2(64 CHAR) NOT NULL,
    CREATE_DATETIME DATE NOT NULL,
    LASTUP_USER_ID VARCHAR2(64 CHAR),
    LASTUP_DATETIME DATE,
    IS_DELETED CHAR(1 CHAR) DEFAULT '0' NOT NULL,
	PRIMARY KEY(TYPE_ID),
	CONSTRAINT UQ_TDT_PROPERTY_TYPE_01 UNIQUE(NAME)
);

CREATE TABLE TDT_PROPERTY
(
    PROPERTY_ID NUMBER(19,0) GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    TYPE_ID NUMBER(19,0) NOT NULL,
    IS_DELETED CHAR(1 CHAR) DEFAULT '0' NOT NULL,
    CREATE_USER_ID VARCHAR2(64 CHAR) NOT NULL,
    CREATE_DATETIME DATE NOT NULL,
    LASTUP_USER_ID VARCHAR2(64 CHAR),
    LASTUP_DATETIME DATE,
	PRIMARY KEY(PROPERTY_ID)
);

CREATE TABLE TDT_PROPERTY_INFO
(
    PROPERTY_INFO_ID NUMBER(19,0) GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    PROPERTY_ID NUMBER(19,0) NOT NULL,
    TITLE VARCHAR2(4000 CHAR) NOT NULL,
    ADDRESS VARCHAR2(4000 CHAR) NOT NULL,
    AMOUNT NUMBER(38,0) NOT NULL,
    AREA NUMBER(38,0) NOT NULL,
    DESCRIPTION VARCHAR2(4000 CHAR) NOT NULL,
    APPROVE_STATUS VARCHAR2(10 CHAR) NOT NULL,
    IS_DELETED CHAR(1 CHAR) DEFAULT '0' NOT NULL,
    PRIMARY KEY(PROPERTY_INFO_ID)
);

CREATE TABLE TDT_PROPERTY_IMG
(
    PROPERTY_IMG_ID NUMBER(19,0) GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    PROPERTY_ID NUMBER(19,0) NOT NULL,
    PROPERTY_IMG VARCHAR2(200 CHAR)  NOT NULL,
    IS_DELETED CHAR(1 CHAR) DEFAULT '0' NOT NULL,
    CREATE_USER_ID VARCHAR2(64 CHAR) NOT NULL,
    CREATE_DATETIME DATE NOT NULL,
    LASTUP_USER_ID VARCHAR2(64 CHAR),
    LASTUP_DATETIME DATE,
	PRIMARY KEY(PROPERTY_IMG_ID)
);

CREATE TABLE TDT_STORAGE
(
    STORAGE_ID NUMBER(19,0) GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    PROPERTY_ID NUMBER(19,0) NOT NULL,
    USER_ID NUMBER(19,0) NOT NULL,
    CREATE_USER_ID VARCHAR2(64 CHAR) NOT NULL,
    CREATE_DATETIME DATE NOT NULL,
    IS_DELETED CHAR(1 CHAR) DEFAULT '0' NOT NULL,
    PRIMARY KEY(STORAGE_ID)
);

CREATE TABLE TDT_BOOKING
(
    BOOKING_ID NUMBER(19,0) GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    PROPERTY_ID NUMBER(19,0) NOT NULL,
    USER_ID NUMBER(19,0) NOT NULL,
    BOOKING_DATE DATE NOT NULL,
    NOTE VARCHAR2(4000 CHAR),
    APPROVE_STATUS VARCHAR2(10 CHAR) NOT NULL,
    CREATE_USER_ID VARCHAR2(64 CHAR) NOT NULL,
    CREATE_DATETIME DATE NOT NULL,
    IS_DELETED CHAR(1 CHAR) DEFAULT '0' NOT NULL,
    PRIMARY KEY(BOOKING_ID)
);

CREATE TABLE TDT_CONTACT
(
    CONTACT_ID NUMBER(19,0) GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    CONTENT VARCHAR2(4000 CHAR) NOT NULL,
    EMAIL VARCHAR2(64 CHAR) NOT NULL,
    PHONE NUMBER(19,0),
    APPROVE_STATUS VARCHAR2(10 CHAR) NOT NULL,
    HANDLER_ID NUMBER(19,0),
    CREATE_USER_ID VARCHAR2(64 CHAR) NOT NULL,
    CREATE_DATETIME DATE NOT NULL,
    IS_DELETED CHAR(1 CHAR) DEFAULT '0' NOT NULL,
    PRIMARY KEY(CONTACT_ID)
);

CREATE TABLE TDT_NEWS
(
    NEWS_ID NUMBER(19,0) GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    TITLE VARCHAR2(4000 CHAR) NOT NULL,
    CONTENT VARCHAR2(4000 CHAR) NOT NULL,
    COVER VARCHAR2(64 CHAR),
    USER_ID NUMBER(19,0) NOT NULL,
    CREATE_USER_ID VARCHAR2(64 CHAR) NOT NULL,
    CREATE_DATETIME DATE NOT NULL,
    LASTUP_USER_ID VARCHAR2(64 CHAR) NOT NULL,
    LASTUP_DATETIME DATE NOT NULL,
    IS_DELETED CHAR(1 CHAR) DEFAULT '0' NOT NULL,
	PRIMARY KEY(NEWS_ID)
);

ALTER TABLE TDT_USER
    ADD CONSTRAINT FK_TDT_USER_TDT_ROLE FOREIGN KEY(ROLE_ID)
        REFERENCES TDT_ROLE(ROLE_ID)
;

ALTER TABLE TDT_USER_INFO
    ADD CONSTRAINT FK_TDT_USER_INFO_TDT_USER FOREIGN KEY(USER_ID)
        REFERENCES TDT_USER(USER_ID)
;

ALTER TABLE TDT_PROPERTY
    ADD CONSTRAINT FK_TDT_PROPERTY_TDT_PROPERTY_TYPE FOREIGN KEY(TYPE_ID)
        REFERENCES TDT_PROPERTY_TYPE(TYPE_ID)
;

ALTER TABLE TDT_PROPERTY_INFO
    ADD CONSTRAINT FK_TDT_PROPERTY_INFO_TDT_PROPERTY FOREIGN KEY(PROPERTY_ID)
        REFERENCES TDT_PROPERTY(PROPERTY_ID)
;

ALTER TABLE TDT_STORAGE
    ADD CONSTRAINT FK_TDT_STORAGE_TDT_PROPERTY FOREIGN KEY(PROPERTY_ID)
        REFERENCES TDT_PROPERTY(PROPERTY_ID)
;

ALTER TABLE TDT_STORAGE
    ADD CONSTRAINT FK_TDT_STORAGE_TDT_USER FOREIGN KEY(USER_ID)
        REFERENCES TDT_USER(USER_ID)
;

ALTER TABLE TDT_BOOKING
    ADD CONSTRAINT FK_TDT_BOOKING_TDT_PROPERTY FOREIGN KEY(PROPERTY_ID)
        REFERENCES TDT_PROPERTY(PROPERTY_ID)
;

ALTER TABLE TDT_BOOKING
    ADD CONSTRAINT FK_TDT_BOOKING_TDT_USER FOREIGN KEY(USER_ID)
        REFERENCES TDT_USER(USER_ID)
;

ALTER TABLE TDT_CONTACT
    ADD CONSTRAINT FK_TDT_CONTACT_TDT_USER FOREIGN KEY(HANDLER_ID)
        REFERENCES TDT_USER(USER_ID)
;

ALTER TABLE TDT_NEWS
    ADD CONSTRAINT FK_TDT_NEWS_TDT_USER FOREIGN KEY(USER_ID)
        REFERENCES TDT_USER(USER_ID)
;

ALTER TABLE TDT_PROPERTY_IMG
    ADD CONSTRAINT FK_TDT_PROPERTY_IMG_TDT_PROPERTY FOREIGN KEY(PROPERTY_ID)
        REFERENCES TDT_PROPERTY(PROPERTY_ID)
;