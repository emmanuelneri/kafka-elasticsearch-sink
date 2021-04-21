#!/usr/bin/env bash

url=http://http://localhost:9200/

indices=$(curl -s http://localhost:9200/_cat/indices?format=json)
echo ${indices}

echo "${indices}" | jq -c -r '.[]' | while read index; do
  indexName=${index} | jq -r '.index'
  echo '---------------------------------------------------------------------'
  echo $(curl -s http://localhost:9200/${indexName}/_search?pretty=true&size=10)
done
