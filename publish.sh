#!/usr/bin/env bash

url=http://localhost:8080/orders

for i in {1..100}; do
  echo "---- SALE: ${i} -------"
  curl -d "{\"identifier\": \"${i}\",\"customer\": \"Customer Y\", \"value\": 100,\"type\": \"SALE\"}" -H "Content-Type: application/json" -X POST $url
done

for i in {1..200}; do
  echo "---- PURCHASE: ${i} -------"
  curl -d "{\"identifier\": \"${i}\",\"customer\": \"Customer X\", \"value\": 100,\"type\": \"PURCHASE\"}" -H "Content-Type: application/json" -X POST $url
done