import React from 'react';

const Footer = ({usersCount, postsCount}) => {
    return (
        <footer>
            <div className="creator-credentials">
                <a href="#">Codehorses</a> 2099 by Mike Mirzayanov
            </div>
            <a>Users registered: {usersCount}</a>
            <a>Post wrote: {postsCount}</a>
        </footer>
    );
};

export default Footer;