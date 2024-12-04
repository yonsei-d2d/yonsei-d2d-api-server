INSERT INTO "public"."location" ("id", "floor", "latitude", "level", "longitude", "name", "node_id", "type") VALUES ('f7be2888-58c7-569f-9fdf-1203e4641f5d', NULL, 37.5615696, 0, 126.9334969, '연세대학교 남문', 3212, 'place');
INSERT INTO "public"."location" ("id", "floor", "latitude", "level", "longitude", "name", "node_id", "type") VALUES ('1d9c35d2-4703-5bf1-9438-b12ee77ad05e', NULL, 37.5604467, 0, 126.9368302, '연세대학교 정문', 3461, 'place');
INSERT INTO "public"."location" ("id", "latitude", "level", "longitude", "name", "node_id", "type") VALUES ('9304ae55-7a9a-562d-815f-0bdfed3c1479', 37.5638432, 0, 126.9338855, '연세대학교 서문', 4243, 'place');

UPDATE location SET geom = ST_SetSRID(ST_MakePoint(longitude, latitude), 4326);

INSERT INTO "public"."alias" ("id", "name", "location_id") VALUES (93, '연세대학교 남문', 'f7be2888-58c7-569f-9fdf-1203e4641f5d');
INSERT INTO "public"."alias" ("id", "name", "location_id") VALUES (94, '연세대학교 정문', '1d9c35d2-4703-5bf1-9438-b12ee77ad05e');
INSERT INTO "public"."alias" ("id", "name", "location_id") VALUES (95, '연세대학교 서문', '1d9c35d2-4703-5bf1-9438-b12ee77ad05e');