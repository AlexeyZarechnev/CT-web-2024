window.notify = function (message) {
    $.notify(message, {
        position: "right bottom",
        className: "success"
    });
}

window.alert = function (message) {
    $.notify(message, {
        position: "right bottom",
        className: "error"
    });
}

window.ajax = function (success, action, data) {
    $.ajax({
        type: "POST",
        url: "",
        dataType: "json",
        data: {
            action: action,
            ...data
        },
        success: function (response) {
            if (response["redirect"]) {
                location.href = response["redirect"];
            } else {
                if (response["message"]) {
                    notify(response["message"]);
                }
                success(response);
            }
        }
    });
}
