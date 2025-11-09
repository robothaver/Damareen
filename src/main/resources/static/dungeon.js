function getDungeon(name, type, cards, leadCard) {
    let root = document.createElement("div");

    root.innerHTML += `<h3>${name}</h3>`;
    let cardContainer = document.createElement("div");
    cardContainer.className = "card_con"
    for (let card of cards) {
        cardContainer.appendChild(getCard(card.cardName, card.damage, card.health, card.cardType))
    }
    root.appendChild(cardContainer);
    return root;
}