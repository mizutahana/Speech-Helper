if (!('webkitSpeechRecognition' in window)) {
    upgrade();
} else {
    var recognition = new webkitSpeechRecognition();
    recognition.continuous = true;
    recognition.interimResults = true;
    recognition.lang = "en-US";
    recognition.maxAlternatives = 1;
    var noteContent = "";
    var isRecording = false;

    function toggleRec() {
        if (isRecording) {
            stopRec();
        } else {
            startRec();
        }
    }

    function startRec() {
        recognition.start();
    }

    recognition.onstart = function() {
        $("#textbox").text('Voice recognition activated. Try speaking into the microphone.');
        $("#speaker").css("color", "grey");
        isRecording = true;
    }

    recognition.onerror = function(event) {
        if (event.error == 'no-speech') {
            $("#textbox").text('No speech was detected. Try again.');
        };
        recognition.stop();
    }

    function stopRec() {
        speechEnd();
        recognition.stop();
    }

    function speechEnd() {
        isRecording = false;
        $("#speaker").css("color", "black");
    }
    recognition.onresult = function(event) {
        var current = event.resultIndex;
        var transcript = event.results[current][0].transcript; // take out what we need
        noteContent = transcript;
        $("#textbox").text(noteContent);
        $("#textbox").css("color", "black");
    }

    function readRec() {
        if (!isRecording) {
            readOutLoud(noteContent);
        }
    }
}

function upgrade() {
    $("#textbox").innerHTML = "Please upgrade your web server"; 
}

function readOutLoud(message) {
    var speech = new SpeechSynthesisUtterance();

    speech.text = message;
    speech.volume = 1;
    speech.rate = 1;
    speech.pitch = 1.5;

    window.speechSynthesis.speak(speech);
}