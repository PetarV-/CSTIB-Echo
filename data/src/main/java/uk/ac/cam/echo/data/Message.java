package uk.ac.cam.echo.data;

import java.io.Serializable;

public interface Message extends Serializable
{
    public long getId();
    public long getTimeStamp();
    public User getSender();
    public Conversation getConversation();
    public String getContents();
}