package uk.ac.cam.echo.client.data;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import uk.ac.cam.echo.client.ProxyResource;
import uk.ac.cam.echo.data.*;
import uk.ac.cam.echo.data.resources.ConferenceResource;
import uk.ac.cam.echo.data.resources.ConversationResource;

import java.util.Collection;
import java.util.List;

public class ConversationData extends BaseData implements Conversation {
    private String name;
    private ProxyResource<Conference, ConferenceResource> conferenceProxy =
            new ProxyResource<Conference, ConferenceResource>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public Conference getConference() {
        return conferenceProxy.getData();
    }

    public Long getConferenceId() {
        return conferenceProxy.getId();
    }

    @JsonProperty
    public void setConference(Conference data) {
        conferenceProxy.setData(data);
    }

    @JsonProperty
    public void setConferenceId(long id) {
        conferenceProxy.setId(id);
    }


    @Override
    @JsonIgnore
    public Collection<Tag> getTags() {
        Collection<Tag> tags = ((ConversationResource) getResource()).getTagResource(getId()).getAll();

        for (Tag t: tags) {
            ((TagData) t).configureResource(getId());
        }

        return tags;
    }

    @Override
    @JsonIgnore
    public Collection<Message> getMessages() {
        return getApi().conversationResource.getMessageResource(getId()).getAll();
    }

    @Override
    @JsonIgnore
    public Collection<Message> getSortedMessages() {
        return getApi().conversationResource.getMessageResource(getID()).getAll();
    }

    @Override
    @JsonIgnore
    public List<Message> getMessages(int n) {
        return getApi().conversationResource.getMessageResource(getID()).getRecent(n);
    }

    @Override
    @JsonIgnore
    public List<User> getUsers() {
        return (List<User>) getApi().conversationResource.getUsers(getId());
    }

    @Override
    public void addUser(User u) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void removeUser(User u) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void addMessage(Message m) {
        throw new UnsupportedOperationException("Not implemented yet. Use api.conversationResource.getMessageResource(conv_id).create(...)");
    }

    @Override
    protected void configureResource() {
        setResource(getApi().conversationResource);
    }
}
