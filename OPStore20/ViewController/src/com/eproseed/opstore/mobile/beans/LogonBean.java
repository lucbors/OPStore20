package com.eproseed.opstore.mobile.beans;

import com.eproseed.opstore.mobile.model.ServicesWrapper;

import javax.el.ValueExpression;

import oracle.adfmf.framework.api.AdfmfJavaUtilities;

public class LogonBean {
    public LogonBean() {
        super();
    }
    public String Logon() {
        ValueExpression ve = AdfmfJavaUtilities.getValueExpression("#{bindings.userName.inputValue}", String.class);
        String userName = (String)ve.getValue(AdfmfJavaUtilities.getAdfELContext());
        
        ve = AdfmfJavaUtilities.getValueExpression("#{bindings.passWord.inputValue}", String.class);
        String password = (String)ve.getValue(AdfmfJavaUtilities.getAdfELContext());
        
        if (ServicesWrapper.Logon(userName, password)=="success")
            return "success";
        
        return null;
    }
}
