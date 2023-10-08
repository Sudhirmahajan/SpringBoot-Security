--------------------------------------------------------
--  File created - Sunday-October-08-2023   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table ROLES_PRIVILEGES
--------------------------------------------------------

  CREATE TABLE "REGISTRATION"."ROLES_PRIVILEGES" 
   (	"ROLE_ID" NUMBER(19,0), 
	"PRIVILEGE_ID" NUMBER(19,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  Constraints for Table ROLES_PRIVILEGES
--------------------------------------------------------

  ALTER TABLE "REGISTRATION"."ROLES_PRIVILEGES" MODIFY ("ROLE_ID" NOT NULL ENABLE);
  ALTER TABLE "REGISTRATION"."ROLES_PRIVILEGES" MODIFY ("PRIVILEGE_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Ref Constraints for Table ROLES_PRIVILEGES
--------------------------------------------------------

  ALTER TABLE "REGISTRATION"."ROLES_PRIVILEGES" ADD CONSTRAINT "FKQGCUD082E3KFAKWH542JCA8G" FOREIGN KEY ("ROLE_ID")
	  REFERENCES "REGISTRATION"."ROLE_TBL" ("ROLEID") ENABLE;
  ALTER TABLE "REGISTRATION"."ROLES_PRIVILEGES" ADD CONSTRAINT "FKJXQI8HVL72JKOG28UNWXH5SP1" FOREIGN KEY ("PRIVILEGE_ID")
	  REFERENCES "REGISTRATION"."PRIVILEGE_TBL" ("PRIVILEGEID") ENABLE;
