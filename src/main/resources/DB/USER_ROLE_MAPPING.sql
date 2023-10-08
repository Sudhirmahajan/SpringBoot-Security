--------------------------------------------------------
--  File created - Sunday-October-08-2023   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table USER_ROLE_MAPPING
--------------------------------------------------------

  CREATE TABLE "REGISTRATION"."USER_ROLE_MAPPING" 
   (	"USER_ID" NUMBER(19,0), 
	"ROLE_ID" NUMBER(19,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  Constraints for Table USER_ROLE_MAPPING
--------------------------------------------------------

  ALTER TABLE "REGISTRATION"."USER_ROLE_MAPPING" MODIFY ("USER_ID" NOT NULL ENABLE);
  ALTER TABLE "REGISTRATION"."USER_ROLE_MAPPING" MODIFY ("ROLE_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Ref Constraints for Table USER_ROLE_MAPPING
--------------------------------------------------------

  ALTER TABLE "REGISTRATION"."USER_ROLE_MAPPING" ADD CONSTRAINT "FKJFYYQ3G3VTR3J0U67FFPK8RLS" FOREIGN KEY ("ROLE_ID")
	  REFERENCES "REGISTRATION"."ROLE_TBL" ("ROLEID") ENABLE;
  ALTER TABLE "REGISTRATION"."USER_ROLE_MAPPING" ADD CONSTRAINT "FK99H744J3T16OMTL6IAGO3QGES" FOREIGN KEY ("USER_ID")
	  REFERENCES "REGISTRATION"."USER_TBL" ("USERID") ENABLE;
