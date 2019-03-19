function checkEmail(inputId, tipId) {
    // 如果匹配正则
    if (/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test($("#" + inputId).val())) {
        $("#" + tipId).html("");
        return true;
    } else {
        $("#" + tipId).html("请输入正确的邮箱地址");
        return false;
    }
}

function checkPhone(inputId, tipId) {
    // 如果匹配正则
    if (/^1[34578]\d{9}$/.test($("#" + inputId).val())) {
        $("#" + tipId).html("");
        return true;
    } else {
        $("#" + tipId).html("请输入正确的手机号");
        return false;
    }
}

function checkLength(inputId, tipId, length) {
    // 如果匹配正则
    if (new RegExp("^.{" + length + ",}$").test($("#" + inputId).val())) {
        $("#" + tipId).html("");
        return true;
    } else {
        $("#" + tipId).html("长度至少" + length + "位");
        return false;
    }
}

function checkDate(inputId, tipId) {
    var pattern = /^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$/;
    // 如果匹配正则
    if (pattern.test($("#" + inputId).val())) {
        $("#" + tipId).html("");
        return true;
    } else {
        $("#" + tipId).html("请输入正确的日期格式，如2016-07-20");
        return false;
    }
}

function checkRepassword(passwordId, repasswordId, repasswordTipId) {
    if ($("#" + passwordId).val() == $("#" + repasswordId).val()) {
        $("#" + repasswordTipId).html("");
        return true;
    } else {
        $("#" + repasswordTipId).html("两次输入的密码不一致");
        return false;
    }
}
function checkNotEmpty(inputId, tipId) {
    if ($("#" + inputId).val()) {
        $("#" + tipId).html("");
        return true;
    } else {
        $("#" + tipId).html("不能为空");
        return false;
    }
}
