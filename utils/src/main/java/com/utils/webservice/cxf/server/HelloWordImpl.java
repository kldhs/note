package com.utils.webservice.cxf.server;

public class HelloWordImpl implements HellowWord{
    @Override
    public String sayHi(String text) {
        return "sjgagas"+text;
    }
}
