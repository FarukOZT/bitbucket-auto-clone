package com.bitbucket.autoclone.model;

import java.util.List;

public class BBModel {
    private int size;
    private int limit;
    private int start;

    private boolean isLastPage;
    private List<Repo> values;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<Repo> getValues() {
        return values;
    }

    public void setValues(List<Repo> values) {
        this.values = values;
    }
}
