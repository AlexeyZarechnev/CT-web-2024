import React, {useMemo} from 'react';
import Aside from "./Aside/Aside";

const Middle = ({posts, page, setPage}) => {

    const aside = useMemo(() => {
        return (<Aside posts={posts} setPage={setPage}/>)
    }, [posts])

    return (
        <div className="middle">
            {aside}
            <main>
                {page}
            </main>
        </div>
    );
};

export default Middle;