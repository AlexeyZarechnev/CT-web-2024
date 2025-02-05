<#import "commons.ftlh" as c>

<@c.page>
    <div class="article form-box">
        <div class="header">Create article</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="create"/>
                <div class="field">
                    <div class="name">
                        <label for="title">Title</label>
                    </div>
                    <div class="value">
                        <input id="title" name="title"/>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="text">Text</label>
                    </div>
                    <div class="value">
                        <textarea id="text" name="text"></textarea>
                    </div>
                </div>
                <div class="error"></div>
                <div class="button-field">
                    <input type="submit" value="Create">
                </div>
            </form>
        </div>
    </div>

    <script>
        $(function () {
            $(".article form").submit(function () {
                const $title = $(this).find("input[name='title']");
                const $text = $(this).find("textarea[name='text']");

                const title = $(this).find("input[name='title']").val();
                const text = $(this).find("textarea[name='text']").val();
                const $error = $(this).find(".error");

                ajax(function (response) {
                    if (response["error"]) {
                        $error.text(response["error"]);
                    } else {
                        $title.val("");
                        $text.val("");
                        $error.text("");
                    } 
                }, "create", {title, text});

                return false;
            });
        })
    </script>
</@c.page>
