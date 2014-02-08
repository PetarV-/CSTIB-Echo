SET DB_CLOSE_DELAY -1;         
;              
CREATE USER IF NOT EXISTS "" SALT '' HASH '' ADMIN;            
-- 1 +/- SELECT COUNT(*) FROM PUBLIC.CONFERENCE;
INSERT INTO PUBLIC.CONFERENCE(ID, NAME) VALUES(1, 'Happy Conference');         
-- 2 +/- SELECT COUNT(*) FROM PUBLIC.CONVERSATION;
INSERT INTO PUBLIC.CONVERSATION(ID, NAME, CONFERENCE_ID) VALUES(1, 'Happy Conversation', 1);   
INSERT INTO PUBLIC.CONVERSATION(ID, NAME, CONFERENCE_ID) VALUES(2, 'Sad Conversation', 1);     
-- 5 +/- SELECT COUNT(*) FROM PUBLIC.MESSAGE;
INSERT INTO PUBLIC.MESSAGE(ID, CONTENTS, TIMESTAMP, CONVERSATION_ID, SENDER_ID) VALUES(1, 'Hello Happy Conversation. This is a happy message.', 1391541416966, 1, NULL);       
INSERT INTO PUBLIC.MESSAGE(ID, CONTENTS, TIMESTAMP, CONVERSATION_ID, SENDER_ID) VALUES(2, 'Well GoodBye', 1391541424482, 1, NULL);             
INSERT INTO PUBLIC.MESSAGE(ID, CONTENTS, TIMESTAMP, CONVERSATION_ID, SENDER_ID) VALUES(3, ':(', 1391541448405, 2, NULL);       
INSERT INTO PUBLIC.MESSAGE(ID, CONTENTS, TIMESTAMP, CONVERSATION_ID, SENDER_ID) VALUES(4, 'why ?', 1391541450301, 2, NULL);    
INSERT INTO PUBLIC.MESSAGE(ID, CONTENTS, TIMESTAMP, CONVERSATION_ID, SENDER_ID) VALUES(5, 'i hate marshmallows', 1391541459019, 2, NULL);      
