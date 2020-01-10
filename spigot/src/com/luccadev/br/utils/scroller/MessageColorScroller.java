package com.luccadev.br.utils.scroller;

import java.util.*;
import org.bukkit.*;

public class MessageColorScroller
{
    private int position;
    private ArrayList<String> lines;
    
    public MessageColorScroller(String message, final ChatColor start, final ChatColor end, final String back, final String middle, final String next, final boolean bold, final ScrollType type) {
        this.lines = new ArrayList<String>();
        this.position = 0;
        message = " " + message + " ";
        if (type == ScrollType.HYPIXEL) {
            this.lines.add(end + (bold ? "§l" : "") + message);
            this.loadMessages(message, start, end, back, middle, next, bold, ScrollType.FORWARD);
            this.lines.add(start + (bold ? "§l" : "") + message);
            this.lines.add(end + (bold ? "§l" : "") + message);
            this.lines.add(start + (bold ? "§l" : "") + message);
            this.lines.add(end + (bold ? "§l" : "") + message);
            this.lines.add(start + (bold ? "§l" : "") + message);
            this.loadMessages(message, start, end, back, middle, next, bold, ScrollType.BACKWARD);
            this.lines.add(end + (bold ? "§l" : "") + message);
            this.lines.add(start + (bold ? "§l" : "") + message);
            this.lines.add(end + (bold ? "§l" : "") + message);
            this.lines.add(start + (bold ? "§l" : "") + message);
        }
        else {
            if (type == ScrollType.BACKWARD) {
                this.lines.add(end + (bold ? "§l" : "") + message);
            }
            if (type == ScrollType.FORWARD) {
                this.lines.add(start + (bold ? "§l" : "") + message);
            }
            this.loadMessages(message, start, end, back, middle, next, bold, type);
            if (type == ScrollType.FORWARD) {
                this.lines.add(end + (bold ? "§l" : "") + message);
            }
            if (type == ScrollType.BACKWARD) {
                this.lines.add(start + (bold ? "§l" : "") + message);
            }
        }
    }
    
    private void loadMessages(final String total, final ChatColor startColor, final ChatColor endColor, final String backColor, final String middleColor, final String nextColor, final boolean bold, final ScrollType type) {
        if (type == ScrollType.BACKWARD) {
            for (int i = total.length(); i >= 0; --i) {
                this.lines.add(this.createMessage(total, i, startColor, endColor, backColor, middleColor, nextColor, bold));
            }
        }
        else if (type == ScrollType.FORWARD) {
            for (int i = 1; i <= total.length(); ++i) {
                this.lines.add(this.createMessage(total, i, startColor, endColor, backColor, middleColor, nextColor, bold));
            }
        }
    }
    
    private String createMessage(final String total, int position, final ChatColor startColor, final ChatColor endColor, final String backColor, final String middleColor, final String nextColor, final boolean bold) {
        if (position >= 0) {
            final int backPos = --position - 1;
            final int nextPos = position + 1;
            String start = "";
            String end = "";
            String back = "";
            String middle = "";
            String next = "";
            if (backPos > 0) {
                start = startColor + (bold ? "§l" : "") + total.substring(0, backPos);
            }
            if (nextPos + 1 < total.length()) {
                end = endColor + (bold ? "§l" : "") + total.substring(nextPos + 1, total.length());
            }
            if (position > -1) {
                middle = String.valueOf(middleColor) + (bold ? "§l" : "") + total.substring(position, position + 1);
            }
            if (nextPos < total.length()) {
                next = String.valueOf(nextColor) + (bold ? "§l" : "") + total.substring(nextPos, nextPos + 1);
            }
            if (backPos > -1) {
                back = String.valueOf(backColor) + (bold ? "§l" : "") + total.substring(backPos, backPos + 1);
            }
            return String.valueOf(start) + back + middle + next + end;
        }
        return "null";
    }
    
    public String getNext() {
        ++this.position;
        if (this.position == this.lines.size()) {
            this.position = 0;
        }
        return this.lines.get(this.position);
    }
    
    public boolean hasNext(){
    	return (this.position == this.lines.size());
    }
    
    public enum ScrollType
    {
        FORWARD("FORWARD", 0), 
        BACKWARD("BACKWARD", 1), 
        HYPIXEL("HYPIXEL", 2);
        
        private ScrollType(final String s, final int n) {
        }
    }
}
