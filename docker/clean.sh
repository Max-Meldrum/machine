#! /bin/bash

docker stop postgres
docker rm postgres
# WILL DELETE ALL DATA
rm -r /srv/docker/machine/postgres/data
