let userName;

function getStartScreen() {
    let div = document.createElement("div");
    div.id = "start_screen"
    let inputElement = document.createElement("input");
    inputElement.type = "text";
    div.appendChild(inputElement);
    let button = document.createElement("input");
    button.type = "button";
    button.value = "Next"
    button.onclick = () => {
        let name = inputElement.value;
        if (name.length <= 16 && name.length > 3) {
            userName = name;
            fetchDungeons()
        }
    }
    div.appendChild(button);
    return div;
}

async function fetchDungeons() {
    let response = await fetch(`api/dungeon/${userName}`);
    let dungeons = await response.json();
    for (let dungeon of dungeons) {
        console.log(dungeon)
    }
}