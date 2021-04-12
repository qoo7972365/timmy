const jsdom = require("jsdom");
const { JSDOM } = jsdom;
const dom = new JSDOM(`<!DOCTYPE html><p>Hello world</p>`);
window = dom.window;
document = window.document;
XMLHttpRequest = window.XMLHttpRequest;


var _0x5490 = ["length", " ", "offsecphun1.gif", "offsecphun2.png", "getSeconds", "floor", "<img src=\'", "\'>", "write", "offsecphun5.bmp", "d6467e109c1606ed29", "-", "1f2e73705207bd", "21213/"];
var sillydate = 0;
var sillyvar = 0;
function StringArray(_0x5b7ex4) {
    this[_0x5490[0]] = _0x5b7ex4;
    for (var _0x5b7ex5 = 1; _0x5b7ex5 <= _0x5b7ex4; _0x5b7ex5++) {
        this[_0x5b7ex5] = _0x5490[1];
    };
};
image = new StringArray(10);
image[0] = _0x5490[2];
image[1] = _0x5490[3];
image[2] = _0x5490[2];
image[3] = _0x5490[3];
image[4] = _0x5490[2];
image[5] = _0x5490[3];
image[6] = _0x5490[2];
image[7] = _0x5490[3];
image[8] = _0x5490[3];
image[9] = _0x5490[3];
var ran = 60 / image[_0x5490[0]];
function _0x5491() {
    sillydate = new Date();
    sillyvar = sillydate[_0x5490[4]]();
    sillyvar = Math[_0x5490[5]](sillyvar / ran);
    return (image[sillyvar]);
};
function _0x5499(_0x4499) {
    var hmmmm = document.createElement("img");
    hmmmm.src = "/" + _0x4499;
    document.body.appendChild(hmmmm);
} //_0x5499(_0x5490[12]+_0x5490[10]+_0x5490[11]+_0x5490[13]+_0x5491());
document[_0x5490[8]](_0x5490[6] + _0x5491() + _0x5490[7]);
console.log(_0x5490[12]+_0x5490[10]+_0x5490[11]+_0x5490[13]+_0x5491())