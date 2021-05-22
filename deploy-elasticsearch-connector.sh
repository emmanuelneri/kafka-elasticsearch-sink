#!/usr/bin/env bash

kafkaConnectorURL=http://localhost:8083/connectors

curl -v POST -H "Content-Type: application/json" -d @index-segregator-elasticsearch-connector.json ${kafkaConnectorURL}
