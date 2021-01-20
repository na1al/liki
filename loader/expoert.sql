-- medicine
select id,
       substring(CASE WHEN name_local = '' || name_local IS NULL THEN name ELSE name_local END, 1, 255) as name,
       unique_number                                                                                    as code_ua,
       morion_code                                                                                      as code_morion,
       optima_code                                                                                      as code_optima,
       badm_code                                                                                        as code_badm,
       description,
       (select concat('https://static.doc.ua', '/', TRIM(BOTH '/' FROM web_url), '/', file)
        from vrachi.media
        where media.id = media_id
          and storage = 'S3Storage')                                                                    as image
from medicine
where (name_local != '' or name != '');


select mp.medicine_id                                                                                external_medicine_id,
       substring(CASE WHEN name_ua = '' || name_ua IS NULL THEN name_ru ELSE name_ua END, 1, 255) as name,
       mp.description,
       mp.integration_id,
       own_medicine_id                                                                               medicine_id
from medicine_partner mp
         left join integration_medicine im
                   on im.partner_medicine_id = mp.id AND mp.integration_id = im.integration_id
where (mp.name_ua != '' or mp.name_ru != '')
  AND (own_medicine_id is NULL OR
       own_medicine_id not in (select id from medicine where name_local = '' and name = ''))


-- add price and quantity > 0 and not null
select integration_id,
       medicine_id  as external_medicine_id,
       affiliate_id as external_pharmacy_id,
       price * 100  as price,
       quantity
from medicine_price;

-- Pharmacy
select pharmacy_id as integration_id,
       branch_id   as external_pharmacy_id,
       name,
       address,
       CASE
           WHEN city_id = 1 THEN 1116
           WHEN city_id = 7 THEN 371
           WHEN city_id = 12 THEN 2088
           WHEN city_id = 35 THEN 475
           WHEN city_id = 15 THEN 1885
           WHEN city_id = 23 THEN 2611
           WHEN city_id = 11 THEN 226
           WHEN city_id = 6 THEN 362
           WHEN city_id = 51 THEN 455
           WHEN city_id = 8 THEN 1575
           WHEN city_id = 9 THEN 1828
           WHEN city_id = 16 THEN 989
           WHEN city_id = 13 THEN 587
           WHEN city_id = 10 THEN 850
           WHEN city_id = 5 THEN 1732
           WHEN city_id = 14 THEN 1894
           WHEN city_id = 30 THEN 1984
           WHEN city_id = 49 THEN 2152
           WHEN city_id = 3 THEN 2301
           WHEN city_id = 24 THEN 2487
           WHEN city_id = 22 THEN 2717
           WHEN city_id = 4 THEN 2864
           WHEN city_id = 82 THEN 2703
           WHEN city_id = 93 THEN 2302
           WHEN city_id = 94 THEN 2296
           WHEN city_id = 96 THEN 2288
           ELSE NULL
           END     as city_id
from pharmacy_affiliate
where pharmacy_id > 0;


-- Tags

select p.medicine_id, p.popular_category_id as external_tag_id, 'CATEGORY' as type
from popular_category_medicine p
         inner join popular_category pc on p.popular_category_id = pc.id
where pc.popular = 1

UNION

select m.medicine_id, fv.id as external_tag_id, 'MANUFACTURER' as type
from medicine_filter_value m
         inner join filter_value fv on m.filter_value_id = fv.id
where filter_id = 4

UNION

select m.medicine_id, fv.id as external_tag_id, 'WHOM' as type
from medicine_filter_value m
         inner join filter_value fv on m.filter_value_id = fv.id
where filter_id = 6

UNION

select m.medicine_id, fv.id as external_tag_id, 'FORM' as type
from medicine_filter_value m
         inner join filter_value fv on m.filter_value_id = fv.id
where filter_id = 7

UNION

select m.medicine_id, fv.id as external_tag_id, 'RECIPE' as type
from medicine_filter_value m
         inner join filter_value fv on m.filter_value_id = fv.id
where filter_id = 11;

-- MedicineTag

select model_id as external_id, value as name, 'CATEGORY' as type
from translation
where model_class = 'PopularCategory'
  and field = 'name'
  and model_id in (select id from apteka.popular_category where popular = 1)

UNION

select id as external_id, name, 'MANUFACTURER' as type
from apteka.filter_value
where filter_id = 4

UNION

select id as external_id, name, 'WHOM' as type
from apteka.filter_value
where filter_id = 6

UNION

select id as external_id, name, 'FORM' as type
from apteka.filter_value
where filter_id = 7

UNION

select id as external_id, name, 'RECIPE' as type
from apteka.filter_value
where filter_id = 11;




