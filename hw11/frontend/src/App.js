import './App.css';
import Enter from "./components/Middle/Main/Form-box/Enter/Enter";
import Register from "./components/Middle/Main/Form-box/Register/Register";
import Index from "./components/Middle/Main/Index/Index";
import React, {useEffect, useState} from "react";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Application from "./Application";
import axios from "axios";
import Users from './components/Middle/Main/Users/Users';
import NotFound from './components/Middle/Main/NotFound/NotFound';
import WritePost from './components/Middle/Main/WritePost/WritePost';
import OnePost from './components/Middle/Main/Post/OnePost/OnePost';

function App() {

    const [login, setLogin] = useState(null)
    useEffect(() => {
        if (localStorage.getItem("jwt")){
            axios.get("/api/jwt", {
                params: {
                    jwt: localStorage.getItem("jwt")
                }
            }).then((response)=>{
                localStorage.setItem("login", response.data.login);
                setLogin(response.data.login)
            }).catch((error)=>{
                console.log(error)
            })
        }
    }, []);

    const [users, setUsers] = useState(null)
    useEffect(() => {
        axios.get("/api/users", {
            params: {
                jwt: localStorage.getItem("jwt")
            }
        }).then((response)=>{
            setUsers(response.data)
        }).catch((error)=>{
            console.log(error)
        })
    }, [localStorage.getItem("jwt")]);

    const [posts, setPosts] = useState(null)
    const getPosts = () => {
        axios.get("/api/posts").then((response)=>{
            setPosts(response.data)
        }).catch((error)=>{
            console.log(error)
        })
    }
    useEffect(() => {
        getPosts()
        const timer = setInterval(getPosts, 10000)
        return () => clearInterval(timer);
    }, []);

    return (
        <div className="App">
            <BrowserRouter>
                <Routes>
                    <Route
                        index={true}
                        element={<Application login={login} setLogin={setLogin} page={<Index posts={posts}/>} posts={posts}/>}
                    />
                    <Route
                        exact path={'/enter'}
                        element={<Application login={login} setLogin={setLogin} page={<Enter setLogin={setLogin}/>} posts={posts}/>}
                    />
                    <Route
                        exact path={'/users'}
                        element={<Application login={login} setLogin={setLogin} page={<Users users={users}/>} posts={posts}/>}
                    />
                    <Route
                        exact path={'/register'}
                        element={<Application login={login} setLogin={setLogin} page={<Register setLogin={setLogin}/>} posts={posts}/>}
                    />
                    <Route
                        exact path={'/writePost'}
                        element={<Application login={login} setLogin={setLogin} page={<WritePost/>} posts={posts}/>}
                    />
                    <Route
                        path={'/posts/:id'}
                        element={<Application login={login} setLogin={setLogin} page={<OnePost posts={posts}/>} posts={posts}/>}
                    />
                    <Route
                        path={'*'}
                        element={<Application login={login} setLogin={setLogin} page={<NotFound/>} posts={posts}/>}
                    />
                </Routes>
            </BrowserRouter>
        </div>
    );
}

export default App;
