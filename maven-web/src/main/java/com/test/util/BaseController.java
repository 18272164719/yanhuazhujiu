package com.test.util;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Date;

public abstract class BaseController {

    @InitBinder
    protected final void initBinder(WebDataBinder binder, NativeWebRequest webRequest) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        /*binder.registerCustomEditor(Date.class, new DateConvertEditor(true));
        binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false));*/
        this.init(binder);
    }

    protected abstract void init(WebDataBinder var1);
}
