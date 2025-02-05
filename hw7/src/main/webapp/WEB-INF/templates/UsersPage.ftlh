<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
            </tr>
            </thead>
            <tbody>
            <template>
                <tr>
                    <td class="user-id"></td>
                    <td class="user_login"></td>
                </tr>
            </template>
            <tr class="noData">
                <td colspan="3">
                    No data
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <template class="adminHead">
        <th>Admin</th>
    </template>

    <template class="adminCol">
        <td class="admin">
            <div class="isAdmin"></div>
            <button class="enableButton">Enable</button>
            <button class="disableButton">Disable</button>
        </td>
    </template>

    <script>
        $(function () {
            const $noData = $(".users .noData");
            $noData.find("td").text("Loading...");

            ajax(function(response) {
                    if (response["user"] != undefined && response["user"].admin) {
                        const $th = $($("template.adminHead").prop("content")).clone();
                        $(".users thead tr").append($th);
                    }

                    response["users"].forEach(function(user) {
                        $noData.hide();
                        const $tr = $($(".users template").prop("content")).clone();
                        $tr.find(".user-id").text(user["id"]);
                        $tr.find(".user_login").text(user["login"]);
                        if (response["user"] != undefined && response["user"].admin) {
                            const $td = $($("template.adminCol").prop("content")).clone();
                            $td.find(".isAdmin").text(user["admin"]);
                            $($td.find("button")).attr("data-userId", user["id"]);
                            if (user["admin"]) {
                                $td.find(".enableButton").hide();
                            } else {
                                $td.find(".disableButton").hide();
                            }
                            $($tr.find("tr")).append($td);
                        }
                        $(".users tbody").append($tr);
                    });
                    $(".users .enableButton").click(function() {
                        const $button = $(this);
                        ajax(function(response) {
                            if (response["error"] != undefined) {
                                alert(response["error"]);
                                return;
                            }
                            $button.hide();
                            $button.siblings(".disableButton").show();
                            $button.siblings(".isAdmin").text("true");
                        }, "makeAdmin", {userId: $button.attr("data-userId")});
                    });
                    $(".users .disableButton").click(function() {
                        const $button = $(this);
                        ajax(function(response) {
                            if (response["error"] != undefined) {
                                alert(response["error"]);
                                return;
                            }
                            $button.hide();
                            $button.siblings(".enableButton").show();
                            $button.siblings(".isAdmin").text("false");
                        }, "removeAdmin", {userId: $button.attr("data-userId")});
                    });
                }, "findAll", {});
        })
    </script>
</@c.page>
