create table notification
(
	id bigint auto_increment,
	notifier bigint not null,
	receiver bigint not null,
	outerId BIGINT not null,
	gmt_created bigint not null,
	status int default 0 not null,
	constraint notification_pk
		primary key (id)
);