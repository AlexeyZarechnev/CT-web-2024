<#import "./common.ftlh" as c>

<@c.page>
    <link rel="stylesheet" href="/css/table.css">
    <div class="datatable">
        <div class="caption">Users</div>
            <table>
                <tbody>
                 <#list users as u>
                    <tr>
                        <td><@c.userlink u "false"/></td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
</@c.page>