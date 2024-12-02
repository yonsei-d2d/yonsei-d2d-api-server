# Osmium docker registry deprecated
# docker run -t -v "./osrm/map:/data" stefda/osmium-tool osmium renumber -O -o /data/yonsei_processed.osm /data/yonsei.osm

# Clean Old Datas
rm ./osrm/map/yonsei_processed*

# Gen IDs
python3 ./scripts/id_gen.py ./osrm/map/yonsei.osm ./osrm/map/yonsei_id_gen.osm

# Renumber Datas
python3 ./scripts/renumber.py ./osrm/map/yonsei_id_gen.osm ./osrm/map/yonsei_processed.osm

# Extract migration data
python3 ./scripts/preprocess.py ./osrm/map/yonsei_processed.osm ./migration.csv

# Backup original map data
cp ./osrm/map/yonsei.osm ./map_backups/yonsei_backup_$(date '+%Y-%m-%d_%H.%M.%S').osm

# Overwrite original map data
mv ./osrm/map/yonsei_id_gen.osm ./osrm/map/yonsei.osm


docker run -t -v "./osrm/map:/data"\
    -v "./osrm/map/way_handlers.lua:/opt/lib/way_handlers.lua"\
    -v "./osrm/map/guidance.lua:/opt/lib/guidance.lua"\
    ghcr.io/project-osrm/osrm-backend osrm-extract\
    -p /data/foot.lua /data/yonsei_processed.osm\
    || echo "osrm-extract failed"
docker run -t -v "./osrm/map:/data"\
    ghcr.io/project-osrm/osrm-backend osrm-partition\
    /data/yonsei_processed.osrm || echo "osrm-partition failed"
docker run -t -v "./osrm/map:/data"\
    ghcr.io/project-osrm/osrm-backend osrm-customize\
    /data/yonsei_processed.osrm || echo "osrm-customize failed"