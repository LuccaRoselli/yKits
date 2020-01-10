package com.luccadev.br.constructors;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Report
{
    private UUID reported;
    private ArrayList<String> reasons;
    private ArrayList<String> reporters;
    private ArrayList<String> admins;
    private Long expire;
    private ReportStatus status;
    private String hour;
    
    public Report(final UUID reported) {
        this.reported = reported;
        this.reporters = new ArrayList<String>();
        this.reasons = new ArrayList<String>();
        this.admins = new ArrayList<String>();
        this.expire = System.currentTimeMillis() + 3600000L;
        this.status = ReportStatus.OPEN;
        this.hour = new SimpleDateFormat("HH:mm:ss").format(new Date());
    }
    
    public UUID getReported() {
        return this.reported;
    }
    
    public ArrayList<String> getReasons() {
        return this.reasons;
    }
    
    public ArrayList<String> getReporters() {
        return this.reporters;
    }
    
    public ArrayList<String> getAdmins() {
        return this.admins;
    }
    
    public String getHour(){
    	return this.hour;
    }
    
    public Long getExpire() {
        return this.expire;
    }
    
    public void updateExpire() {
        this.expire = System.currentTimeMillis() + 3600000L;
    }
    
    public ReportStatus getStatus() {
        return this.status;
    }
    
    public void setStatus(final ReportStatus status) {
        this.status = status;
    }
    
    public enum ReportStatus
    {
        OPEN("OPEN", 0), 
        CHECKING("CHECKING", 1), 
        BANNED("BANNED", 2), 
        OFFLINE("OFFLINE", 3);
        
        private ReportStatus(final String s, final int n) {
        }
    }
}