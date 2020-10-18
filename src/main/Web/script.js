var socket;
var size;
var waiting = true;

function generateBoard(data, size) {
    var arr = data.split('');
    var board = $('#board');
    board.css('width', (size*40)+'px');
    board.html('');
    for(var y = 0; y < size; y++){
        board.append('<div class="row">');
        var row = $('.row:last-child');
        for(var x = 0; x < size; x++){
            if(arr[x*size+y] === 'E') row.append('<div class="field" onclick="putStone('+x+', '+y+')"></div>');
            else if (arr[x*size+y] === 'W') row.append('<div class="field white""></div>');
            else if (arr[x*size+y] === 'B') row.append('<div class="field black""></div>');
        }
    }
}

function startGame(opponent) {
    initGame(Number($('input[name=boardSize]:checked').val()), opponent);
}

function initGame(s, type) {
    $('#gameInit').hide();
    size = s;
    socket = new WebSocket('ws://localhost:8080');
    socket.addEventListener('open', function (event) {
        socket.send(type+'!'+size);
    });

    socket.addEventListener('message', responseHandler);
    generateBoard('EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE', 4);
}

function responseHandler(msg) {
    var data = msg.data.split('!');
    if(data[0] === "WRONGMOVE") popup("Wrong move: "+data[1]);
    else if(data[0] === "BLACK"){
        $('#board').css('border-color', 'black');
        socket.send('WAITING');
    }
    else if(data[0] === "WHITE"){
        $('#board').css('border-color', 'white');
        socket.send('WAITING');
    }
    else if(data[0] === "HEY"){
        $('.waitingDiv').show();
    }
    else if(data[0] === "ENDGAME"){
        endGame(data[1], data[2], data[3]);
    }
    else{
        if(waiting === true){
            waiting = false
            $('.waitingDiv').hide();
        }
        else {
            waiting = true
            $('.waitingDiv').show();
        }
        generateBoard(data[1], size);
    }
}

function putStone(x, y) {
    if(waiting === false) {
        socket.send("PUTSTONE " + x + " " + y);
    }
}

function pass() {
    if(waiting === false) {
        socket.send("PASS");
    }
}

function giveUp() {
    if(waiting === false) {
        socket.send("GIVEUP");
    }
}

function popup(msg) {
    $('body').append('<div class="popup"> <p>'+msg+' </p> <input type="button" value="OK"></div>');
    $('.popup input').click(function (e) {
       $(this).parent().remove();
    });
}

function endGame(r, y, e) {
    socket.close();
    waiting = true;
    $('.waitingDiv').hide();
    $('#gameInit').show();
    generateBoard('EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE', 4);
    if(r === "CONNECTION") popup("Connection trouble");
    else if(r === "GIVEUP"){
        if(Number(y)>1) popup("Your opponent is coward");
        else popup("You are coward");
    }
    else if(r === "PASS"){
        if (Number(y) > Number(e)) popup("Victory ( "+y+" - "+e+" )");
        else if(Number(y) < Number(e)) popup("You lost ( "+y+" - "+e+" )");
        else popup("Draw ( "+y+":"+e+" )");
    }
}