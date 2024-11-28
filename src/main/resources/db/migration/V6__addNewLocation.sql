INSERT INTO "public"."location" ("id", "floor", "latitude", "level", "longitude", "name", "node_id", "type", "geom") VALUES
('327fca3f-47bb-5cf1-a7ef-3d19b8e80141', NULL, 37.56346683381, 0, 126.93572947105, '중앙도서관 옆 벤치', 290, 'place', NULL),
('63e0d45a-9a3d-5835-a585-1faba0733c89', NULL, 37.56597443378, 0, 126.93491480479, '언더우드생가', 289, 'place', NULL),
('73b0a232-b329-5ccd-a19e-fc3857840b78', NULL, 37.56537099851, 0, 126.93777890214, '윤동주문학동산', 291, 'place', NULL),
('c4e890d0-ed45-5bc5-8388-d63dd3984127', NULL, 37.56496303164, 0, 126.93641706505, '동주길', 287, 'place', NULL),
('c9b8101c-f148-54db-bfa4-6b9831c745cb', NULL, 37.56579597843, 0, 126.93857500168, '언더우드동상', 285, 'place', NULL),
('d4821b1f-0e52-528c-b86c-92c90834d55a', NULL, 37.56323593811, 0, 126.93742495286, '연세대학교 독수리상', 288, 'place', NULL),
('d60fd756-594f-55b3-b9a6-349673f91dd3', NULL, 37.56639205228, 0, 126.9408495673, '청송대', 286, 'place', NULL);

UPDATE location SET geom = ST_SetSRID(ST_MakePoint(longitude, latitude), 4326);