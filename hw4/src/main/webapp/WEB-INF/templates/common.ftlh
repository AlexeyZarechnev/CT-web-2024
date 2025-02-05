<#macro page>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" href="/css/normalize.css">
        <link rel="stylesheet" href="/css/style.css">
        <link rel="icon" href="/favicon.ico">
    </head>
    <body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
    </body>
    </html>
</#macro>

<#macro header>
    <header>
        <a href="#" class="logo"><img alt="Codeforces" title="Codeforces"
                                      src="/img/codeforces-logo-with-telegram.png"></a>
        <div class="languages">
            <a href="#"><img alt="In English" title="In English"
                             src="/img/gb.png"></a>
            <a href="#"><img alt="In Russian" title="In Russian"
                             src="/img/ru.png"></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                <@userlink user "true"/>
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <#assign items=[
            {"href": "/index", "name": "Home"},
            {"href": "/misc/help", "name": "Help"},
            {"href": "/contests", "name": "Contests"},
            {"href": "/users", "name": "Users"}
            ]/>
            <ul>
                <#list items as item>
                    <li>
                        <#if currentPage?? && currentPage == item.href>
                            <@menuItem item>id=currentPage</@menuItem>
                        <#else>
                            <@menuItem item/>
                        </#if>
                    </li>
                </#list>
            </ul>
        </nav>
    </header>
</#macro>

<#macro menuItem item>
    <a href="${item.href}" <#nested/>>${item.name}</a>
</#macro>

<#macro sidePost p>
    <section>
        <div class="header">Post#${p.id}</div>
        <div class="body"><@shortText p/></div>
        <div class="footer">
            <a href="/post?post_id=${p.id}">View all</a>
        </div>
    </section>
</#macro>

<#macro sidebar>
    <aside>
        <div class="sidebar">
            <#list posts?reverse as post>
                <@sidePost post/>
            </#list>
        </div>
    </aside>
</#macro>

<#macro footer>
    <footer>
        2010-2021 &copy; by Mike Mirzayanov
    </footer>
</#macro>

<#macro userlink user nameOnly>
    <#if nameOnly == "true">
        <a href="/user?handle=${user.handle}">${user.name}</a>
    <#else>
        <a href="/user?handle=${user.handle}" class="userlink ${user.color}">${user.name}</a>
    </#if>
</#macro>

<#macro disabledLink>
    <a href="javascript:void(0)" style="color: gray"><#nested/></a>
</#macro>

<#macro post post>
    <article>
            <div class="title">${post.title}</div>
            <#assign u = findBy(users, "id", post.userId)/>
            <div class="information">By ${u.handle}, some time ago</div>
            <div class="body">
                <#nested/>
            </div>
            <ul class="attachment">
            </ul>
            <div class="footer">
                <div class="left">
                    <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                    <span class="positive-score">+173</span>
                    <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
                </div>
                <div class="right">
                    <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                    some time ago
                    <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                    <a href="#">68</a>
                </div>
            </div>
        </article>
</#macro>

<#macro shortText p>
    <#if p.text?length &gt; 350>
        ${p.text[0..350]}...
    <#else>
        ${p.text}
    </#if>
</#macro>

<#function findBy items key value>
    <#list items as item>
        <#if item[key]?? && item[key] == value>
            <#return item/>
        </#if>
    </#list>
</#function>

<#function findPrevHandleById items value>
    <#list items as item>
        <#if item.id == value && result??>
            <#return result.handle/>
        </#if>
        <#assign result = item/>
    </#list>
</#function>

<#function findNextHandleById items value>
    <#list items as item>
        <#if result?? && result.id == value>
            <#return item.handle/>
        </#if>
        <#assign result = item/>
    </#list>
</#function>
