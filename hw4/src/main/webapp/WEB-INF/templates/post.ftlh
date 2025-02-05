<#import "./common.ftlh" as c>

<@c.page>
    <link rel="stylesheet" href="/css/posts.css">
    <#if post_id??>
        <#assign p = c.findBy(posts, "id", post_id)!/>
        <#if p?has_content>
            <@c.post p>${p.text}</@c.post>
        <#else>
            No such post
        </#if>
    <#else>
        Incorrect post_id!
    </#if>
</@c.page>