CREATE TABLE "public"."tag" (
                                  "id" int8 NOT NULL,
                                  "tag" varchar(255) NOT NULL,
                                  "location_id" uuid,
                                  CONSTRAINT "fkb5k40bs285q51qs2iwu032c1e" FOREIGN KEY ("location_id") REFERENCES "public"."location"("id"),
                                  PRIMARY KEY ("id")
);
