# Restitution - LEBARBANCHON Valentin

### Choix langage

J'ai choisi de réaliser ce projet en Kotlin, car il se rapproche de Java, langage que j'ai l'habitude d'utiliser, avec le framework Spring Boot.

### L'état actuel du projet
Le basePath est le suivant : `http://localhost:8080`.

La route demandée est la suivante : `POST /v1/tso/requests`. Elle permet de créer une demande de TSO. Le body de la requête doit être un JSON avec les champs suivants :
- `activationDate` : la date d'activation
- `volume` : le volume voulu

#### Exemple de body de requête :
```json
{
  "activationDate": "2029-01-01T00:00:00Z",
  "volume": 35
}
```
#### Exemple de réponse :
```json
{
  "volume": 35,
  "tsoResponses": [
    {
      "volume": 25,
      "asset": {
        "name": "Asset 1",
        "code": "A1"
      },
      "id": 2,
      "cost": 41.67
    },
    {
      "volume": 10,
      "asset": {
        "name": "Asset 2",
        "code": "A2"
      },
      "id": 3,
      "cost": 18.33
    }
  ],
  "cost": 60.0
}
```
La route récupère la meilleure composition possible pour la demande de TSO. 
Elle retourne le volume demandé, le coût total de la composition et la liste des TSOResponses, qui contiennent le volume récupéré, le coût effectif, l'asset et l'id de chaque asset.

Dans le cas où la demande ne peut pas être satisfaite, la route retourne une erreur 400 avec le message suivant :
```
Pas de solution possible
```

J'ai utilisé les contraintes de validation au niveau des DTO et l'annotation `@Valid` au niveau du controller pour refuser les requêtes invalides au plus tôt.

J'ai également utilisé un ExceptionHandler pour gérer les erreurs de validation et les erreurs de logique métier.

### Lancer le projet

Afin de lancer le projet, il suffit de éxecuter les commandes suivantes depuis la racine du projet :
- `docker-compose -f ./src/main/kotlin/fr/flexcity/test/docker/postgresql.yml up -d`
- `./gradlew bootRun` à la racine du projet.

La première commande sert à contenairiser une base de données PostgreSQL. 
La seconde commande permet de lancer le projet.

### Les améliorations possibles/en cours
#### Les tests
- Réaliser des tests d'intégration pour tester une route de bout en bout.
- Réaliser des tests unitaires pour tester le TSORequestService.
  - Un test à été réalisé mais je me suis retrouver face à un problème de persistence des données.
- Réaliser un docker-compose, chose que je n'ai jamais fait, j'ai essayé de le faire mais sans succès.
