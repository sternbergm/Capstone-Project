drop database if exists htd_db;
create database htd_db;
use htd_db;

create table contractors (
	contractor_id int primary key auto_increment,
    first_name varchar(50),
	last_name varchar(50),
	date_of_birth Date,
	address varchar(50),
	email varchar(50),
	salary Decimal(9,2),
	isHired bit,
    constraint uq_first_last_bd 
		unique (first_name, last_name, date_of_birth)
);

create table clients (
	client_id int primary key auto_increment,
	`name` varchar(50),
	address varchar(50),
	company_size int,
	email varchar(50),
    constraint uq_name_address
		unique (`name`, address)
);

create table instructors (
	instructor_id int primary key auto_increment,
	first_name varchar(50),
	last_name varchar(50),
	years_of_experience int,
	expertise varchar(50),
	salary Decimal (10,2),
	constraint uq_first_last unique(first_name, last_name)
);

create table cohorts (
	cohort_id int primary key auto_increment,
	start_date Date,
	end_date Date,
	client_id int,
	instructor_id int,
    constraint fk_client_cohort
		foreign key (client_id)
		references clients(client_id),
	constraint fk_instructor_cohort
		foreign key (instructor_id)
		references instructors(instructor_id),
	constraint uq_cohort
		unique (start_date, client_id, instructor_id) -- assumption, all courses for HTD business models are intense enough that an instructor can only teach a single course at a time.
);

create table modules (
	module_id int primary key auto_increment,
	topic varchar(50),
	start_date Date,
	end_date Date,
	exercise_amount int,
	lesson_amount int,
	constraint uq_module unique(topic, start_date, end_date)
);

create table contractor_cohort_module (
	contractor_id int,
	cohort_id int,
	module_id int,
	grade Decimal(5,2),
	constraint fk_contractor_bridge
		foreign key (contractor_id)
		references contractors(contractor_id),
	constraint fk_cohort_bridge
		foreign key (cohort_id)
		references cohorts(cohort_id),
	constraint fk_module_bridge
		foreign key (module_id)
		references modules(module_id)
);

create table  globalExceptions (
	`timestamp` datetime primary key,
    stackTrace text
);