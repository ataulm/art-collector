art collector
=============

art collector displays a selection of paintings from the [Harvard Art Museum API](https://github.com/harvardartmuseums/api-docs).

We need three screens:

- `/` showing a collection of paintings
- `/{person_id}` showing the collection of paintings by the given artist
- `/{person_id}/{object_id}` showing a single painting

## Why

The aim is to develop a small app so I can learn about:

- Coroutines
- Dagger 2
- Room (or perhaps Realm, then Realm to Room migration)
- Dynamic feature modules

I'll keep meaningful changes restricted to PRs, and will try to keep them small and well documented. Please comment on the PRs if you have any questions/suggestions.

## Building the app

To build the app, you'll need to add `harvard.properties` to the project root with your API key substituted:

```
apiKey=123abc456def
```

[Thanks for the name.](https://github.com/florina-muntenescu)
