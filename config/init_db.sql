drop table if exists contact;
drop table if exists section;
drop table if exists resume;

drop function if exists resumerevisionfunction();
drop sequence if exists revisionsq;

-- we don't know how to generate database resumes (class Database) :(
create sequence revisionsq;

create table resume
(
  uuid      varchar(36) not null
    constraint resume_pkey
    primary key,
  full_name text        not null,
  revision  integer
);

create index resume_uuid_full_name_index
  on resume (uuid, full_name);

create table contact
(
  id           serial not null
    constraint contact_pkey
    primary key,
  uuid         varchar(36)
    constraint contact_resume_uuid_fk
    references resume
    on delete cascade,
  contact_type text   not null,
  value        text   not null,
  constraint contact_uuid_contact_type_uk
  unique (uuid, contact_type)
);

create table section
(
  id           serial      not null
    constraint section_id_pk
    primary key,
  uuid         varchar(36) not null
    constraint section_resume_uuid_fk
    references resume
    on delete cascade,
  section_type text        not null,
  value        text        not null,
  constraint section_uk
  unique (uuid, section_type)
);

create function resumerevisionfunction()
  returns trigger
language plpgsql
as $$
BEGIN
  New.Revision := nextval('RevisionSq');
  Return NEW;
END;
$$;

create trigger resumerevisioninserttrigger
  before insert
  on resume
  for each row
execute procedure resumerevisionfunction();

create trigger resumerevisionupdatetrigger
  before update
  on resume
  for each row
execute procedure resumerevisionfunction();










