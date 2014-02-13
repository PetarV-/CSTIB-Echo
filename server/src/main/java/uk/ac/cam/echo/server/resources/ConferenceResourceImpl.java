package uk.ac.cam.echo.server.resources;

import uk.ac.cam.echo.data.Conference;
import uk.ac.cam.echo.data.Conversation;
import uk.ac.cam.echo.data.User;
import uk.ac.cam.echo.data.resources.ConferenceResource;
import uk.ac.cam.echo.server.HibernateUtil;
import uk.ac.cam.echo.server.models.ConferenceModel;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class ConferenceResourceImpl implements ConferenceResource {

    @Override
    public Conference create(Conference data) {
        HibernateUtil.getTransaction().save(data);

        return data;
    }

    public List<Conference> getAll() {
        return HibernateUtil.getTransaction().createCriteria(ConferenceModel.class).list();
    }

    @Override
    public List<Conversation> getConversations(long id) {
        ConferenceModel conf = (ConferenceModel) get(id);
        return new ArrayList<Conversation>(conf.getConversationSet());
    }

    @Override
    public List<Conversation> search(long id, String keyword, int n) {
        return AnalystFactory.get(id).search(keyword, n);
    }

    @Override
    public List<Conversation> onlyTagSearch(long id, String keyword, int n) {
        return AnalystFactory.get(id).onlyTagSearch(keyword, n);
    }

    @Override
    public List<Conversation> onlyNameSearch(long id, String keyword, int n) {
        return AnalystFactory.get(id).onlyNameSearch(keyword, n);
    }

    @Override
    public List<Conversation> mostUsers(long id, int n) {
        return AnalystFactory.get(id).mostUsers(n);
    }

    @Override
    public List<Conversation> mostActiveRecently(long id, long minutes, int n) {
        return AnalystFactory.get(id).mostActiveRecently(minutes, n);
    }

    @Override
    public List<Conversation> recommend(long id, long userID, int n) {
        throw  new UnsupportedOperationException("Not Implemented yet");
    }

    @Override
    public List<Conversation> mostMessages(long id, int n) {
        return AnalystFactory.get(id).mostMessages(n);
    }

    @Override
    public List<User> mostActiveUsers(long id, int n) {
        throw  new UnsupportedOperationException("Not Implemented yet");
    }

    public Conference get(long id) {
        return (Conference) HibernateUtil.getTransaction().get(ConferenceModel.class, id);
    }

    public Response update(Conference conference) {
        HibernateUtil.getTransaction().update(conference);
        return Response.ok().build();
    }

    public Response delete(long id) {
        Conference u = get(id);
        HibernateUtil.getTransaction().delete(u);
        return Response.ok().build();
    }
}
