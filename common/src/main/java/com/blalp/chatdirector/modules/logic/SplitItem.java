package com.blalp.chatdirector.modules.logic;

import java.util.ArrayList;

import com.blalp.chatdirector.modules.common.PassItem;
import com.blalp.chatdirector.utils.ValidationUtils;
import com.blalp.chatdirector.model.Chain;
import com.blalp.chatdirector.model.Context;

public class SplitItem extends PassItem {
    ArrayList<Chain> chains = new ArrayList<Chain>();
    public SplitItem(ArrayList<Chain> chains) {
        this.chains=chains;
    }

    @Override
    public Context process(Context context) {
        for(Chain chain:chains){
            new Thread(chain).start();
        }
        return new Context();
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.isNotNull(chains);
    }
}