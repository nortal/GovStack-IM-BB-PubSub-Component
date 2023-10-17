# GovStack IM-BB PubSub Component

Monorepo consisting of following

## Sub-components

- Deployment for local development including components and technologies
- Management API
- Management UI
- Messaging API
- Database schemas
- Shared components for both API's
- End-to-end tests

(All components including the sub-components fall under unified license agreement present in this root folder of the monorepo.)

## Quick start

- Build im-msg-db docker image: ```./gradlew :im-msg-db/buildDockerImage```
- Build management-api docker image: ```./gradlew :management-api:bootBuildImage```
