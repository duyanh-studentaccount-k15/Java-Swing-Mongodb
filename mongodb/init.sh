#!/bin/bash

if test -z "$COFFEE_SHOP_PASSWORD"; then
    echo "COFFEE_SHOP_PASSWORD not defined"
    exit 1
fi

auth="-u user -p $COFFEE_SHOP_PASSWORD"

# MONGODB USER CREATION
(
echo "setup mongodb auth"
create_user="if (!db.getUser('onlyu')) { db.createUser({ user: 'onlyu', pwd: '$COFFEE_SHOP_PASSWORD', roles: [ {role:'readWrite', db:'coffee-shop'} ]}) }"
until mongo coffee-shop --eval "$create_user" || mongo coffee-shop $auth --eval "$create_user"; do sleep 5; done
killall mongod
sleep 1
killall -9 mongod
) &

# INIT DUMP EXECUTION
(
if test -n "$INIT_DUMP"; then
    echo "execute dump file"
	until mongo coffee-shop $auth $INIT_DUMP; do sleep 5; done
fi
) &

echo "start mongodb without auth"
chown -R mongodb /data/db
gosu mongodb mongod --bind_ip_all "$@"

echo "restarting with auth on"
sleep 5
exec gosu mongodb mongod --bind_ip_all --auth "$@"