function start() {
    let body = document.querySelector("body");
    if (localStorage.getItem("userName") === null) {
        let startScreen = getStartScreen(() => {
            body.removeChild(startScreen)
            let homeScreen = getHomeScreen();
            body.appendChild(homeScreen);
        });
        body.appendChild(startScreen);
    }
}

start();