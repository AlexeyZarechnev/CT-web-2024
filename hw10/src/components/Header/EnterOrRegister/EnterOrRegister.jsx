import React from 'react';

const EnterOrRegister = ({user, setUser, setPage}) => {

    return (
        <div className="enter-or-register-box">
            {user
                ?
                <>
                    <a href="#">
                        {user.login}
                        ({user.name})
                    </a>/
                    <a href="#" onClick={(event) => {
                        setUser(null);
                        event.preventDefault();
                    }}>
                        Logout
                    </a>
                </>
                :
                <>
                    <a href="" onClick={(event) => {
                        setPage({uri: 'enter'});
                        event.preventDefault();
                    }}>Enter</a>/
                    <a href="" onClick={(event) => {
                        setPage({uri: 'register'});
                        event.preventDefault();
                    }}>Register</a>
                </>
            }
        </div>
    );
};

export default EnterOrRegister;