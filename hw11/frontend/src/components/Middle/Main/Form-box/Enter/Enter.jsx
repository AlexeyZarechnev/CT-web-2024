import React, {useCallback, useRef, useState} from 'react';
import axios from "axios";
import {useNavigate} from "react-router-dom";
import Header from "../Header/Header";
import Body from "../Body/Body";

const Enter = ({ setLogin}) => {

    const loginInputRef = useRef(null)
    const passwordInputRef = useRef(null)
    const [error, setError] = useState(null)

    const router = useNavigate()

    const onEnter = useCallback(() => {
        const login = loginInputRef.current.value
        const password = passwordInputRef.current.value

        if (login.trim().length === 0 || password.length === 0) {
            setError('Password or login could not be empty')
            return
        }
        axios.post("/api/jwt", {
            login: login,
            password: password
        }).then((response)=>{
            const jwt = response.data
            localStorage.setItem("jwt", jwt)
            axios.get("/api/jwt", {
                params: {
                    jwt: jwt
                }
            }).then((response)=>{
                setLogin(response.data.login)
                router("/");
            }).catch((error)=>{
                console.log(error)
            })
        }).catch((error)=>{
            setError(error.response.data)
        })

    }, [])
    
    return (
        <div className="enter form-box">
            <Header title={'Enter'}/>
            <Body form={{'name': 'Enter', 'onSubmit': onEnter}} fields={[
                {'name': 'Login', 'ref': loginInputRef},
                {'name': 'Password', 'ref': passwordInputRef}
            ]} errorState={{error, setError}}/>
        </div>
    );
};

export default Enter;