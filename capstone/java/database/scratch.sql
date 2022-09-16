select * from favourite_meme;
select * from favourite_image;
select * from image;
select * from image_author;
select * from image_tag;
select * from meme;
select * from meme_author;
select * from meme_text;
select * from person;
select * from tag;
select * from team;
select * from team_member;

select distinct t.*, it.image_id
from tag t
         left join image_tag it on t.tag_id = it.tag_id
        and it.image_id = :imageId
where it.image_id is null;

select m.*
from
    favourite_meme fm
        left join meme m on m.meme_id = fm.meme_id
where fm.person_id = :currentUserId
  and m.meme_id = :memeId
  and m.active is true and m.approved is true;

m.active is true and m.approved is true
  and (
select
    i.*
from
    image i
        join image_tag it on i.image_id = it.image_id
        join tag t on t.tag_id = it.tag_id
where i.active is true and tag_name ilike :tagName;

insert into image (file_name, mime_type, file_size, file_blob, active) values (:file_name, :mime_type, :file_size, :file_blob, true) returning image_id;

update person set role = 'Admin' where person_id = 10;
update person set role = 'Standard' where person_id = :personId;

update image set popularity = popularity + 1 where image_id = :imageId; insert into favourite_image (person_id, image_id) values (:personId, :imageId);

select p.* from person p where p.active=true and p.role ilike 'admin';

select p.username, t.team_name from team t
        join team_member tm on t.team_id = tm.team_id
        join person p on p.person_id = tm.person_id
where p.person_id = :person_id;

select
    t.team_name
from
    team t
        join team_member tm on t.team_id = tm.team_id
        join person p on p.person_id = tm.person_id
where p.person_id = :person_id;

select
    i.*
from
    image i
        join favourite_image fi on i.image_id = fi.image_id
where fi.person_id = :personID;

select
    i.*
from
    image i;

select distinct
    m.*
from
    meme m
        join meme_text mt on m.meme_id = mt.meme_id
where m.active is true
  and mt.text ilike :word;

select
    m.*
from
    meme m
        join image i on i.image_id = m.image_id
        join image_tag it on i.image_id = it.image_id
        join tag t on t.tag_id = it.tag_id
where m.active is true
  and t.tag_name ilike :tag;




-- region Movie selects.
select * from app_user;
select * from person;

update app_user set role='admin' where user_name='rob';
update app_user set role='standard' where user_name='rob';

select
    m.*,
    p.person_name as director_name
from
    movie m
    left join person p
        on p.person_id = m.director_id
where
        (
          m.title ilike :search
          or
          m.overview ilike :search
        )
        and
        (
          m.length_minutes between :minLength and :maxLength
        );


--
-- select
--         p.*
--     from
--     person p
--     join movie_actor ma
--         on p.person_id = ma.actor_id;
-- where
--     p.person_name ilike :name
-- order by p.person_name



select
    m.*,
    p.person_name as director_name
from
    movie m
        left join person p
                  on p.person_id = m.director_id
where
    m.movie_id =  1;



update app_user
set
    first_name=:first_name,
    last_name=:last_name,
    role=:role
where
    id=:id;

update app_user
set
    password = :password,
    salt = :salt
where
    user_name = :username;



update movie
    set
        overview = :overview
    where
        movie_id = :id;
-- endregion

update meme
    set approved=false
    where meme_id between 1 and 7
    returning *;

update meme
    set active=true
where meme_id=6

select *
from favourite_meme
where person_id=:userId and meme_id=:memeId

update person p
    join meme_author ma on p.person_id = ma.meme_author
	returning *

update person, meme
set person.active=false, meme.active=false
returning *

update (
select * from meme
join meme_author on meme.meme_id = meme_author.meme_id
where meme_author.author_id=5) as t
set t.active = false;

update meme
set active=false
where (meme_id in (select ma.meme_id from meme_author ma where ma.author_id = 5));
returning *;

-- join meme_author ma on me.meme_id = ma.meme_id
where meme_author.author_id=5;
returning *;

select * from meme
where person_id=5;
    set p.active=false
	where person_id = :person_id