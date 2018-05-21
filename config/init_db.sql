create table resume
(
  uuid      char(36) not null
    constraint resume_pkey
    primary key,
  full_name text     not null
);

create table contact
(
  id           serial not null
    constraint contact_pkey
    primary key,
  uuid         char(36)
    constraint contact_resume_uuid_fk
    references resume
    on delete cascade,
  contact_type text   not null,
  value        text   not null
);

create unique index contact_uuid_contact_type
  on contact (uuid, contact_type);



