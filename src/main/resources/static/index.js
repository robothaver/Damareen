function start() {
    if (localStorage.getItem("userName") === null) {
        let startScreen = getStartScreen();
        document.querySelector("body").appendChild(startScreen);
    }
}

start();