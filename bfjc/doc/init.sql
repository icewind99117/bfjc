 insert into mysql.user(Host,User,Password) values("localhost","bfjc",password("Bfjc,1"));
  flush PRIVILEGES;
 insert into mysql.user(Host,User,Password) values("%","bfjc",password("Bfjc,1"));
  flush PRIVILEGES;
 create database imfDB;
 grant all privileges on imfDB.* to bfjc@localhost identified by 'Bfjc,1';
 flush PRIVILEGES;
 grant all privileges on imfDB.* to bfjc@"%" identified by 'Bfjc,1';

create table imfDB.user_info (
   id            int    primary key not null auto_increment,
   user_name     varchar(50),
   login_name    varchar(10),   
   password      varchar(100)   
);

insert into imfDB.user_info(user_name,login_name,password) values('管理员','bfjc','a2bd25928ed4c09b100ff1415cebe0d29bb6f1d43a38f77c5fe96f74');

CREATE TABLE imfDB.msg_store (
	id INT PRIMARY KEY NOT NULL auto_increment,
	msg_type VARCHAR (50),
	data_type VARCHAR (50),
	msg_content text,
	sender VARCHAR (50),
	msg_time VARCHAR (20),
	receive_time datetime,
	parse_time datetime,
	status INT,
	seq_num varchar (10),
	flight_identity varchar (16),
	flight_direction varchar (9),
	flight_repeat_count varchar (2),
	scheduled_date varchar (20),
	afds_id INT,
	removed INT DEFAULT 0
);

CREATE TABLE imfDB.api_tag (
	id INT PRIMARY KEY NOT NULL auto_increment,
	name VARCHAR (50),
	code VARCHAR (50),
	params VARCHAR (50),
	description VARCHAR (200),
	removed INT DEFAULT 0
);


insert into imfDB.api_tag(name,code,params,description,removed) values('全局订阅提前量','snapshot','-1D','用于首次或者重新订阅（D：代表天数）：以当前日期为基准，加上该提前量，计算订阅起始时间点。',0);
insert into imfDB.api_tag(name,code,params,description,removed) values('局部订阅提前量','update','-20M','用于断点续传订阅（M：代表分钟）：以最新消息时间戳为基准，加上该提前量，计算订阅起始时间点。',0);
insert into imfDB.api_tag(name,code,params,description,removed) values('最大断线时限','maxOffline','24H','允许断线的最大时间（H：代表小时）：断线时间超过该值，则自动发起全局订阅；否则，自动发起局部订阅。',0);
insert into imfDB.api_tag(name,code,params,description,removed) values('最新消息时间戳','timestamp','','收到的最新一条消息的发送时间：该值为空时，发起的订阅为全局订阅。'0);

drop table imfDB.afds_info;

