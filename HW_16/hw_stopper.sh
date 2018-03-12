#!/usr/bin/env bash
kill $(jps | grep msg-core.jar | cut -d " " -f 1)
kill $(jps | grep msg-db-service.jar | cut -d " " -f 1)

