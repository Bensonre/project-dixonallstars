var isSetup = true;  // indicates ship placement phase vs gameplay phase
var placedShips = 0;
var game;
var shipType;
var vertical = 0;
var verticalButton = document;
var gg = false;  // indicates end of game

// AI sonar checks are handled in Game Object

// var is passed to attack function, indicating what type of attack is being used.
var isSonarAttackPlayer = false;

// used for knowing when sonar is first available for each player
var firstShipSunkPlayer = false;

// used to know when ability to use sonar should be revoked
var sonarsFiredPlayer = 0;



function makeGrid(table, isPlayer) {
    for (i=0; i<10; i++) {
        let row = document.createElement('tr');
        for (j=0; j<10; j++) {
            let column = document.createElement('td');
            column.addEventListener("click", cellClick);
            row.appendChild(column);
        }
        table.appendChild(row);
    }
}

function markHits(board, elementId, surrenderText) {
    board.attacks.forEach((attack) => {
        let className;
        if (attack.result === "MISS")
            className = "miss";
        else if (attack.result === "HIT")
            className = "hit";
        else if (attack.result === "SUNK"){

            // if user has sunk an opponent ship, make sonar option available
            if(firstShipSunkPlayer == false && elementId == "opponent"){
                firstShipSunkPlayer = true; // so we never open the sonar modal again
                openSonar();
            }
            className = "sunk";
        }
        else if (attack.result === "CQHIT"){
            className = "cqhit";
        }
        else if (attack.result === "SONAR_OCCUPIED"){
            className = "sonar_occupied";
        }
        else if (attack.result === "SONAR_EMPTY"){
            className = "sonar_empty";
        }
        else if (attack.result === "SURRENDER") {
            if (gg == false) { // If game over modal has never been opened before
                openGG(surrenderText); // Create game over modal with correct surrender text
                gg = true; // Set to true so we don't open game over again
            }
        }
        document.getElementById(elementId).rows[attack.location.row-1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add(className);
    });
}

function redrawGrid() {
    Array.from(document.getElementById("opponent").childNodes).forEach((row) => row.remove());
    Array.from(document.getElementById("player").childNodes).forEach((row) => row.remove());
    Array.from(document.getElementById("player_copy").childNodes).forEach((row) => row.remove());
    makeGrid(document.getElementById("opponent"), false);
    makeGrid(document.getElementById("player"), true);
    makeGrid(document.getElementById("player_copy"), true);
    if (game === undefined) {
        return;
    }

    game.playersBoard.ships.forEach((ship) => ship.occupiedSquares.forEach((square) => {
        document.getElementById("player").rows[square.row-1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("occupied");
        document.getElementById("player_copy").rows[square.row-1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("occupied");
    }));

    /* text displayed for user in error modal upon winning/losing */
    markHits(game.opponentsBoard, "opponent", "Congratulations! You Won!");
    markHits(game.playersBoard, "player", "You Lost.");
}

var oldListener;
function registerCellListener(f) {
    let el = document.getElementById("player");
    for (i=0; i<10; i++) {
        for (j=0; j<10; j++) {
            let cell = el.rows[i].cells[j];
            cell.removeEventListener("mouseover", oldListener);
            cell.removeEventListener("mouseout", oldListener);
            cell.addEventListener("mouseover", f);
            cell.addEventListener("mouseout", f);
        }
    }
    oldListener = f;
}

function cellClick() {
    let row = this.parentNode.rowIndex + 1;
    let col = String.fromCharCode(this.cellIndex + 65);
    if (isSetup) {
        sendXhr("POST", "/place", {game: game, shipType: shipType, x: row, y: col, isVertical: vertical}, function(data) {
            game = data;

            redrawGrid();
            placedShips++;
            if (placedShips == 3) {
                isSetup = false;
                registerCellListener((e) => {});
                document.getElementById("placement_mode").classList.add("inactive");
                document.getElementById("attack_mode").classList.remove("inactive");
            }
        });
        /* send sonar attack */
    } else {
        sendXhr("POST", "/attack", {game: game, x: row, y: col, Sonar: isSonarAttackPlayer}, function(data) {
            if(isSonarAttackPlayer == true){
                sonarsFiredPlayer++;

                // user has fired two sonars. remove ability to use it
                if(sonarsFiredPlayer == 2)
                    closeSonar();
            }
            isSonarAttackPlayer = false; // reset
            document.getElementById("fire-sonar-button").classList.remove("fireactive");

            game = data;
            redrawGrid();
        })
    }
}

function sendXhr(method, url, data, handler) {
    var req = new XMLHttpRequest();
    req.addEventListener("load", function(event) {
        if (req.status != 200) {
            openInv(); // Open the invalid modal
            return;
        }
        handler(JSON.parse(req.responseText));
    });
    req.open(method, url);
    req.setRequestHeader("Content-Type", "application/json");
    req.send(JSON.stringify(data));
}

// this function is code removed from place function so it could be reused other than that Reese didn't touch it
function finishPlacement(size, table, vertical){
    for (let i=0; i<size; i++) { // goes through for the length of the ship being placed
            let cell; // name for the current space on board that the board is trying to hilight
            if(vertical) { //if the ship will be placed vertically increment through the rows
                let tableRow = table.rows[row+i];
                if (tableRow === undefined) {
                    // ship is over the edge; let the back end deal with it
                    break;
                }
                cell = tableRow.cells[col];
            } else { //if the ship isn't vertical increment through the columns
                cell = table.rows[row].cells[col+i];
            }
            if (cell === undefined) {
                // ship is over the edge; let the back end deal with it
                break;
            }
            cell.classList.toggle("placed"); // add class placed to cell so it will show up with color on the board!
    }
}

//this is not a function I wrote but comments were asked for it so i will do my best
function place(size) {
    return function() {
        let row = this.parentNode.rowIndex; // I believe these lines get the info from the cell clicked so ships will be placed in the right location
        let col = this.cellIndex;

        // this receives info on weather or not the play check the box for vertical
        // *** it will need to be changed for the arrow keys to work ****
        // dont think we need this line anymore
        //vertical = document.getElementById("is_vertical").checked;
        let table = document.getElementById("player"); // this makes sure the right table is selected for ship placement
        let table2 = document.getElementById("player_copy"); // i added this so player copy will be able to have the ships placed just like player

        // finish placement is not a function I wrote, the lines of code it contains used to be a part of place function
        // but I needed it to place for both player and player copy so instead of just copying all the code I took what
        // was already there and put it into a function so I could reuse the code.
        finishPlacement(size,table, vertical);
        finishPlacement(size,table2, vertical);

    }
}

function initGame() {
    makeGrid(document.getElementById("player_copy"), true);
    makeGrid(document.getElementById("opponent"), false);
    makeGrid(document.getElementById("player"), true);

    /* let player choose ship, but start with Battleship initially to make it more
    intuitive and user-friendly */
    shipType = "BATTLESHIP";

    /* each function initially removes highlighting of ship select ships, then adds the
       highlighting to the active ship */
    document.getElementById("place_minesweeper").addEventListener("click", function(e) {
       shipType = "MINESWEEPER";
       registerCellListener(place(2));
       document.getElementsByClassName("btnactive")[0].classList.remove("btnactive");
       this.classList.add("btnactive");
    });

    document.getElementById("place_destroyer").addEventListener("click", function(e) {
       shipType = "DESTROYER";
       registerCellListener(place(3));
       document.getElementsByClassName("btnactive")[0].classList.remove("btnactive");
       this.classList.add("btnactive");
    });

    document.getElementById("place_battleship").addEventListener("click", function(e) {
       shipType = "BATTLESHIP";
       registerCellListener(place(4));
       document.getElementsByClassName("btnactive")[0].classList.remove("btnactive");
       this.classList.add("btnactive");
    });

    sendXhr("GET", "/game", {}, function(data) {
        game = data;
    });
};

// open invalid move modal
function openInv(){
	document.getElementById("modal-backdrop-inv").classList.remove("inactive");
	document.getElementById("modal-inv").classList.remove("inactive");
}

// closes invalid move modal
function closeInv(){
	document.getElementById("modal-backdrop-inv").classList.add("inactive");
	document.getElementById("modal-inv").classList.add("inactive");
}

// close invalid move modal when one of the buttons is clicked
document.getElementsByClassName("modal-close-button-inv")[0].addEventListener("click", closeInv);
document.getElementsByClassName("modal-okay-button-inv")[0].addEventListener("click", closeInv);

// open game over modal
function openGG(surrenderText){
	document.getElementById("modal-backdrop-gg").classList.remove("inactive");
	document.getElementById("modal-gg").classList.remove("inactive");
	document.getElementById("surrenderText").textContent = surrenderText;
}

/* ===============    SONAR    ================================================================ */

// open sonar modal. happens when first ship has been sunk.
function openSonar(){
	document.getElementById("modal-sonar").classList.remove("inactive");
}

// close sonar modal. happens when two sonars have been fired.
function closeSonar(){
	document.getElementById("modal-sonar").classList.add("inactive");
}

function fireSonar(){
    // toggle
    if(isSonarAttackPlayer == true){
        isSonarAttackPlayer = false;
        document.getElementById("fire-sonar-button").classList.remove("fireactive");
    }
    else{
        isSonarAttackPlayer = true;
        document.getElementById("fire-sonar-button").classList.add("fireactive");
    }
}

// fire sonar when user clicks button
document.getElementById("fire-sonar-button").addEventListener("click", fireSonar);



/* ===============    ARROW INDICATOR    ======================================================= */

function checkBox(){
    if(vertical == 1) {
        vertical = 0;
        document.getElementById("arrow_vert").classList.add("inactive");
        document.getElementById("arrow_horizontal").classList.remove("inactive");
    }
    else{
        vertical = 1;
        document.getElementById("arrow_vert").classList.remove("inactive");
        document.getElementById("arrow_horizontal").classList.add("inactive");
    }
}

   verticalButton.addEventListener('keydown', function(e) {
        var key = e.keyCode;
        if(key === 37 || key === 39){
               checkBox();
        }
    });
  // verticalButton.addEventListener('39',checkBox);


/* ===============    GAME OVER MODAL    ====================================================== */
/* currently commented out since we want the game over modal to terminate the game.
   leaving code in for potential future use */

/*//closes game over modal
function closeGG(){
	document.getElementById("modal-backdrop-gg").classList.add("inactive");
	document.getElementById("modal-gg").classList.add("inactive");
}*/

/*
// closes game over modal when one of the buttons is clicked
document.getElementsByClassName("modal-close-button-gg")[0].addEventListener("click",closeGG);
document.getElementsByClassName("modal-okay-button-gg")[0].addEventListener("click",closeGG);*/
