# Osmium docker registry deprecated
# docker run -t -v "./osrm/map:/data" stefda/osmium-tool osmium renumber -O -o /data/yonsei_processed.osm /data/yonsei.osm
rm ./osrm/map/yonsei_processed*
python3 ./osrm/map/renumber.py ./osrm/map/yonsei.osm ./osrm/map/yonsei_processed.osm
python3 ./osrm/map/preprocess.py ./osrm/map/yonsei_processed.osm
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