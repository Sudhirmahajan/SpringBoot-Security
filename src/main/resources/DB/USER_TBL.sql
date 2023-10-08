--------------------------------------------------------
--  File created - Sunday-October-08-2023   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table USER_TBL
--------------------------------------------------------

  CREATE TABLE "REGISTRATION"."USER_TBL" 
   (	"USERID" NUMBER(19,0) GENERATED ALWAYS AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE , 
	"EMAIL" VARCHAR2(255 CHAR), 
	"FIRSTNAME" VARCHAR2(255 CHAR), 
	"ISACCOUNTNONEXPIRED" NUMBER(1,0), 
	"ISACCOUNTNONLOCKED" NUMBER(1,0), 
	"ISCREDENTIALSNONEXPIRED" NUMBER(1,0), 
	"ISENABLED" NUMBER(1,0), 
	"LASTNAME" VARCHAR2(255 CHAR), 
	"PASSWORD" VARCHAR2(255 CHAR), 
	"USERCODE" VARCHAR2(255 CHAR)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index SYS_C008405
--------------------------------------------------------

  CREATE UNIQUE INDEX "REGISTRATION"."SYS_C008405" ON "REGISTRATION"."USER_TBL" ("USERID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  Constraints for Table USER_TBL
--------------------------------------------------------

  ALTER TABLE "REGISTRATION"."USER_TBL" MODIFY ("USERID" NOT NULL ENABLE);
  ALTER TABLE "REGISTRATION"."USER_TBL" MODIFY ("ISACCOUNTNONEXPIRED" NOT NULL ENABLE);
  ALTER TABLE "REGISTRATION"."USER_TBL" MODIFY ("ISACCOUNTNONLOCKED" NOT NULL ENABLE);
  ALTER TABLE "REGISTRATION"."USER_TBL" MODIFY ("ISCREDENTIALSNONEXPIRED" NOT NULL ENABLE);
  ALTER TABLE "REGISTRATION"."USER_TBL" MODIFY ("ISENABLED" NOT NULL ENABLE);
  ALTER TABLE "REGISTRATION"."USER_TBL" ADD PRIMARY KEY ("USERID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;