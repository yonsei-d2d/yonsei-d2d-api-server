DROP TABLE tag;
CREATE TABLE "public"."tag" (
                                "id" int8 GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                "tag" varchar(255) NOT NULL,
                                "location_id" uuid,
                                CONSTRAINT "fkb5k40bs285q51qs2iwu032c1f" FOREIGN KEY ("location_id") REFERENCES "public"."location"("id")
);
