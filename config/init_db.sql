create table public.resume
(
    uuid      char(36) not null
        constraint resume_pk
            primary key,
    full_name text     not null
);

alter table public.resume
    owner to marina;

create table public.contact
(
    id          serial
        constraint contact_pk
            primary key,
    resume_uuid char(36) not null
        constraint contact_resume_uuid_fk
            references public.resume
            on delete cascade,
    type        text     not null,
    value       text     not null
);

alter table public.contact
    owner to marina;

create unique index contact_resume_uuid_type_index
    on public.contact (resume_uuid, type);