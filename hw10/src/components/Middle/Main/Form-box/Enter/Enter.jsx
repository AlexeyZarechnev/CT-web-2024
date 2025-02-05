import React, {useCallback, useRef, useState} from 'react';
import Body from '../Body/Body'
import Header from '../Header/Header'

const Enter = ({users, setUser, setPage}) => {

    const loginInputRef = useRef(null)
    const passwordInputRef = useRef(null)
    const [error, setError] = useState(null)


    const onEnter = useCallback(() => {
        const login = loginInputRef.current.value
        const password = passwordInputRef.current.value

        if (login.trim().length === 0 || password.length === 0) {
            setError('Password or login could not be empty')
        }
        const loggedIn = users.find((user) => user.login === login)
        if (!loggedIn){
            setError("Wrong login or password")
        } else {
            setUser(loggedIn)
            setPage({uri: 'index'})
        }
    }, [users])

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