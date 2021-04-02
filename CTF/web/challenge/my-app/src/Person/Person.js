import React from 'react';
import './Person.js';

const decoder = (person) =>{
    var encodedStringAtoB = person;
    var decodedStringAtoB = atob(encodedStringAtoB);
    console.log(decodedStringAtoB);
}

const out = () =>{
    var person = prompt("Please enter your name", "Harry Potter");

if (person != null) {
  document.getElementById("demo").innerHTML =
  "Hello " + person + "! How are you today?";
}
}

const person = ( props ) => {
    return (
        <div>
            <p onClick = {props.click}>I'm {props.name} and I'm {props.age} years old!</p>
            <p>{props.children}</p>
            <input type = "text" onChange = {props.changed} value={props.name} />
        </div>
    )
};



export default person;