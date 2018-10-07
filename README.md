art collector
=============

https://github.com/harvardartmuseums/api-docs

#### Aim
**tiny** modularised app, showcasing applinks, deeplinks and instant apps.

We need three screens:

- `/` showing a collection of paintings
- `artist/{person_id}` showing a single artist and their work
- `piece/{object_id}` showing a single piece

#### Current state (inc. next steps if applicable)

- Created `HarvardApi.paintings(): Deferred<Any>`
- Next, need to learn htf to use Deferred and create a type for the response so it can be persisted
with Realm (save as many as I can) (with the intention of swapping it out for Room later)
