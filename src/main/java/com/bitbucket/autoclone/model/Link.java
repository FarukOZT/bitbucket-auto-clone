package com.bitbucket.autoclone.model;

import java.util.List;

public class Link {

    private List<Self> self;
    private List<Clone> clone;

    public List<Clone> getClone() {
        return clone;
    }

    public void setClone(List<Clone> clone) {
        this.clone = clone;
    }

    public List<Self> getSelf() {
        return self;
    }

    public void setSelf(List<Self> self) {
        this.self = self;
    }

}
