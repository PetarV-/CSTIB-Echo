package uk.ac.cam.echo.data;

import java.io.Serializable;

public interface Message implements Serializable
{
    public long getID();
    public long getTimeStamp();
    public User getSender();
    public Conversation getConversation();
    public String getContents();
}