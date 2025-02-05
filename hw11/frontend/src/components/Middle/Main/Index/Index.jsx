import React from 'react';
import Post from '../Post/Post';

const Index = ({posts}) => {
    if (!posts)
        return (<div>Loading...</div>)
    return (
        <>
            {posts.map(post => {
                return <Post key={post.id} id={post.id} title={post.title} text={post.text}/>
            })}
        </>
    )
};

export default Index;