art collector
=============

https://github.com/harvardartmuseums/api-docs

Add `harvard.properties` to the project root:

```
apiKey=YOUR_API_KEY_HERE
```

#### Aim
**tiny** modularised app, showcasing applinks, deeplinks and instant apps.

We need three screens:

- `/` showing a collection of paintings
- `artist/{person_id}` showing a single artist and their work
- `piece/{object_id}` showing a single piece

#### Current state (inc. next steps if applicable)

- Added Dagger in the `paintings` package
- Using coroutines to fetch and display a list of paintings in the activity
- the `paintings` package should move into its own feature module, but can wait until adding another
feature