create table imfDB.afds_info (
	id int primary key not null auto_increment,
	flight_identity varchar (16),
	flight_direction varchar (9),
	flight_repeat_count varchar (2),
	scheduled_date varchar (20),
	iata_carrier_iata_code varchar (3),
	iata_flight_number varchar (4),
	iata_flight_suffix varchar (1),
	icao_carrier_icao_code varchar (4),
	icao_flight_number varchar (4),
	icao_flight_suffix varchar (1),
	aircraft_callsign varchar (20),
	aircraft_owner_iata_code varchar (3),
	aircraft_passenger_capacity varchar (3),
	aircraft_registration varchar (20),
	aircraft_subtype_iata_code varchar (3),
	aircraft_type_icao_code varchar (4),
	aircraft_operator varchar (6),
	aircraft_terminal_code varchar (16),
	airline_terminal_code varchar (16),
	airport_iata_code varchar (3),
	passenger_terminal_code varchar (16),
	runway_id varchar (16),
	secure_stand_is_required varchar (5),
	stand_position varchar (16),
	supplementary_infomation_text varchar (160),
	supplementary_infomation_type varchar (9),
	baggage_makeup_close_date_time varchar (200),
	baggage_makeup_open_date_time varchar (200),
	baggage_makeup_position_id varchar (350),
	baggage_makeup_position_role varchar (200),
	baggage_reclaim_carousel_id varchar (350),
	baggage_reclaim_carousel_role varchar (200),
	baggage_reclaim_close_dt varchar (200),
	baggage_reckaim_open_dt varchar (200),
	exit_door_number varchar (350),
	first_bag_date_time varchar (200),
	last_bag_date_time varchar (200),
	baggage_terminal_code varchar (16),
	bus_is_required varchar (5),
	checkin_close_date_time varchar (200),
	checkin_cluster_id varchar (380),
	checkin_desk_range varchar (700),
	checkin_open_date_time varchar (100),
	checkin_role varchar (200),
	checkin_type_code varchar (50),
	gate_boarding_status varchar (50),
	gate_close_date_time varchar (200),
	gate_number varchar (350),
	gate_open_date_time varchar (200),
	gate_role varchar (200),
	remark_code varchar (160),
	remark_type varchar (1000),
	account_code varchar (6),
	code_share_status varchar (2),
	divert_airport_iata_code varchar (3),
	flight_cancel_code varchar (1),
	flight_classification_code varchar (6),
	flight_nature_code varchar (6),
	flight_operates_overnight varchar (5),
	flight_sector_code varchar (1),
	flight_service_type_iata_code varchar (1),
	flight_status_code varchar (100),
	flight_transit_code varchar (1),
	new_flight_reason varchar (6),
	irregularity_alpha_iata_code varchar (60),
	irregularity_duration varchar (1020),
	irregularity_numeric_iata_code varchar (60),
	irregularity_role varchar (200),
	is_general_aviation_flight varchar (5),
	is_return_flight varchar (5),
	is_transfer_flight varchar (5),
	linked_carrier_iata_code varchar (3),
	linked_flight_number varchar (4),
	linked_flight_suffix varchar (1),
	linked_flight_identity varchar (16),
	linked_flight_repeat_count varchar (2),
	linked_scheduled_date varchar (10),
	master_carrier_iata_code varchar (3),
	master_flight_number varchar (4),
	master_flight_suffix varchar (1),
	master_flight_identity varchar (16),
	master_flight_repeat_count varchar (2),
	handling_agent_iata_code varchar (350),
	handing_agent_service varchar (850),
	operation_type_code varchar (80),
	operation_type_role varchar (200),
	sf_carrier_iata_code varchar (240),
	sf_flight_number varchar (300),
	sf_flight_suffix varchar (20),
	sf_flight_identity varchar (170),
	sf_flight_repeat_count varchar (30),
	transfer_flight_bag_count varchar (40),
	transfer_flight_identity varchar (170),
	transfer_flight_passenger_ct varchar (40),
	total_passenger_count varchar (3),
	total_crew_count varchar (2),
	total_baggage_count varchar (3),
	total_baggage_weight varchar (6),
	total_freight_weight varchar (7),
	total_load_weight varchar (7),
	total_mail_weight varchar (5),
	transfer_baggage_count varchar (4),
	transfer_baggage_weight varchar (12),
	transfer_cargo_weight varchar (6),
	transfer_mail_weight varchar (6),
	transit_baggage_count varchar (6),
	transit_baggage_weight varchar (6),
	adult_passenger_count varchar (3),
	business_class_passenger_count varchar (3),
	child_passenger_count varchar (3),
	domestic_passenger_count varchar (3),
	economy_class_passenger_count varchar (3),
	first_class_passenger_count varchar (3),
	infant_passenger_count varchar (3),
	international_passenger_count varchar (3),
	local_passenger_count varchar (3),
	non_transfer_passenger_count varchar (3),
	transfer_passenger_count varchar (3),
	transit_passenger_count varchar (3),
	wheel_chair_passenger_count varchar (3),
	actual_off_blocks_date_time varchar (19),
	actual_on_blocks_date_time varchar (19),
	estimated_date_time varchar (19),
	estimated_flight_duration varchar (30),
	final_approach_date_time varchar (19),
	latest_known_date_time varchar (19),
	latest_known_date_time_source varchar (9),
	next_information_dt varchar (19),
	next_station_actual_dt varchar (19),
	next_station_estimated_dt varchar (19),
	next_station_scheduled_dt varchar (19),
	previous_station_airborne_dt varchar (19),
	previous_station_estimated_dt varchar (19),
	previous_station_scheduled_dt varchar (19),
	scheduled_date_time varchar (19),
	ten_mile_out_date_time varchar (19),
	wheels_down_date_time varchar (19),
	wheels_up_date_time varchar (19),
	port_of_call_iata_code varchar (50),
	port_of_call_icao_code varchar (50),
	public_flight_identity varchar (16),
	public_carrier_code varchar (6),
	public_date_time varchar (19),
	removed int default 0,
	key
);

drop  INDEX idx_afds ON imfDB.afds_info;
CREATE UNIQUE INDEX idx_afds ON imfDB.afds_info(flight_identity,flight_direction,flight_repeat_count,scheduled_date,removed); 



							     
