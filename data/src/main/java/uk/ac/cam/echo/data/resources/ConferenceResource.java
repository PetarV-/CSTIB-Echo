package uk.ac.cam.echo.data.resources;

import uk.ac.cam.echo.data.Conference;
import uk.ac.cam.echo.data.Conversation;
import uk.ac.cam.echo.data.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/conferences")
@Produces("application/json")
public interface ConferenceResource extends RestResource<Conference> {
    @GET
    public List<Conference> getAll();

    @GET
    @Path("/{id}")
    public Conference get(@PathParam("id") long id);

    @Path("/{conferenceId}/search")
    @GET
    public List<Conversation> search(@PathParam("conferenceId") long id, @QueryParam("keyword") String keyword, @QueryParam("num") int n);

    @Path("/{conferenceId}/tag-search")
    @GET
    public List<Conversation> onlyTagSearch(@PathParam("conferenceId") long id, @QueryParam("keyword") String keyword, @QueryParam("num") int n);

    @Path("/{conferenceId}/name-search")
    @GET
    public List<Conversation> onlyNameSearch(@PathParam("conferenceId") long id, @QueryParam("keyword") String keyword, @QueryParam("num") int n);

    @Path("/{conferenceId}/most-users")
    @GET
    public List<Conversation> mostUsers(@PathParam("conferenceId") long id, @QueryParam("num") int n);

    @Path("/{conferenceId}/most-active")
    @GET
    public List<Conversation> mostActiveRecently(@PathParam("conferenceId") long id, @QueryParam("millis") long millis, @QueryParam("num") int n);

    @Path("/{conferenceId}/recommend")
    @GET
    public List<Conversation> recommend(@PathParam("conferenceId") long id, @QueryParam("uid") long userID, @QueryParam("num") int n);

    @Path("/{conferenceId}/most-messages")
    @GET
    public List<Conversation> mostMessages(@PathParam("conferenceId") long id, @QueryParam("num") int n);

    @Path("/{conferenceId}/most-active-users")
    @GET
    public List<User> mostActiveUsers(@PathParam("conferenceId") long id, @QueryParam("num") int n);

    @Path("/{conferenceId}/activity")
    @GET
    public int activity(@PathParam("conferenceId") long id, @QueryParam("millis") long millis);

    @Path("/{conferenceId}/m-f-ratio")
    @GET
    public double maleToFemaleRatio(@PathParam("conferenceId") long id);

    @Path("/{conferenceId}/msg-count")
    @GET
    public int messageCount(@PathParam("conferenceId") long id, @QueryParam("conversationId") long convoId);

    @Path("/{conferenceId}/msg-count-total")
    @GET
    public int messageCount(@PathParam("conferenceId") long id);

    @Path("/{conferenceId}/user-count")
    @GET
    public int userCount(@PathParam("conferenceId") long id, @QueryParam("conversationId") long convoId);

    @Path("/{conferenceId}/user-count-total")
    @GET
    public int userCount(@PathParam("conferenceId") long id);

    @Path("/{conferenceId}/contrib-users")
    @GET
    public int contributingUsers(@PathParam("conferenceId") long id, @QueryParam("conversationId") long convoId, @QueryParam("current") boolean current);

    @GET
    @Path("/{conferenceId}/conversations")
    public List<Conversation> getConversations(@PathParam("conferenceId") long id);


    @PUT
    @Consumes("application/json")
    public Response update(Conference item);


    @POST
    @Consumes("application/json")
    public Object create (Conference data);

}
