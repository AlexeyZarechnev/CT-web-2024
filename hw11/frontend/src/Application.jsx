import './App.css';
import React from "react";
import Middle from "./components/Middle/Middle";
import Footer from "./components/Footer/Footer";
import Header from "./components/Header/Header";

function Application({login, setLogin, page, posts}) {

    return (
        <div>
            <Header setLogin={setLogin} login={login}/>
            <Middle
                posts={posts}
                page={page}
            />
            <Footer/>
        </div>
    );
}

export default Application;
