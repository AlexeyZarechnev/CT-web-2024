<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#if handle??>
        <#assign u = c.findBy(users, "handle", handle)!/>
    <#elseif user_id??>
        <#assign u = c.findBy(users, "id", user_id)!/>
    </#if>
    <link rel="stylesheet" href="/css/user.css">

    <div class="profile">
        <#if u?? && u?has_content>
            <div class="data">
                <h1><@c.userlink u "false"/></h1>
                <ul>
                    <li>id: ${u.id}</li>
                    <li>handle: ${u.handle}</li>
                    <li>name: ${u.name}</li>
                    <li>posts: <a href="/userPosts?user_id=${u.id}">${u.postsCount}</a></li>
                </ul>
            </div>
            <img src="../../img/no-title.jpeg" alt="avatar"/>
        <#else>
            No such user
        </#if>
    </div>
    <#if u??>
        <div class="usersNav">
            <#assign prev = c.findPrevHandleById(users, u.id)!/>
            <#assign next = c.findNextHandleById(users, u.id)!/>
            <#if prev?has_content>
                <a href="/user?handle=${prev}"><--</a>
            <#else>
                <@c.disabledLink><--</@c.disabledLink>
            </#if>
            <#if next?has_content>
                <a href="/user?handle=${next}">--></a>
            <#else>
                <@c.disabledLink>--></@c.disabledLink>
            </#if>
        </div>
    </#if>
</@c.page>
