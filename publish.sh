#!/usr/bin/env bash

url=http://localhost:8080/orders

for i in {1..100}; do
  echo "---- ${i} -------"
  curl -d "{\"identifier\": \"${i}\",\"customer\": \"Customer X\", \"value\": 1500}" -H "Content-Type: application/json" -X POST $url
done