FROM postgres:16-bullseye

RUN apt-get update && apt-get install -y \
    postgis \
    postgresql-16-postgis-3 \
    postgresql-16-postgis-3-scripts \
    --no-install-recommends && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*