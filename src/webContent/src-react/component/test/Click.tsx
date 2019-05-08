
import React from "react";
interface Click {
    state:any;
    timerID:any;
    setState:Function
}

class Click extends React.Component{
    constructor(props){
        super(props);
        this.state = {date:new Date()}
    }
    componentDidMount() {
        this.timerID = setInterval(
            () => this.tick(),
            1000
          );
    }
    componentWillUnmount() {
  
    }
    tick() {
        this.setState({
          date: new Date()
        });
      }
    render(props){
        return (
            <div>
                <span>{this.state.date.toUTCString()}</span>
            </div>
        );
    }
}

export default Click;