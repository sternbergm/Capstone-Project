drop database if exists htd_db_test;
create database htd_db_test;
use htd_db_test;

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


delimiter //
create procedure set_known_good_state()
begin
	
    delete from globalExceptions;
    delete from contractor_cohort_module;
    delete from cohorts;
    alter table cohorts auto_increment = 1;
    delete from contractors;
    alter table contractors auto_increment = 1;
    delete from clients;
    alter table clients auto_increment = 1;
    delete from instructors;
    alter table instructors auto_increment = 1;
    delete from modules;
    alter table modules auto_increment = 1;
    
    insert into modules values 
    (1, 'Spring', '2023-04-01', '2023-04-07', 5, 5),
    (2, 'File IO', '2023-04-08', '2023-04-15', 4, 4);
    
    insert into contractors values 
    (1, 'John', 'Doe', '1990-01-01', '123 way st', 'john@doe.com', 30000, True),
    (2, 'Jane', 'Doe', '1990-10-10', '456 main ave', 'Jane@doe.com', 40000, True);
    
    insert into clients values
    (1, 'Main bank', 'One Main st', 1000, 'info@mainbank.com'),
    (2, 'Realtors CO', '200 cherry road', 150, 'realtors@mail.com');
    
    insert into instructors values
    (1, 'James', 'Bond', 30, 'espionage', 30000),
    (2, 'Indiana', 'Jones', 20, 'History', 35000);
    
    insert into cohorts values 
    (1, '2023-01-01', '2023-03-01', 1, 1),
    (2, '2023-06-01', '2023-08-01', 2, 2);
    
    insert into contractor_cohort_module values
    (1, 1, 1, 90),
    (2, 1, 1, 95),
    (1, 1, 2, 85),
    (2, 1, 2, 80);
    
end //
-- 4. Change the statement terminator back to the original.
delimiter ;