const recordButton = document.getElementById('rec');
const inleveringButton = document.getElementById('inlevering');
const stopButton = document.getElementById('stop');
let mic, recorder, soundFile;



function setup(){
    cnv = createCanvas(200,200);
    cnv.position(recordButton.offsetLeft, recordButton.offsetTop);
    cnv.hide();
    stopButton.style.display = 'none';
    mic = new p5.AudioIn();

    // prompts user to enable their browser mic
    mic.start();

    // create a sound recorder
    recorder = new p5.SoundRecorder();

    // connect the mic to the recorder
    recorder.setInput(mic);

    // this sound file will be used to
    // playback & save the recording
    soundFile = new p5.SoundFile();
}

function draw(){
    background(0.1);
}

recordButton.addEventListener('click', () =>{
    recordButton.style.display = 'none';
    cnv.show();
    stopButton.style.display = 'block';
    stopButton.style.marginLeft = 210 + 'px';
    inleveringButton.style.marginTop = 170 + 'px';
    userStartAudio();
    if (mic.enabled){
        recorder.record(soundFile);
    }
});

stopButton.addEventListener('click', ()=>{
    recorder.stop();
    stopButton.style.display = 'none';
})