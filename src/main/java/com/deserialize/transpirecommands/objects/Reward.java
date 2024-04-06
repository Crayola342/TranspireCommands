package com.deserialize.transpirecommands.objects;

public class Reward {
    private boolean sendMessage;
    private String message;
    private String command;

    public Reward(boolean sendMessage, String message, String command) {
        this.sendMessage = sendMessage;
        this.message = message;
        this.command = command;
    }

    public boolean isSendMessage() {
        return this.sendMessage;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCommand() {
        return this.command;
    }
}