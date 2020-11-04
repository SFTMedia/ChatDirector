package com.blalp.chatdirector.model.fancychat;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.blalp.chatdirector.ChatDirector;

public class FancyMessage {
    public String text,color=null;
    public ArrayList<FancyMessage> next=new ArrayList<>();
    public boolean bold,italics,strikethrough,obfuscated;
    public FancyMessageEnum click=FancyMessageEnum.NONE,hover=FancyMessageEnum.NONE;
    public String clickData,hoverData;
	public FancyMessage(String data) {
        text=data;
	}

	public FancyMessage() {
        text="";
    }

    public FancyMessage withContext(Map<String,String> context){
        text=ChatDirector.format(text, context);
        for(FancyMessage message:next){
            message.withContext(context);
        }
        clickData=ChatDirector.format(clickData, context);
        hoverData=ChatDirector.format(hoverData, context);
        return this;
    }
    
    public FancyMessage duplicate(){
        FancyMessage output = new FancyMessage();
        output.text=text;
        output.color=color;
        for(FancyMessage message:next){
            output.next.add(message.duplicate());
        }
        output.bold=bold;
        output.italics=italics;
        output.strikethrough=strikethrough;
        output.obfuscated=obfuscated;
        output.click=click;
        output.hover=hover;
        output.clickData=clickData;
        output.hoverData=hoverData;
        return output;
    }

	public void append(FancyMessage fancyMessage) {
        next.add(fancyMessage);
	}

	public void setClickEvent(FancyMessageEnum clickType, String string) {
        click=clickType;
        clickData=string;
	}
	public void setHoverEvent(FancyMessageEnum hoverType, String string) {
        hover=hoverType;
        hoverData=string;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public void setItalics(boolean italics) {
        this.italics = italics;
    }

    public void setStrikethrough(boolean strikethrough) {
        this.strikethrough = strikethrough;
    }

    public void setObfuscated(boolean obfuscated) {
        this.obfuscated = obfuscated;
    }
    
    public static FancyMessage parse(Object data) {
        FancyMessage output=new FancyMessage();
        if(data==null){
            System.err.println("Fancy text object resolved to null, this shouldn't happen, check your YAML.");
        }
        if(data instanceof String){
            return new FancyMessage((String)data);
        }
        if(data instanceof ArrayList){
            for (Object nestedData:(ArrayList)data){
                if(output==null){
                    output=parse(nestedData);
                } else {
                    output.append(parse(nestedData));
                }
            }
        }
        if (data instanceof Map){
            // This is the actual map
            String type = ((LinkedHashMap<String,Object>)data).keySet().toArray(new String[1])[0];
            LinkedHashMap<String,Object> dataMap = (LinkedHashMap<String, Object>) ((LinkedHashMap<String,Object>)data).values().toArray()[1];
            output = parse(dataMap.get("text"));
            switch (type){
                case "click":
                    if(dataMap.containsKey("click-file")){
                        output.setClickEvent(FancyMessageEnum.OPEN_FILE, (String)dataMap.get("click-file"));
                    }
                    if(dataMap.containsKey("click-change-page")){
                        output.setClickEvent(FancyMessageEnum.CHANGE_PAGE, (String)dataMap.get("click-change-page"));
                    }
                    if(dataMap.containsKey("click-run-command")){
                        output.setClickEvent(FancyMessageEnum.RUN_COMMAND, (String)dataMap.get("click-run-command"));
                    }
                    if(dataMap.containsKey("click-suggest-command")){
                        output.setClickEvent(FancyMessageEnum.SUGGEST_COMMAND, (String)dataMap.get("click-suggest-command"));
                    }
                    if(dataMap.containsKey("click-url")){
                        output.setClickEvent(FancyMessageEnum.OPEN_URL, (String)dataMap.get("click-url"));
                    }
                case "color":
                    if(dataMap.containsKey("color")){
                        output.setColor((String)dataMap.get("color"));
                    }
                    if(dataMap.containsKey("bold")){
                        output.setBold((boolean)dataMap.get("bold"));
                    }
                    if(dataMap.containsKey("italics")){
                        output.setItalics((boolean)dataMap.get("italics"));
                    }
                    if(dataMap.containsKey("strikethrough")){
                        output.setStrikethrough((boolean)dataMap.get("strikethrough"));
                    }
                    if(dataMap.containsKey("obfuscated")){
                        output.setObfuscated((boolean)dataMap.get("obfuscated"));
                    }
                case "hover":
                    if(dataMap.containsKey("show-achievement")){
                        output.setHoverEvent(FancyMessageEnum.SHOW_ACHIEVEMENT, (String)dataMap.get("show-achievement"));
                    }
                    if(dataMap.containsKey("show-entity")){
                        output.setHoverEvent(FancyMessageEnum.SHOW_ENTITY, (String)dataMap.get("show-entity"));
                    }
                    if(dataMap.containsKey("show-item")){
                        output.setHoverEvent(FancyMessageEnum.SHOW_ITEM, (String)dataMap.get("show-item"));
                    }
                    if(dataMap.containsKey("show-text")){
                        output.setHoverEvent(FancyMessageEnum.SHOW_TEXT, (String)dataMap.get("show-text"));
                    }
                default:
                    System.err.println(type+ " is not a valid fancy type");
            }
        }
        return output;
    }
}
