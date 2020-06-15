# Bingie

[![CircleCI](https://circleci.com/gh/mantas84/Bingie/tree/master.svg?style=svg&circle-token=dacac278f17bc8f9d923cf5674ab0a32ca7d3a05)](https://circleci.com/gh/mantas84/Bingie)

[![CircleCI](https://circleci.com/gh/mantas84/Bingie/tree/dev.svg?style=svg&circle-token=dacac278f17bc8f9d923cf5674ab0a32ca7d3a05)](https://circleci.com/gh/mantas84/Bingie)

### Under development

### Api keys

You need to supply API / client keys for the various services the app uses. That is currently Trakt.tv, TMDb, TvDb and Fanart. You can find information about how to gain access via the relevant links.
```
TRAKT_CLIENT_ID=
TRAKT_CLIENT_SECRET=

TRAKT_CLIENT_ID_DEV=
TRAKT_CLIENT_SECRET_DEV=

TMDB_TOKEN=
TVDB_KEY=
FANART_KEY =
```
Put file apikey.properties in the project directory bingie/apikey.properties

Similary create keystore.properties file with

```
storePassword=
keyPassword=
keyAlias=
storeFile=${path to jks keystore}
```
