<#import "/spring.ftl" as spring/>
<#import "commons.ftlh" as c>

<@c.page>
    <#if post??>
        <@c.post post/>
        <div class="comments">
            <div class="add-comment">
                <form action="<@spring.url '/post/' + post.id?c/>" method="post">
                    <textarea name="text" placeholder="Your comment" value="${comment.text!}"></textarea>
                    <@c.error "comment.text"/>
                    <input type="submit" value="Add comment">
                </form>
            </div>
            <#list post.comments as comment>
                <div class="comment">
                    <div class="comment-header">
                        <div class="comment-author">${comment.user.login}</div>
                        <div class="comment-time">${comment.creationTime}</div>
                    </div>
                    <div class="comment-text">${comment.text}</div>
                </div>
            </#list>
        </div>
    <#else>
        No such post
    </#if>
</@c.page>