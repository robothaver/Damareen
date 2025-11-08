#!/bin/bash
# Damareen API Test Examples

# 1. Játékos létrehozása
curl -X POST "http://localhost:8080/api/player/create?playerName=TestPlayer"

# 2. Világ lekérdezése (ID: 1 az auto-generált demo világ)
curl -X GET "http://localhost:8080/api/world/1"

# 3. Új játék létrehozása
curl -X POST "http://localhost:8080/api/game/create" \
  -H "Content-Type: application/json" \
  -d '{
    "playerName": "TestPlayer",
    "worldId": 1,
    "initialCollection": ["Aragorn", "Gandalf", "Legolas", "Gimli"]
  }'

# Eredmény: { "id": 1, ... }

# 4. Pakli összeállítása (Simple Encounter - 1 kártya)
curl -X POST "http://localhost:8080/api/game/1/deck" \
  -H "Content-Type: application/json" \
  -d '{
    "cardNames": ["Aragorn"]
  }'

# 5. Harc indítása Simple Encounter ellen (Dungeon ID: 1)
curl -X POST "http://localhost:8080/api/game/1/battle/1"

# 6. Pakli újraállítása Small Dungeon-hoz (4 kártya: 3 sima + 1 vezér)
curl -X POST "http://localhost:8080/api/game/1/deck" \
  -H "Content-Type: application/json" \
  -d '{
    "cardNames": ["Aragorn", "Gandalf", "Legolas", "Gimli"]
  }'

# 7. Harc Small Dungeon ellen (Dungeon ID: 2)
curl -X POST "http://localhost:8080/api/game/1/battle/2"

# 8. Új kártya hozzáadása a világhoz
curl -X POST "http://localhost:8080/api/world/1/card" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Boromir",
    "damage": 6,
    "health": 5,
    "cardType": "FIRE"
  }'

# 9. Vezérkártya létrehozása
curl -X POST "http://localhost:8080/api/world/1/leader" \
  -H "Content-Type: application/json" \
  -d '{
    "baseCardName": "Boromir",
    "leaderName": "Dark Boromir",
    "derivation": "DOUBLE_DAMAGE"
  }'

# 10. Új kazamata létrehozása
# Először lekérdezzük a kártyák ID-ját
curl -X GET "http://localhost:8080/api/world/1"

# Majd létrehozzuk a kazamatát (példa ID-kkal)
curl -X POST "http://localhost:8080/api/dungeon/create" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "The Black Gate",
    "type": "LARGE_DUNGEON",
    "cards": [1, 2, 3, 4, 5],
    "leadCard": 9
  }'

# 11. Játék állapot lekérdezése
curl -X GET "http://localhost:8080/api/game/1"

# 12. Kártya manuális fejlesztése
curl -X POST "http://localhost:8080/api/game/1/upgrade" \
  -H "Content-Type: application/json" \
  -d '{
    "cardName": "Aragorn",
    "rewardType": "BONUS_DAMAGE",
    "amount": 5
  }'

# TELJES JÁTÉKMENET PÉLDA:
echo "=== TELJES JÁTÉKMENET ==="

# 1. Játékos
curl -X POST "http://localhost:8080/api/player/create?playerName=Frodo"

# 2. Játék indítása
GAME=$(curl -s -X POST "http://localhost:8080/api/game/create" \
  -H "Content-Type: application/json" \
  -d '{
    "playerName": "Frodo",
    "worldId": 1,
    "initialCollection": ["Aragorn", "Gandalf", "Legolas", "Gimli", "Frodo", "Sam"]
  }')

echo "Game created: $GAME"

# 3. Simple Encounter
curl -X POST "http://localhost:8080/api/game/1/deck" \
  -H "Content-Type: application/json" \
  -d '{"cardNames": ["Aragorn"]}'

BATTLE1=$(curl -s -X POST "http://localhost:8080/api/game/1/battle/1")
echo "Battle 1 result: $BATTLE1"

# 4. Small Dungeon
curl -X POST "http://localhost:8080/api/game/1/deck" \
  -H "Content-Type: application/json" \
  -d '{"cardNames": ["Aragorn", "Gandalf", "Legolas", "Gimli"]}'

BATTLE2=$(curl -s -X POST "http://localhost:8080/api/game/1/battle/2")
echo "Battle 2 result: $BATTLE2"

# 5. Large Dungeon
curl -X POST "http://localhost:8080/api/game/1/deck" \
  -H "Content-Type: application/json" \
  -d '{"cardNames": ["Aragorn", "Gandalf", "Legolas", "Gimli", "Frodo", "Sam"]}'

BATTLE3=$(curl -s -X POST "http://localhost:8080/api/game/1/battle/3")
echo "Battle 3 result: $BATTLE3"

# 6. Végső állapot
FINAL=$(curl -s -X GET "http://localhost:8080/api/game/1")
echo "Final game state: $FINAL"