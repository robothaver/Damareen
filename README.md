# Damareen API

A simple card-based dungeon battle game API. Players can create cards, leader cards, dungeons, and games, then battle through dungeons using their deck.

## Base URL

```
http://localhost:8080/api
```

---

## Players

### Create Player

**POST** `/player/create`
**Parameters**:

| Parameter  | Type   | Description                          |
| ---------- | ------ | ------------------------------------ |
| playerName | String | Name of the player (cannot be blank) |

**Response**: `Long` (Player ID)

**Example:**

```bash
POST /api/player/create?playerName=Hero
```

### Get Player

**GET** `/player/{playerName}`

**Response**: `Player` object

```json
{
  "id": 1,
  "userName": "Hero",
  "createdAt": "2025-11-09T18:18:27.077115"
}
```

---

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
  "cards": [1, 2, 3],
  "leadCard": 1
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
