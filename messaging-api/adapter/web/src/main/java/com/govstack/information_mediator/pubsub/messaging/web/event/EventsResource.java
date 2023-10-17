package com.govstack.information_mediator.pubsub.messaging.web.event;

import com.govstack.information_mediator.pubsub.domain.entity.identifier.EventTypeIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.PublisherIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.messaging.domain.usecase.pulling.PullEventMessagesUseCase;
import com.govstack.information_mediator.pubsub.messaging.domain.usecase.publishing.PublishEventsUseCase;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.UUID;

@Path("/")
@RequiredArgsConstructor
class EventsResource {

    private final PullEventMessagesUseCase pullEventMessagesUseCase;
    private final PublishEventsUseCase publishEventsUseCase;

    @GET
    @Path("rooms/{memberId}/{roomId}/pull")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Pulls the next event for the given member and room",
        description = "This endpoint allows a member to pull the next event from a room's queue. The member must be subscribed to the room's queue.")
    @Parameter(name = "memberId", description = "The identifier of the member", example = "EE/BUSINESS/123456789", required = true)
    @Parameter(name = "roomId", description = "The identifier of the room", example = "PatientPortal", required = true)
    @Parameter(name = "subscriptionId", description = "The subscription ID", example = "dc6ba878-db39-4d43-83c9-30a671481fdd", required = true)
    @APIResponse(responseCode = "200", description = "Successful pull",
        headers = @Header(name = "Another-Event-In-Queue", description = "Indicates if there are more events in the room for this subscriber (true or false)", schema = @Schema(example = "true")),
        content = @Content(schema = @Schema(implementation = String.class, example = "This is a test event payload.")))
    @APIResponse(responseCode = "404", description = "No event found")
    public Response pullEventMessage(@PathParam("memberId") String memberId,
                                     @PathParam("roomId") String roomId,
                                     @QueryParam("subscriptionId") UUID subscriptionId) {

        var request = new PullEventMessagesUseCase.Request(roomId, memberId, subscriptionId);
        var result = pullEventMessagesUseCase.execute(request);

        var payload = result.payload();
        var hasAnother = result.hasAnother();

        if (payload != null) {
            return Response.ok(payload).header("Another-Event-In-Queue", hasAnother).build();
        }

        if (hasAnother) {
            return Response.status(Response.Status.NO_CONTENT).header("Another-Event-In-Queue", true).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("rooms/{memberId}/{roomId}/publish")
    @Operation(summary = "Publish an event to a room",
            description = "This endpoint allows a member to publish an event to existing message room with the provided publisher.")
    @Parameter(name = "memberId", description = "The identifier of the member", example = "EE/BUSINESS/123456789", required = true)
    @Parameter(name = "roomId", description = "The identifier of the room", example = "PatientPortal", required = true)
    @APIResponse(responseCode = "200", description = "Message was successfully published to a room")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response publishEventToRoom(@PathParam("memberId") String memberId,
                                       @PathParam("roomId") String roomId,
                                       @Valid PublishEventDTO publishEventDTO) {
        var request = PublishEventsUseCase.Request.builder()
            .publisher(new PublisherIdentifier(memberId))
            .room(new RoomIdentifier(roomId))
            .eventType(new EventTypeIdentifier(publishEventDTO.getEventType()))
            .payload(publishEventDTO.getContent())
            .build();

        var response = publishEventsUseCase.execute(request);

        var publishEventResponseDTO = new PublishEventResponseDTO();
        publishEventResponseDTO.setEventId(response.eventId());
        return Response.ok(publishEventResponseDTO).build();
    }
}
