import voteup from '../../../../assets/img/voteup.png';
import votedown from '../../../../assets/img/votedown.png';
import date from '../../../../assets/img/date_16x16.png';
import commentsImg from '../../../../assets/img/comments_16x16.png';
import { useNavigate } from 'react-router-dom';


const Post = ({id, title, text}) => {
    const router = useNavigate();

    return (
        <article>
            <div className="title">
                <a href="#" onClick={(event) => {
                    event.preventDefault();
                    router(`/posts/${id}`);
                }}>
                    {title}
                </a>
            </div>
            <div className="information"></div>
            <div className="body">
                {text}
            </div>
            <div className="footer">
                <div className="left">
                    <img src={voteup} title="Vote Up" alt="Vote Up"/>
                    <span className="positive-score">+173</span>
                    <img src={votedown} title="Vote Down" alt="Vote Down"/>
                </div>
                <div className="right">
                    <img src={date} title="Publish Time" alt="Publish Time"/>
                    2 days ago
                    <img src={commentsImg} title="Comments" alt="Comments"/>
                    <a href="#">{0}</a>
                </div>
            </div>
        </article>
    );
}

export default Post;