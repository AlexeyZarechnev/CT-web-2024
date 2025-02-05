import React, {useCallback, useRef, useState} from 'react';
import {useNavigate} from "react-router-dom";
import Header from '../Header/Header'
import Body from '../Body/Body'
import axios from 'axios';

const Register = ({setLogin}) => {

    const loginInputRef = useRef(null)
    const passwordInputRef = useRef(null)
    const passwordConfirmInputRef = useRef(null)
    const [error, setError] = useState(null)

    const router = useNavigate()


    const onRegister = useCallback(() => {
        const login = loginInputRef.current.value.trim();
        const password = passwordInputRef.current.value.trim();
        const passwordConfirm = passwordConfirmInputRef.current.value.trim();

        if (password !== passwordConfirm){
            setError('Passwords do not match')
            return
        }

        axios.post('/api/users', {
            login,
            password
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
            <Header title={'Register'}/>
            <Body form={{'name': 'Register', 'onSubmit': onRegister}} fields={[
                {'name': 'Login', 'ref': loginInputRef},
                {'name': 'Password', 'ref': passwordInputRef},
                {'name': 'Confirm password', 'ref': passwordConfirmInputRef}
            ]} errorState={{error, setError}}/>
        </div>
    );
};

export default Register;