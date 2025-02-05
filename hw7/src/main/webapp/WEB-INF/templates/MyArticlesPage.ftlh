<#import "commons.ftlh" as c>

<@c.page>
    <#if error??>
        You should be authorized to view this page
    <#else>
        <div class="articles datatable">
            <div class="caption">My Articles</div>
            <table>
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Title</th>
                    <th>Visibility</th>
                </tr>
                </thead>
                <tbody>
                <#list articles as article>
                    <tr>
                        <td>${article.id}</td>
                        <td>${article.title}</td>
                        <#if article.hidden>
                            <td>
                                <button class="showButton" data-id="${article.id}">Show</button>
                                <button hidden class="hideButton" data-id="${article.id}">Hide</button>
                            </td>
                        <#else>
                            <td>
                                <button hidden class="showButton" data-id="${article.id}">Show</button>
                                <button class="hideButton" data-id="${article.id}">Hide</button>
                            </td>
                        </#if>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>

        <script>
            $(function () {
                $(".hideButton").click(function() {
                    const $button = $(this);
                    const articleId = $button.attr("data-id");
                    ajax(function(response) {
                        if (response["error"] != undefined) {
                            alert(response["error"]);
                            return;
                        }
                        $button.hide();
                        $button.siblings(".showButton").show();
                    }, "hideArticle", {articleId});
                });

                $(".showButton").click(function() {
                    const $button = $(this);
                    const articleId = $button.attr("data-id");
                    ajax(function(response) {
                        if (response["error"] != undefined) {
                            alert(response["error"]);
                            return;
                        }
                        $button.hide();
                        $button.siblings(".hideButton").show();
                    }, "showArticle", {articleId});
                });
            });
        </script>
    </#if>
</@c.page>