<#import "commons.ftlh" as c>

<@c.page>
    <div class="noData">No articles</div>
</@c.page>

<template id="articleTemplate">
    <article>
        <div class="title"></div>
        <div class="information"></div>
        <div class="body"></div>
        <div class="footer">
            <div class="left">
                <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                <span class="positive-score">+173</span>
                <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
            </div>
            <div class="right">
                <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                2 days ago
                <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                <a href="#">68</a>
            </div>
        </div>
    </article>
</template>

<script>
        $(function () {
            const $noData = $("div.noData");
            $noData.text("Loading...");

            ajax(function(response) {
                    $noData.hide();
                    const users = response["users"];
                    const findById = function(id) {
                        return users.find(function(user) {
                            return user["id"] === id;
                        });
                    };
                    if (response["articles"].length === 0) {
                        $noData.text("No visible articles");
                        $noData.show();
                        return;
                    }
                    response["articles"].sort(
                        function(a, b) {
                            return new Date(b["creationTime"]) - new Date(a["creationTime"]);
                        }
                    ).forEach(function(article) {
                        const $article = $($("#articleTemplate").prop("content")).clone();

                        $article.find(".title").text(article["title"]);
                        $article.find(".information").text("By " + findById(article["userId"])["login"] + " at " + article["creationTime"]);
                        $article.find(".body").text(article["text"]);
                        
                        $("main").append($article);
                    });
                }, "findAll", {});
        })
    </script>
