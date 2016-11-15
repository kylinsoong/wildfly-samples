package impl;

import api.HelloWorld;

public class HelloWorldImpl implements HelloWorld{

    @Override
    public String sayHello() {
        return "Hello World";
    }

    @Override
    public Long getTimestamp() {
        return System.currentTimeMillis();
    }

}
