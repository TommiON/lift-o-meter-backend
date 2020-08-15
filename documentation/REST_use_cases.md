# REST-rajapinta ja käyttötapaukset (hahmottelua)

## Käyttäjähallinta

`POST /users` Käyttäjätilin luominen

`GET /users` Käyttäjäluettelo

`GET /users/{id}` Yksittäisen käyttäjän tiedot

`PUT /users/{id}` Yksittäisen käyttäjän tietojen päivitys

`DELETE /users/{id}` Käyttäjätilin poisto

## Kirjautuminen

`POST /login` Sisäänkirjautuminen

`GET /logout` Uloskirjautuminen

(Alla oleville pitää kerrata autorisointijutut: tokenilla saadaan varmaan jotenkin ohjautumaan oikeisiin paikkoihin ilman että käyttäjää tarvitsee identifioida pyyntö-urlissa.)

## Ohjelma (Program)

## Harjoitus (Workout)

## Sarja (Set)

(Käyttöliittymälinjaus: sarja ei varsinaisesti muokattavissa, paitsi yhdellä toistot-napilla.)

`GET /sets` 

`POST /sets` Viimeksi tehdyn sarjan tallentaminen

`GET /sets/{id}` Yksittäisen sarjan tiedot

`PUT /sets/{id}` Yksittäisen sarjan toteutuneen toistomäärän muuttaminen


