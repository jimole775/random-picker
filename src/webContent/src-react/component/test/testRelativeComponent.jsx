
import React from "react";
import HighOrderComponent from "./HighComponent";

@HighOrderComponent
class Son extends React.Component{
    constructor(props){
        super(props);
        this.props = props;
    }
    render(){
         return (
             <input type="button" value="点击" onClick={(e) => this.handleClick(e)}/> 
         )
    }

    handleClick(e){
        console.log(this.props);
        this.props.emitEvent("son");
    }

}

@HighOrderComponent
class Father extends React.Component{
    constructor(){
        super();
        this.state = {
            title:"father page",
            name:"father",
            getData:(data) => {
                console.log("i'm been changed!",this);
                
                this.setState({name:"son"})

            },
            comps:[]
        };
    }
    handleClick(){

        const {comps} = this.state;
        
        import("./Click").then((newComp) => {
            comps.push(newComp.default);
            this.setState({
                name : "son1",
                comps:comps
            })
        });               
    }
    render(){
        // const NewSon = HC(Son);
        return (
            <div>
                <Son emitEvent={this.state.getData}/>
                <span >{this.state.name}</span>

                <div>
                    {
                        this.state.comps.map((Comp,index)=>{
                            return <Comp key={index}/>
                        })
                    }
                </div>
                <input type="button" value="手动更改" onClick={(e) => this.handleClick(e)}/>   
            </div>
        )
    }
}


export default Father;