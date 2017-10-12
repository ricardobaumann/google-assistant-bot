#!/usr/bin/env bash
aws --region 'eu-central-1' s3 cp s3://up-config-eu-central-1/production/content-bot/application.yml src/main/resources/application.yml
./gradlew clean build
serverless deploy