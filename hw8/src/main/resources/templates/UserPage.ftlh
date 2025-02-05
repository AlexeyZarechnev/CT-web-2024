<#import "commons.ftlh" as c>

<@c.page>
    <link rel="stylesheet" href="/css/user.css">

    <div class="profile">
        <#if u?has_content>
            <div class="data">
                <h1><@c.userlink u/></h1>
                <ul>
                    <li>id: ${u.id?c}</li>
                    <li>creation time: ${u.creationTime}</li>
                </ul>
            </div>
            <img src="../../img/no-title.jpeg" alt="avatar"/>
        <#else>
            No such user
        </#if>
    </div>
</@c.page>