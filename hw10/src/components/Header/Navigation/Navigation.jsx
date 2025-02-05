import React from 'react';

const Navigation = ({user, setPage}) => {
    return (
        <nav>
            <ul>
                <li>
                    <a href="" onClick={(event) => {
                        event.preventDefault()
                        setPage({uri: 'index'});
                    }}>Home</a>
                </li>
                <li><a href="" onClick={(event) => {
                        event.preventDefault()
                        setPage({uri: 'users'});
                    }}>Users</a></li>
                {user
                    ?
                    <li>
                        <a href="" onClick={(event)=>{
                            event.preventDefault()
                            setPage({uri: 'writePost'});
                        }}>
                            Write Post
                        </a>
                    </li>
                    : null
                }
            </ul>
        </nav>
    );
};

export default Navigation;