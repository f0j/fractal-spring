// _url set above

const DELIM = ",";
const BACKGROUND = "#444444";
const HEADER_FOOTER_HEIGHT = 100;

const _scene = document.getElementById("scene");
const _scene2d = _scene && _scene.getContext("2d", { alpha: false }); // { alpha: false } turns off the transparency to improve paint consistency and performance
const _move = document.getElementById("move");
const _origCursor = _scene && _scene.style.cursor;
let _subscribed = false;
let _stream = false;
let _progress = 0;
let _minRe = null;
let _minIm = null;
let _maxRe = null;
let _maxIm = null;
let _zoomed = null;

function generate(state) {
    if (_subscribed) {
        stopStream();
    }

    setCanvasDimensions();
    _progress = 0;
    setProgress();

    _stream = new WebSocket(_url);
    _stream.onopen = function() {
        _subscribed = true;
        console.log("_stream.onopen", _stream);
        _stream.send(_scene.width + DELIM + _scene.height + DELIM + state);
    }

    _stream.onclose = function(msg) {
        _subscribed = false;
        console.log("_stream.onclose", _stream, msg);
    }

    _stream.onerror = function(msg) {
        _subscribed = false;
        console.log("_stream.onerror", _stream, msg);
    }

    _stream.onmessage = function(msg) {
        // console.log("_stream.onmessage", _stream, msg);
        // console.log(msg.data);

        // FORMAT: ,<message>
        if (msg.data[0] === DELIM) {
            if (msg.data.includes('state')) {
                // FORMAT ,state,<re>,<im>
                const state = msg.data.split(DELIM);
                _minRe = state[2];
                _minIm = state[3];
                _maxRe = state[4];
                _maxIm = state[5];
                _zoomed = state[6];
                setOrig();
                setZoomed();
            }

            console.log(msg.data);
            return;
        }

        // FORMAT: xpx,ypx,r10,g10,b10,[every 5]...
        const pixels = msg.data.split(DELIM);

        // if (pixels[0] === "0") {
        //     console.log(msg.data);
        // }

        let count = 0;
        for (let i = 0; i < pixels.length; i = i + 5) {
            const xpx = parseInt(pixels[i]);
            // Last split is always ','
            if (xpx >= 0 && xpx < _scene.width) { // || xpx === 0 // _scene.width - 1
                const ypx = parseInt(pixels[i + 1]);
                const red = parseInt(pixels[i + 2]);
                const green = parseInt(pixels[i + 3]);
                const blue = parseInt(pixels[i + 4]);
                const colour = '#' + red.toString(16) + green.toString(16) + blue.toString(16);

                // if (ypx === 0) {
                //     console.info(i + '=' + xpx + '/' + ypx + '/' + colour);
                // }

                // requestAnimationFrame(() => {
                _scene2d.fillStyle = colour;
                _scene2d.fillRect(xpx, ypx, 1, 1);
                // });

                count++;
            }
        }

        _progress += count;
        setProgress();
    }
}

function stopStream() {
    if (!_subscribed) {
        console.log("Already closed ... returning early");
        return;
    }

    if (!_stream) {
        console.log("Never started or something happened ... returning early")
        return;
    }

    _stream.send(",close");
    _stream.close();
    _subscribed = false;
}

function setCanvasDimensions() {
    if (_scene) {
        const width = window.innerWidth;
        const height = window.innerHeight - HEADER_FOOTER_HEIGHT;

        // Resizing the canvas will clear it so only resize when there is a change of canvas size to reduce flicker effect.
        if (_scene.width !== width || _scene.height !== height) {
            _scene.width = window.innerWidth;
            _scene.height = window.innerHeight - HEADER_FOOTER_HEIGHT;

            // When resizing, set the canvas to a dark grey instead of the default black when transparency is turned off
            _scene2d.fillStyle = BACKGROUND;
            _scene2d.fillRect(0, 0, _scene.width, _scene.height);
        }
    }
}

setCanvasDimensions();

function setProgress() {
    const progO = document.getElementById("progress");
    if (progO) {
        const total = _scene.width * _scene.height;
        progO.innerText = _progress + " / " + (_scene.width * _scene.height);
        if (_progress >= total) {
            console.info("Received all data, closing ws ...");
            stopStream();
        }
    }
}

setProgress();

function setOrig() {
    const origO = document.getElementById("orig");
    if (origO) {
        if (!_minRe || !_minIm || !_maxRe || !_maxIm) {
            origO.innerText = "-+-i to -+-i";
        } else {
            origO.innerText = _minRe + "+" + _minIm + "i to " + _maxRe + "+" + _maxIm + "i";
        }
    }
}

setOrig();

function toggleMoveCursor() {
    if (_move) {
        if (_move.checked) {
            _scene.style.cursor = "crosshair"; // grabbing
        } else if (!_move.checked) {
            _scene.style.cursor = _origCursor;
        }
    }
}

toggleMoveCursor();

function sceneMouseDown(event) {
    if (_move.checked) {
        const moveX = event.clientX - event.target.offsetLeft;
        const moveY = event.clientY - event.target.offsetTop;
        const middleX = _scene.width / 2;
        const middleY = _scene.height / 2;
        // console.log("moveX=" + moveX + ", moveY=" + moveY + ", middleX=" + middleX + ", middleY=" + middleY);

        const state = "move" + DELIM +
            // Server expects integers
            Math.floor(moveX - middleX) + DELIM +
            Math.floor(moveY - middleY) + DELIM +
            _minRe + DELIM +
            _minIm + DELIM +
            _zoomed;

        generate(state);
    }
}

_scene && _scene.addEventListener('mousedown', function(event) { sceneMouseDown(event); })

function setZoomed() {
    const zoomedO = document.getElementById("zoomed");
    if (zoomedO) {
        if (!_zoomed) {
            _zoomed = 1.0;
        }
        zoomedO.innerText = _zoomed + "x";
    }
}

setZoomed();

function twoZoomIn() {
    const state = "two-zoom-in" + DELIM +
        _minRe + DELIM +
        _minIm + DELIM +
        _zoomed;

    generate(state);
}

function twoZoomOut() {
    const state = "two-zoom-out" + DELIM +
        _minRe + DELIM +
        _minIm + DELIM +
        _zoomed;

    generate(state);
}