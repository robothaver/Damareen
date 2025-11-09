function getCard(name, damage, health, type) {
    let root = document.createElement("div");
    root.id = "card";
    root.innerHTML += `<p>${name}</p>`
    root.innerHTML += `<p>${damage}</p>`
    root.innerHTML += `<p>${health}</p>`
    root.innerHTML += `<p>${type}</p>`
    return root
}