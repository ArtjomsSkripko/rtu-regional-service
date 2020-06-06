FROM postgres:9.4.22
ADD db/*.sql /docker-entrypoint-initdb.d/
ENV POSTGRES_USER user_name
ENV POSTGRES_PASSWORD password
ENV POSTGRES_DB regional
EXPOSE 5432
