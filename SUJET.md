# Kata : backend JIRA-like en architecture hexagonale

## Contexte

Ce projet est un mini "JIRA" (User / Sprint / Ticket, avec Commentaires et Tags sur les
tickets) construit en architecture hexagonale (ports & adapters) avec Spring Boot,
Spring Data JPA et PostgreSQL.

Toute la plomberie (modèle de domaine, ports, adapters JPA, mappers, controllers REST,
jeu de données de démo) est déjà écrite et fonctionne. Il reste **6 exercices**,
chacun isolé dans une méthode précise, chacun ciblant une compétence précise :

| # | Fichier à compléter |
|---|---|
| A | `infrastructure/persistence/TicketRepositoryAdapter.java` → `findDetailById` |
| B | `infrastructure/persistence/TicketRepositoryAdapter.java` → `findSummariesBySprintId` |
| C | `infrastructure/persistence/TicketRepositoryAdapter.java` → `findFullById` |
| D | `infrastructure/keygen/SequentialTicketKeyGenerator.java` → `nextKey` |
| E | `application/SprintApplicationService.java` → `getStatistics` |
| F | `application/SprintClosure.java` → `close` |

Chaque exercice a son propre test JUnit, actuellement rouge (il appelle une méthode qui
lève `UnsupportedOperationException`). Le but est de le faire passer au vert - **sans**
casser les deux tests "garde-fou" (`src/test/java/.../architecture/`) qui bloquent les
solutions de facilité (tout passer en `EAGER`, ou remplacer un `List` par un `Set`).

## Lancer le projet

- `./gradlew bootTestRun` démarre l'application avec une base PostgreSQL locale fournie
  automatiquement par Testcontainers (rien à installer), avec des données de démo. Les
  fichiers `http/*.http` (format IntelliJ HTTP Client) permettent de tester à la main.
- `./gradlew test` lance toute la suite de tests, y compris les 6 tests d'exercices.
- `./gradlew test --tests "*TicketDetailLazyLoadingTest"` lance un seul test (idem pour
  les autres : `TicketListForSprintQueryCountTest`, `TicketFullFetchBagExceptionTest`,
  `TicketKeyGeneratorConcurrencyTest`, `SprintStatisticsStreamTest`,
  `SprintClosurePureFunctionTest`).

## Lire cet exemple en premier

Avant de commencer, regardez comment un cas d'usage "qui marche déjà" est câblé de bout
en bout, pour repérer le pattern à reproduire :

- `domain/port/in/AddCommentToTicketUseCase.java` (le contrat)
- `application/TicketApplicationService.java` → `addComment` (l'implémentation)
- `infrastructure/persistence/CommentRepositoryAdapter.java` (l'adapter JPA)
- `infrastructure/persistence/mapper/CommentMapper.java` (le mapping manuel entité → domaine)
- `infrastructure/web/TicketController.java` → `addComment` (le controller REST)

## Règles globales

- Pas de Lombok, pas de MapStruct : tout le mapping entité ↔ domaine est écrit à la
  main, exprès (c'est souvent dans le mapping que se niche le piège du lazy loading).
- Ne "résolvez" jamais un piège Hibernate en passant une relation en `FetchType.EAGER`
  ou une collection `List` en `Set`. Deux tests dans `architecture/` vérifient ça par
  réflexion et échoueront si vous le faites, même si votre test d'exercice passe.
- Les méthodes à compléter sont marquées par un commentaire `EXERCISE <lettre>` et lèvent
  `UnsupportedOperationException` tant qu'elles ne sont pas implémentées.

## Les 6 exercices

### A (`TicketRepositoryAdapter.findDetailById`)

- Test à faire passer : `ticket/TicketDetailLazyLoadingTest`
- Appel HTTP pour tester à la main : `GET {{baseUrl}}/api/tickets/1` (requête "EXERCISE A"
  dans `http/tickets.http`).

### B (`TicketRepositoryAdapter.findSummariesBySprintId`)

Doit renvoyer un `TicketSummary` par ticket du sprint, avec le nom de l'assigné résolu
(`null` si pas d'assigné) et le nombre total de commentaires du ticket.

- Test à faire passer : `ticket/TicketListForSprintQueryCountTest`

### C (`TicketRepositoryAdapter.findFullById`)

- Test à faire passer : `ticket/TicketFullFetchBagExceptionTest`

### D (`SequentialTicketKeyGenerator.nextKey`)

- Test à faire passer : `ticket/TicketKeyGeneratorConcurrencyTest`

### E (`SprintApplicationService.getStatistics`)

- Test à faire passer : `sprint/SprintStatisticsStreamTest`

### F (`SprintClosure.close`)

- Test à faire passer : `sprint/SprintClosurePureFunctionTest`
