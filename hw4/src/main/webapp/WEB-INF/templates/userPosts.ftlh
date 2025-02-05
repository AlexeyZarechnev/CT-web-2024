<#import "./common.ftlh" as c>

<@c.page>
    <link rel="stylesheet" href="/css/posts.css">
    <#list posts?reverse as post>
        <#if post.userId == user_id>
            <@c.post post><@c.shortText post/></@c.post>
        </#if>
    </#list>
</@c.page>
