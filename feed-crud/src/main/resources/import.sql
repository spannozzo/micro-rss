 create table if not exists feed (
   	id int8 not null,
    deleted boolean not null,
    description varchar(255),
    name varchar(255),
    url varchar(255),
    primary key (id)
);

insert into feed (id, name, description,url,deleted) values (-1, 'Il fatto quotidiano','Italian online news feed', 'https://www.ilfattoquotidiano.it/feed/',false);
insert into feed (id, name, description,url,deleted) values (-2, 'NOS Nieuws','Dutch online news feed', 'http://feeds.nos.nl/nosjournaal?format=xml',false);
insert into feed (id, name, description,url,deleted) values (-3, 'Stack Overflow','Stack Overflow feed', 'https://stackoverflow.com/feeds/',false);

