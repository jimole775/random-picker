
import React from "react";
import Click from "./component/test/Click";
import Relative from "./component/test/testRelativeComponent";
import {Route,NavLink,HashRouter,BrowserRouter,Link} from "react-router-dom";

class Home extends React.Component{
    constructor(props){
        super(props);
        console.log(props);
    }
    render(){
        return (
            <div name="Rongxis">
                <header></header>
                <footer></footer>
                <HashRouter>
                    <Relative/>
                    <input type="text" ref={(input)=>{this.input = input}}/>
                    <NavLink to="/test"> 测试跳转 </NavLink>
                    <Route exact path="/test" component={Click}/>
                </HashRouter>
            </div>
        );
    }
}

export default Home;
