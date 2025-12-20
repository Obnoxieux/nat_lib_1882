docker exec -i -t /postgres_natlib /usr/bin/psql --file=/var/lib/postgresql/local-TIMESTAMP-dump.sql --username=db --host=localhost --port=5432 db
