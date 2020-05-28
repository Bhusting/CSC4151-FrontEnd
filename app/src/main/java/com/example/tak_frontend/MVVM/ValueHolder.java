package com.example.tak_frontend.MVVM;

public class ValueHolder {

    private int code;
    private Object object;
    ValueHolder(){
        code = 0;
        object = null;
    }
    ValueHolder(int code, Object object){
        this.code = code;
        this.object = object;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

}
