package com.shavatech.management.infraestructure.google;

import java.util.List;

public class Reminders {
    public boolean useDefault = true;
    public List<Override> overrides;

    public boolean isUseDefault() {
        return useDefault;
    }

    public void setUseDefault(boolean useDefault) {
        this.useDefault = useDefault;
    }

    public List<Override> getOverrides() {
        return overrides;
    }

    public void setOverrides(List<Override> overrides) {
        this.overrides = overrides;
    }
}
