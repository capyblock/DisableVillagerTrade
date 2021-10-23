package me.dodo.disablevillagertrade.settings.configurations;

import java.util.List;

public interface Main {
    boolean isEnabled();
    String getContext();
    List<String> getDisabledWorlds();
}
