import React, {useMemo} from 'react';
import Section from "./Section/Section";
import cl from './Aside.module.css'

const Aside = ({posts, setPage}) => {

    const sortedPosts = useMemo(() => {
        if (!posts)
            return []
        return posts.sort((a, b) => b.id - a.id).slice(0, 2)
    }, [posts])

    return (
        <aside className={cl.sidePosts}>
            {sortedPosts.map((post) =>
                <Section post={post} key={post.id} setPage={setPage}/>
            )}
        </aside>
    );
};

export default Aside;