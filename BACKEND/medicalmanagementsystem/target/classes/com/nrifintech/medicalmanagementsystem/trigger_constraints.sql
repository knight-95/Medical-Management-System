CREATE OR REPLACE TRIGGER patient_insert_trigger
BEFORE INSERT ON patient
FOR EACH ROW
BEGIN
    IF :NEW.birth_year >= EXTRACT(YEAR FROM SYSDATE) THEN
        RAISE_APPLICATION_ERROR(-20001, 'The attribute must be less than the current date.');
    END IF;
END;
/

CREATE OR REPLACE TRIGGER doctor_insert_trigger
BEFORE INSERT ON doctor
FOR EACH ROW
BEGIN
    IF :NEW.experience_start >= EXTRACT(YEAR FROM SYSDATE) THEN
        RAISE_APPLICATION_ERROR(-20001, 'The attribute must be less than the current date.');
    END IF;
END;
/



ALTER TABLE PATIENT 
ADD CONSTRAINT PATIENT_EMAIL_CHECK
CHECK (REGEXP_LIKE(email, '[a-zA-Z0-9._%-]+@[a-zA-Z0-9._%-]+\.[a-zA-Z]{2,4}'));

ALTER TABLE DOCTOR 
ADD CONSTRAINT DOCTOR_EMAIL_CHECK
CHECK (REGEXP_LIKE(email, '[a-zA-Z0-9._%-]+@[a-zA-Z0-9._%-]+\.[a-zA-Z]{2,4}'));

ALTER TABLE PATIENT 
ADD CONSTRAINT PATIENT_CONTACT_CHECK
CHECK (REGEXP_LIKE(contact_number, '^(\\+\\d{1,3})?\\s*(\\d{1,4})?\\s*(\\d{1,4})$'));


CREATE INDEX doctor_name_index
ON doctor(name);
CREATE INDEX specialization_name_index
ON specialization(name);
CREATE INDEX doctor_fees_index
ON doctor(fees);
CREATE INDEX doctor_rating_index
ON doctor(rating);
CREATE BITMAP INDEX doctor_gender_index
ON doctor(gender);
CREATE BITMAP INDEX doctor_status_index
ON doctor(status);

CREATE INDEX appointment_date_index
ON appointment(app_date);


CREATE BITMAP INDEX appointment_status_index
ON appointment(app_status);

CREATE BITMAP INDEX appointment_slot_index
ON appointment(slot);


CREATE BITMAP INDEX user_username_password_index
ON "user"(user_name,password);


INSERT INTO "MMS_DB"."ROLE" (ID, ROLE_NAME) VALUES ('1', 'ROLE_ADMIN')
INSERT INTO "MMS_DB"."ROLE" (ID, ROLE_NAME) VALUES ('2', 'ROLE_USER')

-- write return types for EACH methods
-- maintain scope specifier for each methods
-- view child annotation
-- view child variables
-- inout variables
-- output variables
-- public variables without scope specifier
--declare argument type
-- private variables
-- constructur
-- ngoninit
--commit message should be short and concise and meaning ful
--ss during pull request

-- Return type for each method must be specified
-- Scope specifier for each method
-- Order : ViewChild, Input, Output, public, private, constructor, ngOnInit
-- Dependency Injection through constructor
-- Max length of characters in one line : 130
-- for methods not used from anywhere else make it private
-- 1 line gap between all functions
-- declare type for function parameters


-- add JIRA story before commit message
-- add screenshot of feature while raising PR