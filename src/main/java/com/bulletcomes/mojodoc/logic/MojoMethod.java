package com.bulletcomes.mojodoc.logic;

import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;

public class MojoMethod {
    private RequestMethod requestMethod;
    private String name;
    private String description;
    private ArrayList<MojoItem> params = new ArrayList<MojoItem>();
    private ArrayList<MojoItem> responses = new ArrayList<MojoItem>();
    private String note;
    private String requestContent;

    public String getRequestContent() {
        return requestContent;
    }

    public void setRequestContent(String requestContent) {
        this.requestContent = requestContent;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<MojoItem> getParams() {
        return params;
    }

    public void setParams(ArrayList<MojoItem> params) {
        this.params = params;
    }

    public ArrayList<MojoItem> getResponses() {
        return responses;
    }

    public void setResponses(ArrayList<MojoItem> responses) {
        this.responses = responses;
    }

    public void addParam(MojoItem param) {
        params.add(param);
    }

    public void addResponse(MojoItem response) {
        responses.add(response);
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }
}
