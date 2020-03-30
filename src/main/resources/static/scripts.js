function randomId(length) {
    var S4 = function() {
       return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
    };
    return Array.from({length: length}, () => S4()).reduce((a, b) => a+b)
}

function modal(url, options) {
    var defaultOptions = {
        callback: undefined,
        size: "", // modal-lg, modal-sm
        method: 'POST'
    };

    options = $.extend(defaultOptions, options);

    $.ajax({
        url: url,
        async: false,
        timeout: 100000,
        success: function(data) {
            var dialogId = "dialog_" + randomId(1000000);
            var dialogIdRef = "#" + dialogId;

            $("body").append('<div id="' + dialogId + '" class="modal fade" tabindex="-1"><div class="modal-dialog ' + options.size + '"><div class="modal-content"><form></form></div></div></div>');

            $(dialogIdRef).find('form').ajaxForm({
                url: url,
                type: options.method,
                async: false,
                cache: false,
                success: function (data) {
                    responseHandler($(dialogIdRef), data);
                }
            });

            $(dialogIdRef).find('form').html(data);

            $(dialogIdRef)
                .modal({show: false})
                .on("hidden.bs.modal", function () {
                    $(dialogIdRef).remove();
                    if (options.callback != undefined) {
                        options.callback();
                    }
                })
                .on("shown.bs.modal", function () {
                    $(dialogIdRef).on('change', 'input,select,textarea', function () {
                        $(dialogIdRef).data('bs.modal').options.backdrop = 'static';
                    });
                    $(dialogIdRef).on('click', 'button', function () {
                        $(dialogIdRef).data('bs.modal').options.backdrop = 'static';
                    });
                    $(dialogIdRef).find('select.multiselect').each(function (i, multiselect) {
                        initMultiselect(multiselect);
                    });
                })
                .modal('show');
        },
        error: function(e) {
            console.log("ERROR: ", e);
            display(e);
        },
        done: function(e) {
            console.log("DONE");
        }
    });

    return false;
}

function responseHandler(form, response)
{
    if (response.responseType == "REDIRECT") {
        location.href = response.url;
        return;
    }
    if (response.responseType == "MESSAGE") {
        var messages = response.messages;
        form.find('[data-editor-for]').removeClass('is-invalid');
        form.find('[data-errors-for]').text('');
        Object.keys(messages).forEach(function(field){
            var message = messages[field][0].message;

            var dataEditor = form.find('[data-editor-for*="' + field + '"]');
            var dataErrors = form.find('[data-errors-for*="' + field + '"]');

            if (dataEditor.length === 0 || dataErrors.length === 0) {
                console.log("Server validation error: Field '" + field + "' was not found on the form.");
            } else {
                dataEditor.addClass("is-invalid");
                dataErrors.text(message);
            }
        });
        return;
    }
}

function loadTemplate(id, template) {
    if (typeof template == 'string') {
        $("#" + id).val(template);
        return;
    }
    const pathPrefix = id.length == 0 ? path : id + '-';
    for (let [key, value] of Object.entries(template)) {
        loadTemplate(pathPrefix + key, value);
    }
}

function loadDefaultTemplate(template)
{
    $.ajax({
        url: '/channel/defaultTemplate',
        async: true,
        timeout: 100000,
        success: function(data) {
            loadTemplate(template, data);
        },
        error: function(e) {
            console.log("ERROR: ", e);
            display(e);
        },
        done: function(e) {
            console.log("DONE");
        }
    })
}