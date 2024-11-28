ALTER TABLE location ADD COLUMN geom GEOMETRY(Point, 4326);
UPDATE location SET geom = ST_SetSRID(ST_MakePoint(longitude, latitude), 4326);
CREATE INDEX location_geom_idx ON location USING GIST (geom);
