package com.dankeroni.dankbot;

import java.io.FileReader;
import java.util.Properties;

public class DankBot{

    public static void main(String[] args){
        try(FileReader reader = new FileReader("config.properties")){
            Properties properties = new Properties();
            properties.load(reader);
            String botName = properties.getProperty("botname");
            String oauth = properties.getProperty("oauth");
            String admin = properties.getProperty("admin");
            String channel = "#" + properties.getProperty("channel");
            boolean silentJoinLeave = Boolean.parseBoolean(properties.getProperty("silentJoinLeave"));
            boolean superCommands = Boolean.parseBoolean(properties.getProperty("superCommands"));
            boolean logOutput = Boolean.parseBoolean(properties.getProperty("logOutput"));
            if(botName.isEmpty() || oauth.isEmpty() || admin.isEmpty() || channel.isEmpty() || properties.getProperty("silentJoinLeave").isEmpty() || properties.getProperty("superCommands").isEmpty() || properties.getProperty("logOutput").isEmpty()) {
                System.out.println("Please fill in the config file!");
                return;
            }
            System.out.println("Dankbot starting!");
            System.out.println("Botname: " + botName);
            System.out.println("Channel: " + channel.substring(1));
            System.out.println("Admin: " + admin);
            new DankBot(botName, oauth, admin, channel, silentJoinLeave, superCommands, logOutput);
        } catch(Exception e){
            System.out.println("Please fix your config file!");
            e.printStackTrace();
        }
    }

    public String botName, oauth, admin, channel;
    public boolean silentJoinLeave, superCommands, logOutput;

    public DankBot(String botName, String oauth, String admin, String channel, boolean silentJoinLeave, boolean superCommands, boolean logOutput){
        this.botName = botName;
        this.oauth = oauth;
        this.admin = admin;
        this.channel = channel;
        this.silentJoinLeave = silentJoinLeave;
        this.superCommands = superCommands;
        this.logOutput = logOutput;
        this.start();
    }

    private void start(){

    }

}
