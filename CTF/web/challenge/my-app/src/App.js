import React, { Component } from 'react';
import './App.css';
import Person from './Person/Person';

class App extends Component {
  state = {
    persons: [
      { name:'Max' , age : 28},
      { name:'Black' , age : 30}
    ]
  }

  switchNameHandler = (newName) => {
    console.log('Was Clicked');
    this.setState({
      persons: [
        { name: newName , age : 28},
        { name:'Black' , age : 30}
    ]
  })
  }
  
  decoder = () => {
    var person = prompt("Please enter your name", "Harry Potter");
if (person != null) {
  console.log("Hello " + person + "! How are you today?");
    var encodedStringAtoB = person;
    var decodedStringAtoB = atob(encodedStringAtoB);
    console.log(decodedStringAtoB);
  }
}

  nameChangedHandler = (event)=> {
    this.setState({
      persons: [
        { name: 'Max' , age : 28},
        { name: event.target.value, age : 30}
    ]
  })

  } 

  render() {
    return (
      <div className="App">
        <h1>Hi, I'm a react app</h1>
        <button onClick={this.switchNameHandler.bind(this, 'Inspect person.js')}>Switch Name</button>
        <Person 
          name={this.state.persons[0].name} 
          age={this.state.persons[0].age} />
        <Person 
          name={this.state.persons[1].name} 
          age={this.state.persons[1].age}
          click={this.switchNameHandler.bind(this, 'inspect an INDEX')}
          changed={this.nameChangedHandler} > My hobbies: Racing</Person>
          <button onClick={this.decoder.bind(this, 'hahahaha')}>Switch Name</button>
          
      </div>
    );
  }
}

export default App;