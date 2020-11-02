package com.blalp.chatdirector.modules.logic;

import java.util.Map;

import com.blalp.chatdirector.modules.common.PassItem;
import com.blalp.chatdirector.configuration.Configuration;
import com.blalp.chatdirector.model.IItem;

public abstract class ConditionalItem extends PassItem {
    
    IItem nestedTrue;
    IItem nestedFalse;
    boolean stopOnFalse=false,invert=false,stopOnTrue=false;
    String source="%CURRENT%";

    @Override
    public void work(String string, Map<String,String> context) {
        if(string.isEmpty()){
            return;
        }
        boolean result= test(string,context);
        if(invert){
            result=!result;
        }
        if(Configuration.debug){
            System.out.println("Conditional "+this.getClass().getCanonicalName()+" test returned "+result);
            System.out.println("Trues is "+nestedTrue+" false is "+nestedFalse);
        }
        if(result){
            nestedTrue.work(string,context);
        } else {
            nestedFalse.work(string,context);
        }
        if(context.containsKey("STRING")){
            string=context.get("STRING");
        }
        context.put("STRING", string);
        if(stopOnFalse&&!result||stopOnTrue&&result){
            next.work(string, context);
        } else {
            if(Configuration.debug){
                System.out.println("Stopping here. StopOnFalse is true and failed or Stop on True is true and was true. Check: "+result);
            }
        }
    }
    public abstract boolean test(String string,Map<String,String> context);
    public ConditionalItem(IItem nestedTrue,IItem nestedFalse){
        this.nestedFalse=nestedFalse;
        this.nestedTrue=nestedTrue;
    }
}
