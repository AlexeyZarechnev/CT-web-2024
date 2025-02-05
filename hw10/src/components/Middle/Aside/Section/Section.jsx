import React from 'react';

const Section = ({post, setPage}) => {
    return (
        <section>
            <div className="header">
                {post.title}
            </div>
            <div className="body">
                {post.text}
            </div>
            <div className="footer">
                <a href="#" onClick={
                    (event) => {
                        event.preventDefault();
                        setPage({uri: 'post', postId: post.id});
                    }
                }>View all</a>
            </div>
        </section>
    );
};

export default Section;