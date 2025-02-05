import React, {useRef, useState} from "react";

const Comments = ({post, user, setPage, addComment}) => {

    const textAreaRef = useRef(null);
    const [error, setError] = useState(null)

    const submitComment = () => {
        const text = textAreaRef.current.value;
        if (text.trim().length === 0) {
            setError('Comment cannot be empty');
            return;
        }
        addComment(post.id, {author: user.login, text});
        textAreaRef.current.value = '';
        setPage({uri: 'post', postId: post.id});
    }

    return (
        <div className="comments">
            {user 
            ? <div className="add-comment">
                <form action="" method="" onSubmit={(event) => {
                    event.preventDefault();
                    submitComment()
                }}>
                    <textarea 
                        name="text" 
                        placeholder="Your comment"
                        ref={textAreaRef}
                    ></textarea>
                    {error
                        ? <div className={'error'}>{error}</div>
                        : null
                    }
                    <input type="submit" value="Add comment"/>
                </form>
            </div>
            : null
            }
            {post.comments.map((comment, index) => (
                <div className="comment" key={index}>
                    <div className="comment-header">
                        <div className="comment-author">{comment.author}</div>
                    </div>
                    <div className="comment-text">{comment.text}</div>
                </div>
            ))}
        </div>
    );
}

export default Comments;