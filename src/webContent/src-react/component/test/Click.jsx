
import React from "react";


class Click extends React.Component{
    constructor(props){
        super(props); console.log(props); //props的值来自元素的自定义属性
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