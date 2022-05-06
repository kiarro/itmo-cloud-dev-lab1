# postgres
FROM postgres
ENV POSTGRES_USER dragon
ENV POSTGRES_PASSWORD dragon
ENV POSTGRES_DB dragon_base
# copy sql script for creating tables
COPY CreateDB.sql /docker-entrypoint-initdb.d/