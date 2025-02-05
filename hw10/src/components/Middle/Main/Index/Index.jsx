import React, {useMemo} from 'react';
import Post from '../Post/Post';

const Index = ({posts, setPage}) => {

    const sortedPosts = useMemo(() => {
        if (!posts)
            return []
        return posts.sort((a, b) => b.id - a.id);
    }, [posts])

    return (
        <>
            {sortedPosts.map((post) => (<Post key={post.id} id={post.id} title={post.title} 
                                text={post.text} comments={post.comments} setPage={setPage}/>))}
        </>
    );
};

export default Index;