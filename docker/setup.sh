#! /bin/bash

if [[ -z "${DB_PG_PWD}" ]]; then
	echo "DB_PG_PWD environmental variable has not been set! Check postgres_env.sh"
else
	docker run --name postgres --restart=always -e POSTGRES_PASSWORD=$DB_PG_PWD -p 5432:5432 -v /srv/docker/machine/postgres/data:/var/lib/postgresql/data -d postgres:9.4
fi
