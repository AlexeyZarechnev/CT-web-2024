import { useParams } from "react-router-dom";
import Post from "../Post";

const OnePost = ({posts}) => {
    const {id} = useParams(); 
    if (!posts)
        return (<div>Loading...</div>)
    const post = posts.find(post => post.id === +id)
    if (!post)
        return (<div>No such post</div>)
    return (
        <Post key={post.id} id={post.id} title={post.title} text={post.text}/>
    )
}

export default OnePost;