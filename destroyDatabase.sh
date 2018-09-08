#!/usr/bin/env bash

docker-compose --project-name coffee_shop down
# for macos
docker rmi -f $(docker images --format '{{.Repository}}' | grep 'coffee_shop_database')
# for linux
docker rmi -f $(docker images --format '{{.Repository}}' | grep 'coffeeshop_database')