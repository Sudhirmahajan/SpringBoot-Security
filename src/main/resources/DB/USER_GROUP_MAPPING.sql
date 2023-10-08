--------------------------------------------------------
--  File created - Sunday-October-08-2023   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table USER_GROUP_MAPPING
--------------------------------------------------------

  CREATE TABLE "REGISTRATION"."USER_GROUP_MAPPING" 
   (	"USER_ID" NUMBER(19,0), 
	"GROUP_ID" NUMBER(19,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  Constraints for Table USER_GROUP_MAPPING
--------------------------------------------------------

  ALTER TABLE "REGISTRATION"."USER_GROUP_MAPPING" MODIFY ("USER_ID" NOT NULL DISABLE);
  ALTER TABLE "REGISTRATION"."USER_GROUP_MAPPING" MODIFY ("GROUP_ID" NOT NULL DISABLE);
--------------------------------------------------------
--  Ref Constraints for Table USER_GROUP_MAPPING
--------------------------------------------------------

  ALTER TABLE "REGISTRATION"."USER_GROUP_MAPPING" ADD CONSTRAINT "FK6R223TJ4S7I1HGNMED9A8WIUP" FOREIGN KEY ("GROUP_ID")
	  REFERENCES "REGISTRATION"."GROUP_TBL" ("ID") DISABLE;
  ALTER TABLE "REGISTRATION"."USER_GROUP_MAPPING" ADD CONSTRAINT "FKF14PLRWAMQISUFAP60HPV3V9K" FOREIGN KEY ("USER_ID")
	  REFERENCES "REGISTRATION"."USER_TBL" ("USERID") DISABLE;
