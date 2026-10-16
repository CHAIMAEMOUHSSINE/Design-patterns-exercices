# Exercice 1 — Design Patterns

## Objectif

Identifier les Design Patterns adaptés à quatre situations et représenter leurs diagrammes de classes UML.

## Sommaire

1. [Composite — Figures géométriques](#1-composite--figures-géométriques)
2. [Template Method et Factory Method — Plugin](#2-template-method-et-factory-method--plugin)
3. [Decorator — Ajouter des responsabilités](#3-decorator--ajouter-des-responsabilités)
4. [Observer — Notification du changement de score](#4-observer--notification-du-changement-de-score)
5. [Récapitulatif](#récapitulatif)

---

## 1. Composite — Figures géométriques

### Énoncé

Une figure peut être un cercle, un rectangle ou un groupe de figures.

### Pattern utilisé : Composite

Le pattern **Composite** permet de traiter de manière uniforme des objets simples (`Cercle`, `Rectangle`) et des compositions d’objets (`GroupeFigures`). Un groupe peut contenir plusieurs figures, y compris d’autres groupes.

### Diagramme de classes

```mermaid
classDiagram
    class Figure {
        <<interface>>
        +dessiner() void
    }

    class Cercle {
        +dessiner() void
    }

    class Rectangle {
        +dessiner() void
    }

    class GroupeFigures {
        -figures : List~Figure~
        +ajouter(Figure figure) void
        +retirer(Figure figure) void
        +dessiner() void
    }

    Figure <|.. Cercle
    Figure <|.. Rectangle
    Figure <|.. GroupeFigures
    GroupeFigures "1" *-- "0..*" Figure : contient
```

### Explication

- `Figure` définit l’interface commune.
- `Cercle` et `Rectangle` sont des feuilles (*Leaf*).
- `GroupeFigures` est le composite : il contient une collection de `Figure`.
- L’appel à `dessiner()` fonctionne aussi bien pour une figure individuelle que pour un groupe.

**Pourquoi Composite ?** Parce qu’on veut manipuler les objets simples et les groupes de figures via la même abstraction.

---

## 2. Template Method et Factory Method — Plugin

### Énoncé

Un plugin contient une opération qui définit le squelette d’un algorithme. Deux étapes (`partie1` et `partie2`) varient selon l’implémentation. Le client doit également pouvoir obtenir un plugin concret sans connaître sa classe d’implémentation.

### Patterns utilisés

- **Template Method** : la classe abstraite `Plugin` définit l’ordre général de l’algorithme dans `operation()`. Les sous-classes implémentent `partie1()` et `partie2()`.
- **Factory Method** : la création du plugin est déléguée à une méthode de fabrique. Le client dépend de l’abstraction `FabriquePlugin` et non de la classe concrète du plugin.

### Diagramme de classes

```mermaid
classDiagram
    class Plugin {
        <<abstract>>
        +operation() void
        #partie1()* void
        #partie2()* void
    }

    class PluginConcret {
        #partie1() void
        #partie2() void
    }

    class FabriquePlugin {
        <<abstract>>
        +creerPlugin()* Plugin
    }

    class FabriquePluginConcrete {
        +creerPlugin() Plugin
    }

    class Client {
        +utiliserPlugin(FabriquePlugin fabrique) void
    }

    Plugin <|-- PluginConcret
    FabriquePlugin <|-- FabriquePluginConcrete
    FabriquePluginConcrete ..> PluginConcret : crée
    Client --> FabriquePlugin : utilise
    Client --> Plugin : utilise le résultat
```

### Fonctionnement

1. Le client utilise une `FabriquePlugin`.
2. La fabrique concrète crée une instance de `PluginConcret` et la retourne comme un `Plugin`.
3. Le client appelle `operation()`.
4. `operation()` exécute le squelette commun et appelle les étapes spécialisées implémentées par le plugin concret.

**Pourquoi ces deux patterns ?** `Template Method` répond à la variation des étapes de l’algorithme. `Factory Method` répond au besoin de masquer la classe concrète lors de la création.

> Remarque : une fabrique simple peut également masquer la classe concrète. Le terme **Factory Method** désigne plus précisément une méthode de création redéfinie dans des sous-classes.

---

## 3. Decorator — Ajouter des responsabilités

### Énoncé

Un composant implémente une interface définissant `traitement()`. On veut ajouter des traitements avant et après l’exécution de cette méthode, sans modifier le code source du composant.

### Pattern utilisé : Decorator

Le pattern **Decorator** enveloppe un objet dans un autre objet qui respecte la même interface. Le décorateur peut ainsi ajouter des responsabilités avant et après l’appel à l’objet enveloppé.

### Diagramme de classes

```mermaid
classDiagram
    class Composant {
        <<interface>>
        +traitement() void
    }

    class ComposantConcret {
        +traitement() void
    }

    class Decorateur {
        <<abstract>>
        #composant : Composant
        +Decorateur(Composant composant)
        +traitement() void
    }

    class DecorateurAvantApres {
        +traitement() void
    }

    Composant <|.. ComposantConcret
    Composant <|.. Decorateur
    Decorateur o--> Composant : enveloppe
    Decorateur <|-- DecorateurAvantApres
```

### Fonctionnement

La méthode `traitement()` de `DecorateurAvantApres` :

1. exécute un traitement avant ;
2. appelle `composant.traitement()` ;
3. exécute un traitement après.

**Pourquoi Decorator ?** Il permet d’ajouter des responsabilités sans modifier la classe `ComposantConcret`. Le décorateur et le composant partagent la même interface.

---

## 4. Observer — Notification du changement de score

### Énoncé

La classe `Joueur` possède un score de type `int`. Les objets `Couloir`, `Camera` et `Gardien` doivent être informés à chaque changement de score, avec un couplage faible entre les classes.

### Pattern utilisé : Observer

Le pattern **Observer** établit une relation de type un-à-plusieurs : lorsqu’un sujet change d’état, il notifie ses observateurs inscrits.

### Diagramme de classes

```mermaid
classDiagram
    class Sujet {
        <<interface>>
        +ajouterObservateur(Observateur o) void
        +retirerObservateur(Observateur o) void
        +notifierObservateurs() void
    }

    class Joueur {
        -score : int
        -observateurs : List~Observateur~
        +setScore(int score) void
        +getScore() int
        +ajouterObservateur(Observateur o) void
        +retirerObservateur(Observateur o) void
        +notifierObservateurs() void
    }

    class Observateur {
        <<interface>>
        +actualiser(int score) void
    }

    class Couloir {
        +actualiser(int score) void
    }

    class Camera {
        +actualiser(int score) void
    }

    class Gardien {
        +actualiser(int score) void
    }

    Sujet <|.. Joueur
    Observateur <|.. Couloir
    Observateur <|.. Camera
    Observateur <|.. Gardien
    Joueur "1" o--> "0..*" Observateur : notifie
```

### Fonctionnement

1. `Couloir`, `Camera` et `Gardien` s’inscrivent auprès du `Joueur`.
2. Quand `setScore()` détecte un changement de score, le joueur met à jour son état puis appelle `notifierObservateurs()`.
3. Chaque observateur reçoit le nouveau score via `actualiser(score)` et réagit selon son rôle.

**Pourquoi Observer ?** `Joueur` dépend de l’interface `Observateur`, et non directement des classes `Couloir`, `Camera` et `Gardien`. On peut donc ajouter de nouveaux observateurs sans modifier le joueur.

---

## Récapitulatif

| Situation | Pattern | Indice principal |
|---|---|---|
| Une figure est simple ou composée d’autres figures | **Composite** | Traiter uniformément les objets et les groupes |
| Squelette d’algorithme avec étapes variables | **Template Method** | Définir l’ordre des étapes et redéfinir certaines étapes |
| Création sans dépendre de la classe concrète | **Factory Method** | Déléguer la création à une fabrique polymorphe |
| Ajouter des traitements avant et après une opération | **Decorator** | Envelopper un objet sans modifier sa classe |
| Notifier plusieurs objets lors d’un changement d’état | **Observer** | Diffuser les changements aux observateurs inscrits |

## Exercice 2
