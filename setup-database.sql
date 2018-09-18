#!/bin/bash

mysql -u root -ppass -e "drop database challenge; create database 
challenge;"


mysql -u root -ppass --default-character-set=utf8 < src/utils/database/create-tables.sql 


mysql -u root -ppass --default-character-set=utf8 < src/utils/database/init-test-values.sql


