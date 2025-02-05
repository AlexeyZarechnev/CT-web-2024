import React, {useCallback, useRef, useState} from 'react';
import Header from '../Header/Header'
import Body from '../Body/Body'

const Register = ({users, setPage, createUser}) => {

    const loginInputRef = useRef(null)
    const nameInputRef = useRef(null)
    const [error, setError] = useState(null)


    const onRegister = useCallback(() => {
        const login = loginInputRef.current.value.trim();
        const name = nameInputRef.current.value.trim();

        if (login.length === 0) {
            setError('login could not be empty');
            return;
        }
        if (login.length < 3 || login.length > 16) {
            setError('login must be between 3 and 16 characters');
            return;
        }
        if (!login.match("[a-z]")) {
            setError('login must contain only lowercase latin letters');
            return;
        }
        if (users.find((user) => user.login === login)) {
            setError('login already exists');
            return;
        }
        if (name.length === 0) {
            setError('name could not be empty');
            return;
        }
        if (name.length < 1 || name.length > 32) {
            setError('name must be between 2 and 32 characters');
            return;
        }

        createUser({login, name});
        console.log(users);
        setPage({uri: 'enter'});
    }, [users])

    return (
        <div className="enter form-box">
            <Header title={'Register'}/>
            <Body form={{'name': 'Register', 'onSubmit': onRegister}} fields={[
                {'name': 'Login', 'ref': loginInputRef},
                {'name': 'Name', 'ref': nameInputRef}
            ]} errorState={{error, setError}}/>
        </div>
    );
};

export default Register;