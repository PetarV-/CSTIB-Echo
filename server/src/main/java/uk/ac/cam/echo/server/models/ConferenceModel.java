package uk.ac.cam.echo.server.models;

import uk.ac.cam.echo.data.Conference;
import uk.ac.cam.echo.data.Conversation;

import javax.persistence.*;
import java.util.Set;

@Table(name="Conference")
public class ConferenceModel implements Conference{
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="ConferenceIdSeq")
    @SequenceGenerator(name="ConferenceIdSeq", sequenceName="Conference_SEQ", allocationSize=1)
    private long id;

    private String name;
    @OneToMany(targetEntity = Conversation.class)
    private Set<Conversation> conversationSet;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Conversation> getConversationSet() {
        return conversationSet;
    }

    public void setConversationSet(Set<Conversation> conversationSet) {
        this.conversationSet = conversationSet;
    }

    @Override
    public void addConversation(Conversation conv) {
        getConversationSet().add(conv);
    }
}