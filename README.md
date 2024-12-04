# yonsei-d2d-api-server

## Requirements
- Java 17
  - for running Spring Boot API Server
- Docker & docker-compose 
  - for running [osrm-backend](https://github.com/Project-OSRM/osrm-backend)
- JOSM 
  - for editing [osm map data](https://josm.openstreetmap.de/)
- Python3 (for parsing and renumbering .osm files)

## Getting started
1. Install all required software
2. Clone the Repository
3. From the repository root, execute:```./preprocess_map.sh```
This script parses/preprocesses the OSM map data and generates migration data/OSRM data.
4. From the repository root, run:```docker-compose up```
This starts the PostgreSQL database and the OSRM routing server.
5. Open the project in your IDE and run the server once to enable automatic table creation (flyway).
6. The setup is complete, and the project is ready to use.

## Caution
When modifying the map, make sure to edit only the ```yonsei.osm``` file.

**DO NOT DIRECTLY EDIT THE ```yonsei_processed.osm``` FILE**, as it is automatically generated.
After modifying the ```yonsei.osm``` file, running the preprocessor script will automatically generate the ```yonsei_processed.osm``` file.

During the preprocessing stage, node renumbering and node ID generation are performed, which involves modifying the original ```yonsei.osm``` file.
Therefore, ensure that you close any files you are editing before running the preprocess script.