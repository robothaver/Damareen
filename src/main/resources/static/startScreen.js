let userName;

function getStartScreen(onUserNameRetried) {
    let div = document.createElement("div");
    div.id = "start_screen"

    let title = document.createElement("h1");
    title.innerText = "Welcome to Damareen!";
    let welcomeText = document.createElement("h2");
    welcomeText.innerText = "Please select your username";
    div.appendChild(title)
    div.appendChild(welcomeText)

    let inputElement = document.createElement("input");
    inputElement.type = "text";
    inputElement.id = "username_field"
    div.appendChild(inputElement);
    let button = document.createElement("input");
    button.type = "button";
    button.value = "Next"
    button.id = "start_btn"
    button.onclick = () => {
        let name = inputElement.value;
        if (name.length <= 16 && name.length > 3) {
            userName = name;
            onUserNameRetried();
        }
    }
    div.appendChild(button);
    return div;
}