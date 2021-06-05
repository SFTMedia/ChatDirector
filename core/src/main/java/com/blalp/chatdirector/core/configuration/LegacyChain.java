package com.blalp.chatdirector.core.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.IValid;
import com.blalp.chatdirector.core.model.Version;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@JsonDeserialize(using = LegacyChainDeserializer.class)
@ToString
public class LegacyChain implements IValid {
    List<ILegacyItem> items = new ArrayList<>();
    boolean invalidItem = false;

    public void addItem(ILegacyItem item) {
        items.add(item);
    }

    public void setInvalidItem() {
        invalidItem = true;
    }

    @Override
    public boolean isValid() {
        return !invalidItem;
    }

    public List<ILegacyItem> getItems() {
        return items;
    }

    public LegacyChain updateTo(Version version) {
        LegacyChain output = new LegacyChain();
        output.invalidItem = invalidItem;
        for (ILegacyItem item : items) {
            output.items.addAll(updateTo(item, version));
        }
        // Make sure there are no nulls
        while (output.items.contains(null)) {
            output.items.remove(null);
        }
        return output;
    }
    
    private Collection<? extends ILegacyItem> updateTo(ILegacyItem item, Version version) {
        List<ILegacyItem> output = new ArrayList<>();
        // It's possible for an item's latest version to be null (aka useless)
        if (item == null) {
            return output;
        }
        List<ILegacyItem> pendingOutput = new ArrayList<>();
        if (item.getNextUpdateVersion().compareTo(version) <= 0) {
            pendingOutput.addAll(item.updateToNextLegacyItems());
        } else {
            pendingOutput.add(item);
        }
        for (ILegacyItem iLegacyItem : pendingOutput) {
            // Recursive call to make sure the items we are added are latest.
            output.addAll(updateTo(iLegacyItem, version));
        }
        return output;
    }
}
