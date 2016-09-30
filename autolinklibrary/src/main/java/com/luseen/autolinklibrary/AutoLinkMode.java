package com.luseen.autolinklibrary;

/**
 * Created by Chatikyan on 25.09.2016-22:02.
 */

public enum AutoLinkMode {
    MODE_HASHTAG("Hashtag", 1), //1
    MODE_MENTION("Mention", 1<<1), //2
    MODE_URL("Url", 1<<2), //4
    MODE_PHONE("Phone", 1<<3), //8
    MODE_EMAIL("Email", 1<<4), //16
    MODE_CUSTOM("Custom", 1<<5); //32

    private String name;
    private int bitFlag;

    AutoLinkMode(String name, final int bitFlag) {
        this.name = name;
        this.bitFlag = bitFlag;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getBitFlag(){
        return bitFlag;
    }
}
