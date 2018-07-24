CREATE TABLE ELEUSAGE (
	USAGEID VARCHAR(20) NOT NULL PRIMARY KEY, 
	RESID INTEGER,
	DATE DATE NOT NULL,
	TIME INTEGER NOT NULL,
	TEMPERATURE DECIMAL(4,1) NOT NULL,
	AIRCON NUMERIC(4,1),
	WASHINGMACHINE NUMERIC(4,1),
	FRIDGE NUMERIC(4,1)
);

Alter Table eleusage Add Foreign Key (resid) References resident (resid);
Alter table eleusage add constraint constraint4 check(time>=0 and time<=23);
Alter table eleusage Add Constraint constraint5 check (aircon>=0);
Alter table eleusage Add Constraint constraint6 check (washingmachine>=0);
Alter table eleusage Add Constraint constraint7 check (fridge>=0);