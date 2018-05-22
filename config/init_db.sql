drop table if exists contact;
drop table if exists list_section_content;
drop table if exists organization_section_content;
drop table if exists organization_section;
drop table if exists organization;
drop table if exists section;
drop table if exists resume;

create table resume
(
  uuid      varchar(36) not null
    constraint resume_pkey
    primary key,
  full_name text        not null
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
    references resume,
  section_type text        not null,
  value        text        not null,
  constraint section_uk
  unique (uuid, section_type)
);

create table list_section_content
(
  id         serial not null
    constraint list_section_content_pkey
    primary key,
  value      text   not null,
  section_id integer
    constraint list_section_content_section_id_fk
    references section
    on delete cascade
);

create index list_section_content_section_id_index
  on list_section_content (section_id);


create table organization
(
  id        serial not null
    constraint organization_pkey
    primary key,
  name      text   not null,
  home_page text,
  constraint organization_uk
  unique (name, home_page)
);

create unique index organization_name_home_page_index
  on organization (name, home_page);

create table organization_section
(
  id              serial  not null
    constraint organization_section_pkey
    primary key,
  section_id      integer not null
    constraint organization_section_section_id_fk
    references section
    on delete cascade,
  organization_id integer not null
    constraint organization_section_organization_id_fk
    references organization
    on delete cascade,
  constraint organization_section_section_id_organization_id_uk
  unique (section_id, organization_id)
);

create table organization_section_content
(
  id                      serial  not null
    constraint organization_section_content_pkey
    primary key,
  organization_section_id integer not null
    constraint organization_section_content_organization_section_id_fk
    references organization_section
    on delete cascade,
  date_from               date    not null,
  date_to                 date    not null,
  position                text    not null,
  description             text
);














