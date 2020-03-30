package rss.controller.response.message;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import rss.controller.response.ResponseInfo;
import rss.controller.response.ResponseType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageInfo implements ResponseInfo
{
    private final Map<String, List<PageMessage>> messages = new HashMap<>();

    public static MessageInfo createFrom(Errors errors)
    {
        MessageInfo result = new MessageInfo();

        for (ObjectError error : errors.getAllErrors())
        {
            if (!(error instanceof FieldError))
            {
                throw new IllegalArgumentException("ObjectError is not instanceof FieldError. Not supported case.");
            }

            FieldError fieldError = (FieldError) error;

            String field = fieldError.getField();

            result.addFieldError(field, error.getDefaultMessage());
        }

        return result;
    }

    public boolean isValidField(String field)
    {
        return !messages.containsKey(field);
    }

    public MessageInfo addFieldError(String field, String error)
    {
        if (StringUtils.isEmpty(field))
        {
            throw new IllegalArgumentException("Field can't be null or empty");
        }

        addToMap(field, new PageMessage(error));

        return this;
    }

    public boolean hasErrors()
    {
        return !messages.isEmpty();
    }

    void addToMap(String key, PageMessage message)
    {
        String fixedKey = StringUtils.defaultIfEmpty(key, "");

        List<PageMessage> list = messages.get(fixedKey);
        if (list == null)
        {
            list = new ArrayList<>();
            messages.put(fixedKey, list);
        }

        list.add(message);
    }

    //region getters and setters

    public Map<String, List<PageMessage>> getMessages()
    {
        return messages;
    }

    @Override
    public ResponseType getResponseType()
    {
        return ResponseType.MESSAGE;
    }

    //endregion


}
