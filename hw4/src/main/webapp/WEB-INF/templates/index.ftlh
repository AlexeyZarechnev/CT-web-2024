<#import "./common.ftlh" as c>

<@c.page>
    <link rel="stylesheet" href="/css/posts.css">
    <#list posts?reverse as post>
        <@c.post post><@c.shortText post/></@c.post>
    </#list>
</@c.page>
