function getHomeScreen() {
    let root = document.createElement("div");
    root.id = "home_screen";

    let worldCardTitle = document.createElement("h2");
    worldCardTitle.innerText = "World cards"
    let worldCards = document.createElement("div");
    worldCards.id = "world_cards"
    root.appendChild(worldCardTitle)
    root.appendChild(worldCards)

    let leadCardsTitle = document.createElement("h2");
    leadCardsTitle.innerText = "Lead cards"
    let leadCards = document.createElement("div");
    leadCards.id = "lead_cards"
    root.appendChild(leadCardsTitle)
    root.appendChild(leadCards)

    let dungeonsTitle = document.createElement("h2");
    dungeonsTitle.innerText = "Dungeons"
    let dungeons = document.createElement("div");
    dungeons.id = "dungeons"
    root.appendChild(dungeonsTitle)
    root.appendChild(dungeons)
    getDungeons();
    return root;
}

async function getDungeons() {
    let response = await fetch(`api/dungeon/${userName}`);
    let dungeons = await response.json();
    for (let dungeon of dungeons) {
        document.getElementById("dungeons").appendChild(getDungeon(dungeon.name, dungeon.type, dungeon.worldCards, dungeon.leadCard));
    }
}