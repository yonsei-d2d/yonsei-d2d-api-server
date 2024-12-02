INSERT INTO "public"."location" ("id", "floor", "latitude", "level", "longitude", "name", "node_id", "type", "geom") VALUES
 ('0bb96044-64c1-5d88-87f1-ebb50ccc1bca', NULL, 37.5621763807, 2, 126.93794260787, '백주년기념관 박물관', 306, 'facility', NULL),
 ('0cec7b58-75e8-5ff2-b517-91988ae54d5b', NULL, 37.56389552622, -1, 126.93843106159, '우체국', 299, 'facility', NULL),
 ('110194e6-d9f2-5f97-9d5c-58d62a3bf6f1', NULL, 37.56308159984, 1, 126.93677953371, '런어스 스튜디오', 308, 'place', NULL),
 ('17c97e28-c18e-549d-9bb2-c3acd2fb849e', NULL, 37.56340155665, 0, 126.93678934887, '중앙도서관 남측 출입문', 293, 'waypoint', NULL),
 ('5f9cd717-4206-5e6c-aa52-c28b073febca', NULL, 37.56361027239, 1, 126.93838403962, '꽃누리샘', 295, 'store', NULL),
 ('6c52c162-9225-50e0-8d68-8a71a7dcb70f', NULL, 37.56405222595, 1, 126.93441883863, '세븐일레븐 연세대과학관점 (편의점)', 302, 'convenience', NULL),
 ('774327af-7d05-5aa7-b7bd-bf974e35b41e', NULL, 37.56381641594, -1, 126.93840035336, '우리은행', 298, 'facility', NULL),
 ('87ce5426-af61-57eb-88c6-90ea44af21c4', NULL, 37.56387845178, -1, 126.93832606536, '학생회관 우체국 출입문', 300, 'waypoint', NULL),
 ('efb86b19-9060-5859-9dd1-ee62a0651ef6', NULL, 37.56588586485, 1, 126.93677582723, '원두우신학관 정문', 304, 'waypoint', NULL);


UPDATE location SET geom = ST_SetSRID(ST_MakePoint(longitude, latitude), 4326);

INSERT INTO "public"."tag" ("tag", "location_id") VALUES ('bank', '774327af-7d05-5aa7-b7bd-bf974e35b41e');
INSERT INTO "public"."tag" ("tag", "location_id") VALUES ('museum', '0bb96044-64c1-5d88-87f1-ebb50ccc1bca');
INSERT INTO "public"."tag" ("tag", "location_id") VALUES ('flower', '5f9cd717-4206-5e6c-aa52-c28b073febca');
INSERT INTO "public"."tag" ("tag", "location_id") VALUES ('postoffice', '0cec7b58-75e8-5ff2-b517-91988ae54d5b');
INSERT INTO "public"."tag" ("tag", "location_id") VALUES ('convenience', '6c52c162-9225-50e0-8d68-8a71a7dcb70f');
INSERT INTO "public"."tag" ("tag", "location_id") VALUES ('photo', '110194e6-d9f2-5f97-9d5c-58d62a3bf6f1');
INSERT INTO "public"."tag" ("tag", "location_id") VALUES ('stroll', 'c9b8101c-f148-54db-bfa4-6b9831c745cb');
INSERT INTO "public"."tag" ("tag", "location_id") VALUES ('stroll', 'd60fd756-594f-55b3-b9a6-349673f91dd3');
INSERT INTO "public"."tag" ("tag", "location_id") VALUES ('stroll', 'c4e890d0-ed45-5bc5-8388-d63dd3984127');
INSERT INTO "public"."tag" ("tag", "location_id") VALUES ('stroll', 'd4821b1f-0e52-528c-b86c-92c90834d55a');
INSERT INTO "public"."tag" ("tag", "location_id") VALUES ('museum', '63e0d45a-9a3d-5835-a585-1faba0733c89');
INSERT INTO "public"."tag" ("tag", "location_id") VALUES ('stroll', '327fca3f-47bb-5cf1-a7ef-3d19b8e80141');
INSERT INTO "public"."tag" ("tag", "location_id") VALUES ('stroll', '73b0a232-b329-5ccd-a19e-fc3857840b78');
INSERT INTO "public"."tag" ("tag", "location_id") VALUES ('stroll', '110194e6-d9f2-5f97-9d5c-58d62a3bf6f1');