# REST-rajapinta ja käyttötapaukset (hahmottelua)

## Käyttäjähallinta

`POST /users` Käyttäjätilin luominen

`GET /users` Käyttäjäluettelo

`GET /users/{user_id}` Yksittäisen käyttäjän tiedot

`PUT /users/{user_id}` Yksittäisen käyttäjän tietojen päivitys

`DELETE /users/{user_id}` Käyttäjätilin poisto

## Kirjautuminen

`POST /login` Sisäänkirjautuminen

`GET /logout` Uloskirjautuminen

## Treenikierto (Cycle)

`POST /cycles` Uuden treenikierron luominen

`GET /users/{user_id}/cycles` Käyttäjän kaikki treenikierrot

`GET /cycles/{cycle_id}` Yksittäinen treenikierto

`PUT /cycles/{cycle_id}` Yksittäisen treenikierron tietojen päivittäminen

`GET /cycles` Kaikki treenikierrot, tarvitaan ehkä tilastotietojen laskemiseen?

`DELETE /cycles/{cycle_id}` Treenikierron poistaminen

## Harjoitus (Workout)

(Käyttöliittymäkirjaus: voiko treenin poistaa vai ainoastaan keskeyttää? Jos jälkimmäinen, tarvitaan jonkinlainen completed-bitti)

(Toiminnallinen linjaus: POST heti aluksi, sen jälkeen sarja PUTteja, jotta kantaan tallentuu jotain vaikka tulisi häiriö?. Siis aluksi luodaan uusi Workout, jossa ennalta määrätyt mutta vielä tyhjät MoveSetit)

`POST /workouts` Uuden harjoituksen luominen

`PUT /workouts/{workout_id}` Harjoituksen tietojen päivittäminen

`GET /cycles/{cycle_id}/workouts` Tietyn treenikierron kaikki harjoitukset

`GET /workout/{workout_id}` Tietyn harjoituksen tarkemman tiedot    

## Sarja (MoveSet)

(Käyttöliittymälinjaus: sarja ei varsinaisesti muokattavissa, paitsi yhdellä toistot-napilla.)

`GET /workouts/{workout_id}/movesets` Tietyn harjoituksen kaikki sarjat (osa tai kaikki saattavat olla hakuhetkellä tyhjiä)

`POST /workouts/{workout_id}/movesets` Uuden (viimeksi tehdyn) sarjan tallentaminen

`PUT /workouts/{workout_id}/movesets/{set_id}` Yksittäisen sarjan toteutuneen toistomäärän muuttaminen


