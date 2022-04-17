#!/bin/bash

sh ./infra-down.sh 
servers="gm-utils fc-core-back tcc-back"


for server in $servers; do

    cd $server
    echo $(pwd)

    ./mvnw clean package install -DskipTests
    echo ''
    cd .. 
done





