/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.moeketsi.classes;

/**
 *
 * @author Moeketsi Tsotetsi
 */
public class SubjectStreams {
    private int id;
    private String streamName;
    private String streamCode;
    private String description;

    public SubjectStreams(int id, String streamName, String streamCode, String description) {
        this.id = id;
        this.streamName = streamName;
        this.streamCode = streamCode;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        
        if(streamName.length() <3 || streamName.isBlank()){
            throw  new IllegalArgumentException("Nme cannot be empty or less than three characters  ");
        }
        this.streamName = streamName;
    }

    public String getStreamCode() {
        return streamCode;
    }

    public void setStreamCode(String streamCode) {
        
        if(streamCode.isEmpty()  || streamCode.length()<2){
            throw  new IllegalArgumentException("Stream Code cannot be less than 2 characters");
        }
        this.streamCode = streamCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if(description.isBlank() || description.length()<5){
            throw new IllegalArgumentException("Description cannot be less than 5 characters");
        }
        this.description = description;
    }

    @Override
    public String toString() {
        return streamName;
    }
    
    
    
}
