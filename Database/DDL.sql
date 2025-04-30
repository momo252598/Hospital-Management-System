create table department
(
	dep_name varchar(20) not null
		primary key,
	floor varchar(4) not null,
	no_of_beds integer not null
);

alter table department owner to hmd;

create table employee
(
	emp_id varchar(15) not null
		primary key,
	fname varchar(15) not null,
	mname varchar(15) not null,
	lname varchar(15) not null,
	sex char not null,
	bdate date not null,
	phone_number varchar(10),
	salary numeric(10,2) not null,
	shift varchar(4) not null,
	address varchar(15) not null,
	pass_word varchar(15)
);

alter table employee owner to hmd;

create table lab_technician
(
	emp_id varchar(15) not null
		primary key
		constraint lab_fky
			references employee
				on update cascade on delete set null,
	dep_name varchar(20)
		constraint dep_fky
			references department
				on update cascade on delete set null,
	pass_word varchar(15),
	sample_desc varchar(150)
);

alter table lab_technician owner to hmd;

create table doctor
(
	emp_id varchar(15) not null
		primary key
		constraint doc_fky
			references employee
				on update cascade on delete set null,
	dep_name varchar(20)
		constraint dep_fky
			references department
				on update cascade on delete set null,
	specialty varchar(15) not null,
	tech_id varchar(15)
		constraint tech_fky
			references lab_technician
				on update cascade on delete set null,
	result_desc text,
	status boolean,
	pass_word varchar(15)
);

alter table doctor owner to hmd;

create table nurse
(
	emp_id varchar(15) not null
		primary key
		constraint nurse_casc
			references employee
				on update cascade on delete set null,
	dep_name varchar(20)
		constraint dep_fky
			references department
				on update cascade on delete set null,
	pass_word varchar(15)
);

alter table nurse owner to hmd;

create table patient
(
	id_no varchar(15) not null
		primary key,
	fname varchar(15) not null,
	mname varchar(15) not null,
	lname varchar(15) not null,
	sex char not null,
	blood_type char(3),
	bdate date not null,
	phone_number varchar(10),
	dep_name varchar(20)
		references department,
	emp_id varchar(15)
		constraint patient_casc
			references nurse
				on update cascade on delete set null,
	address varchar(15) not null,
	admission_date date,
	discharge_note text,
	nurse_note text
);

alter table patient owner to hmd;

create table orders
(
	nurse_id varchar(255)
		constraint nurse_fky
			references nurse
				on update cascade on delete set null,
	doc_id varchar(15)
		constraint doc_fky
			references doctor
				on update cascade on delete set null,
	order_desc text,
	status boolean,
	order_no serial
		primary key,
	"patient_ID" varchar(15)
);

alter table orders owner to hmd;

create table diagnosis
(
	id_no varchar(15)
		references patient
			on update cascade on delete set null,
	doc_id varchar(255)
		references doctor
			on update cascade on delete set null,
	diagnosis_no integer default nextval('proj_1.diagnosis_diagnosis_no_seq'::regclass) not null
		primary key,
	description text,
	status boolean
);

alter table diagnosis owner to hmd;

create table nurse_tech
(
	nurse_id varchar(15)
		references nurse
			on update cascade on delete set null,
	tech_id varchar(15)
		references lab_technician
			on update cascade on delete set null,
	sample_no serial
		primary key,
	pid varchar(15),
	doc_id varchar(15),
	sample_desc varchar(255),
	sample_status boolean default false not null
);

alter table nurse_tech owner to hmd;

