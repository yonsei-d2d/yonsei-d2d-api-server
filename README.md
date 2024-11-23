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
This script parses the OSM map data and generates migration data.
4. From the repository root, run:```docker-compose up```
This starts the PostgreSQL database and the OSRM routing server.
5. Open the project in your IDE and run the server once to enable automatic table creation (ddl-auto setting).
6. Import the generated migration data (location.csv in the repository root) into the database.
7. The setup is complete, and the project is ready to use.