#!/usr/bin/env python3
"""
Damareen API Test Script
Requires: Python 3.6+, requests library
Install: pip install requests
"""

import requests
import json
from typing import Dict, Any

BASE_URL = "http://localhost:8080"
PLAYER = "Hero"

def print_section(title: str):
    print(f"\n{'='*50}")
    print(f"  {title}")
    print('='*50)

def print_response(response: requests.Response, description: str = ""):
    if description:
        print(f"\n{description}")

    if response.status_code >= 200 and response.status_code < 300:
        try:
            data = response.json()
            print(json.dumps(data, indent=2))
            return data
        except:
            print(response.text)
            return response.text
    else:
        print(f"ERROR {response.status_code}: {response.text}")
        return None

def main():
    print_section("Damareen API Test")

    # 1. Create Player
    print("\n1. Creating Player:", PLAYER)
    response = requests.post(f"{BASE_URL}/api/player/create", params={"playerName": PLAYER})
    player_id = print_response(response)

    # 2. Create World Cards
    print("\n2. Creating World Cards...")
    cards = {}

    card_data = [
        ("Aragorn", 5, 8, "FIRE"),
        ("Gandalf", 7, 6, "AIR"),
        ("Legolas", 6, 5, "EARTH"),
        ("Gimli", 8, 7, "WATER")
    ]

    for name, damage, health, card_type in card_data:
        print(f"\n   - {name} ({card_type}, {damage} dmg, {health} hp)")
        response = requests.post(
            f"{BASE_URL}/api/card/{PLAYER}/create",
            json={"name": name, "damage": damage, "health": health, "cardType": card_type}
        )
        card = print_response(response)
        if card:
            cards[name] = card["id"]

    # 3. Get All Cards
    print("\n3. Getting all cards for", PLAYER)
    response = requests.get(f"{BASE_URL}/api/card/{PLAYER}")
    all_cards = print_response(response)

    # 4. Create Leader Card
    print("\n4. Creating Leader Card: King Aragorn (DOUBLE_DAMAGE)")
    response = requests.post(
        f"{BASE_URL}/api/card/{PLAYER}/lead",
        json={
            "worldCardId": cards["Aragorn"],
            "name": "King Aragorn",
            "derivation": "DOUBLE_DAMAGE"
        }
    )
    leader = print_response(response)
    leader_id = leader["id"] if leader else None

    # 5. Create Dungeons
    print("\n5. Creating Dungeons...")
    dungeons = {}

    print("\n   - Orc Patrol (SIMPLE_ENCOUNTER)")
    response = requests.post(
        f"{BASE_URL}/api/dungeon/{PLAYER}/create",
        json={
            "name": "Orc Patrol",
            "type": "SIMPLE_ENCOUNTER",
            "cards": [cards["Aragorn"]],
            "leadCard": None
        }
    )
    dungeon1 = print_response(response)
    if dungeon1:
        dungeons["Orc Patrol"] = dungeon1["id"]

    print("\n   - Mines of Moria (SMALL_DUNGEON)")
    response = requests.post(
        f"{BASE_URL}/api/dungeon/{PLAYER}/create",
        json={
            "name": "Mines of Moria",
            "type": "SMALL_DUNGEON",
            "cards": [cards["Aragorn"], cards["Gandalf"], cards["Legolas"]],
            "leadCard": leader_id
        }
    )
    dungeon2 = print_response(response)
    if dungeon2:
        dungeons["Mines of Moria"] = dungeon2["id"]

    # 6. Get All Dungeons
    print("\n6. Getting all dungeons")
    response = requests.get(f"{BASE_URL}/api/dungeon/{PLAYER}")
    print_response(response)

    # 7. Create Game
    print("\n7. Creating Game")
    response = requests.post(
        f"{BASE_URL}/api/game/{PLAYER}/create",
        json={
            "initialCollection": ["Aragorn", "Gandalf", "Legolas", "Gimli"]
        }
    )
    game = print_response(response)
    game_id = game["id"] if game else None

    if not game_id:
        print("ERROR: Could not create game!")
        return

    # 8. Battle 1: Simple Encounter
    print("\n8. Setting deck for Simple Encounter (1 card)")
    response = requests.post(
        f"{BASE_URL}/api/game/{game_id}/deck",
        json={"cardNames": ["Aragorn"]}
    )
    print_response(response, "Deck set successfully")

    print("\n9. BATTLE: Simple Encounter")
    response = requests.post(f"{BASE_URL}/api/game/{game_id}/battle/{dungeons['Orc Patrol']}")
    battle1 = print_response(response)

    # 9. Battle 2: Small Dungeon
    print("\n10. Setting deck for Small Dungeon (4 cards)")
    response = requests.post(
        f"{BASE_URL}/api/game/{game_id}/deck",
        json={"cardNames": ["Aragorn", "Gandalf", "Legolas", "Gimli"]}
    )
    print_response(response, "Deck set successfully")

    print("\n11. BATTLE: Small Dungeon")
    response = requests.post(f"{BASE_URL}/api/game/{game_id}/battle/{dungeons['Mines of Moria']}")
    battle2 = print_response(response)

    # 10. Final Game State
    print("\n12. Final Game State")
    response = requests.get(f"{BASE_URL}/api/game/{game_id}")
    print_response(response)

    print_section("Tests Complete!")

    # Summary
    if battle1 and battle2:
        print("\n✅ All tests passed successfully!")
        print(f"   - Battle 1 (Simple): {'WON' if battle1.get('playerWon') else 'LOST'}")
        print(f"   - Battle 2 (Small): {'WON' if battle2.get('playerWon') else 'LOST'}")
    else:
        print("\n⚠️  Some tests failed. Check the output above.")

if __name__ == "__main__":
    try:
        main()
    except requests.exceptions.ConnectionError:
        print("\n❌ ERROR: Cannot connect to server at", BASE_URL)
        print("   Make sure the server is running: mvn spring-boot:run")
    except Exception as e:
        print(f"\n❌ ERROR: {e}")
        import traceback
        traceback.print_exc()