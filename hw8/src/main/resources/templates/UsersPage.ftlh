<#import "/spring.ftl" as spring>
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
            </tr>
            </thead>
            <tbody>
            <#if users?size==0>
                <tr class="noData">
                    <td colspan="3">
                        No data
                    </td>
                </tr>
            <#else>
                <#list users as user>
                <tr>
                    <td class="id">${user.id?c}</td>
                    <td class="login"><@c.userlink user/></td>
                    <td class="creationTime">${user.creationTime}</td>
                    <td class="enterRight">
                        <#if user.disabled>
                            <form method="post" action="<@spring.url '/users/enable/' + user.id?c/>">
                                <input type="submit" value="Enable">
                            </form>
                        <#else>
                            <form method="post" action="<@spring.url '/users/disable/' + user.id?c/>">
                                <input type="submit" value="Disable">
                            </form>
                        </#if>
                    </td>
                </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</@c.page>
