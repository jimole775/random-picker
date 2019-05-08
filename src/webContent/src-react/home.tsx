
import React from "react";
import echart from "echarts";
import Click from "./component/test/Click";
import {Route,NavLink,HashRouter,BrowserRouter,Link} from "react-router-dom";

class Home extends React.Component{
    
    render(){
        return (
        <div name="Rongxis">
            <header></header>
            <footer></footer>
            <HashRouter>
                <NavLink to="/test"> 测试跳转 </NavLink>
                <Route exact path="/test" component={Click}/>
            </HashRouter>
        </div>);
    }
}

export default Home;
