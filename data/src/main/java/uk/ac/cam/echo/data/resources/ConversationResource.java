package uk.ac.cam.echo.data.resources;

import uk.ac.cam.echo.data.Conversation;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/conversations")
@Produces("application/json")
public interface ConversationResource {
    @GET
    public List<Conversation> getAll();

    @GET
    @Path("/{conversationId}")
    public Conversation get(@PathParam("conversationId") long id);

    @Path("/{conversationId}/messages")
    public MessageResource getMessageResource(@PathParam("conversationId") long id);

    @POST
    public Conversation create(@FormParam("name") String name, @FormParam("conference") long conference_id);

    @DELETE
    @Path("/{conversationId}")
    public Response deleteConversation(@PathParam("conversationId") long id);
}