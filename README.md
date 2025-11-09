# Damareen API

# A program elindítása
A program elindításához használhatjuk a mavent a project mappájában:

```
mvn spring-boot:run
```
vagy IntelIj-ből is elindítható a projekt

# A projektről
Sajnos nem sikerült befejeznünk a projektet, mivel a frontend nem készült el.
Ami elkészült azt felraktuk ide: [Github](https://github.com/robothaver/damareen)

## Base URL

```
http://localhost:8080/api
```


## Cards

### Create World Card

**POST** `/card/{player}/create`
**Body**:

```json
{
  "name": "Aragorn",
  "damage": 5,
  "health": 8,
  "cardType": "FIRE"
}
```

**Response**: `WorldCard` object

### Get All Cards for Player

**GET** `/card/{player}`

**Response**:

```json
{
  "worldCards": [...],
  "leadCards": [...]
}
```

### Create Leader Card

**POST** `/card/{player}/lead`
**Body**:

```json
{
  "worldCardId": 1,
  "name": "King Aragorn",
  "derivation": "DOUBLE_DAMAGE"
}
```

**Response**: `LeadCard` object

---

## Dungeons

### Create Dungeon

**POST** `/dungeon/{player}/create`
**Body**:

```json
{
  "name": "Mines of Moria",
  "type": "SMALL_DUNGEON",
  "cards": [cardId, cardId, cardId],
  "leadCard": leadCardId
}
```

**Response**: `Dungeon` object

### Get Dungeon by ID

**GET** `/dungeon/{player}/{id}`

**Response**: `Dungeon` object

### Get All Dungeons

**GET** `/dungeon/{player}`

**Response**: List of `Dungeon` objects

---

## Games

### Create Game

**POST** `/game/{player}/create`
**Body**:

```json
{
  "initialCollection": ["Aragorn", "Gandalf", "Legolas", "Gimli"]
}
```

**Response**: `GameEntity` object

### Set Deck

**POST** `/game/{gameId}/deck`
**Body**:

```json
{
  "cardNames": ["Aragorn", "Gandalf", "Legolas", "Gimli"]
}
```

**Response**: `Void` (Deck set successfully)

### Start Battle

**POST** `/game/{gameId}/battle/{dungeonId}`

**Response**: `BattleResult` object

```json
{
  "rounds": [...],
  "playerWon": false,
  "playerWins": 0,
  "dungeonWins": 4,
  "rewardMessage": "No reward - dungeon won"
}
```

### Upgrade Card

**POST** `/game/{gameId}/upgrade`
**Body**:

```json
{
  "cardName": "Aragorn",
  "rewardType": "DOUBLE_DAMAGE",
  "amount": 1
}
```

**Response**: `Void`

### Get Game State

**GET** `/game/{gameId}`

**Response**: `GameEntity` object

```json
{
  "id": 1,
  "player": {...},
  "collection": [...],
  "deck": [...],
  "createdAt": "2025-11-09T18:18:27.295712"
}
```

---

## Error Handling

All errors return a JSON object with a message:

```json
{
  "message": "Player not found"
}
```

* **400** – Bad Request (validation or invalid deck/dungeon)
* **404** – Not Found (player, card, dungeon, or game missing)
* **409** – Conflict (duplicate player or dungeon)
* **500** – Internal Server Error

---

## Notes

* Battles are deterministic: if player and dungeon cards tie, the dungeon wins by default.
* The API auto-creates a player if a player does not exist when creating cards, leader cards, or games.
