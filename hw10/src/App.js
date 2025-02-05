import './App.css';
import Enter from "./components/Middle/Main/Form-box/Enter/Enter";
import Register from "./components/Middle/Main/Form-box/Register/Register";
import WritePost from "./components/Middle/Main/WritePost/WritePost";
import Users from "./components/Middle/Main/Users/Users";
import Index from "./components/Middle/Main/Index/Index";
import React, {useCallback, useState} from "react";
import Middle from "./components/Middle/Middle";
import Footer from "./components/Footer/Footer";
import Header from "./components/Header/Header";
import Post from "./components/Middle/Main/Post/Post";
import Comments from './components/Middle/Main/Post/Comments/Comments';


function App({usersData, postsData}) {

    const [user, setUser] = useState(null);
    const [page, setPage] = useState('index');
    const [posts, setPosts] = useState(postsData);
    const [users, setUsers] = useState(usersData);

    const createPost = useCallback((post) => {
        const maxId = Math.max(...posts.map((post) => post.id), 0) + 1;
        setPosts([...posts, {...post, id: maxId, comments: []}]);
    }, [posts])

    const createUser = useCallback((user) => {
        const maxId = Math.max(...users.map((post) => post.id), 0) + 1;
        setUsers([...users, {...user, id: maxId}]);
    }, [users])

    const addComment = useCallback((postId, comment) => {
        const post = posts.find((post) => post.id === postId);
        post.comments.unshift(comment);
        setPosts(posts);
    }, [posts])

    const getPage = useCallback((page) => {
        switch (page.uri) {
            case 'index':
                return (<Index posts={posts} setPage={setPage}/>);
            case 'enter':
                return (<Enter users={users} setUser={setUser} setPage={setPage}/>);
            case 'register':
                return (<Register users={users} setPage={setPage} createUser={createUser}/>);
            case 'writePost':
                return (<WritePost createPost={createPost} setPage={setPage}/>);
            case 'users':
                return (<Users users={users}/>);
            case 'post':
                const post = posts.find((post) => post.id === page.postId);
                return (<>
                    <Post id={post.id} title={post.title} text={post.text} comments={post.comments} setPage={setPage}/>
                    <Comments post={post} user={user} setPage={setPage} addComment={addComment}/>
                </>);
        }
    }, [user, users, posts])

    return (
        <div className="App">
            <Header setUser={setUser} setPage={setPage} user={user}/>
            <Middle
                posts={posts}
                page={getPage(page)}
                setPage={setPage}
            />
            <Footer usersCount={users.length} postsCount={posts.length}/>
        </div>
    );
}

export default App;
