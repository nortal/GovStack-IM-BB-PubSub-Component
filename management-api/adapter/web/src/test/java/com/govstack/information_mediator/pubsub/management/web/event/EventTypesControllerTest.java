package com.govstack.information_mediator.pubsub.management.web.event;

import com.govstack.information_mediator.pubsub.commons.JsonService;
import com.govstack.information_mediator.pubsub.commons.ObjectMapperFactory;
import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.CreateEventTypesUseCase;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.DeleteEventTypesUseCase;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.GetEventTypesUseCase;
import com.govstack.information_mediator.pubsub.management.domain.view.EventTypeView;
import com.govstack.information_mediator.pubsub.management.web.paging.PagingSortingParametersDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(MockitoExtension.class)
class EventTypesControllerTest {

    private static final String versionSchema = "{\"$id\":\"https://example.com/sample.json\",\"type\":[\"string\",\"number\",\"integer\",\"object\",\"array\",\"boolean\"],\"title\":\"Default event type version\",\"$schema\":\"https://json-schema.org/draft/2020-12/schema\",\"description\":\"A default event type version that accepts everything\"}";
    private static final JsonService jsonService = new JsonService(ObjectMapperFactory.getObjectMapper());

    @Mock
    private CreateEventTypesUseCase createEventTypesUseCase;

    @Mock
    private DeleteEventTypesUseCase deleteEventTypesUseCase;

    @Mock
    private GetEventTypesUseCase getEventTypesUseCase;

    @InjectMocks
    private EventTypesController eventTypesController;

    @Test
    void testGetEventTypeOverviewsUseCase() {
        // Given
        var firstEventType = EventTypeView.builder()
            .id(UUID.randomUUID())
            .identifier("newPatient")
            .createdBy("system-administrator")
            .createdAt(Instant.now())
            .versions(1)
            .build();

        var secondEventType = EventTypeView.builder()
            .id(UUID.randomUUID())
            .identifier("newPatient2")
            .createdBy("system-administrator-2")
            .createdAt(Instant.now())
            .versions(3)
            .build();

        var result = Page.<EventTypeView>builder().content(List.of(firstEventType, secondEventType)).build();
        var useCaseResponse = new GetEventTypesUseCase.Response(result);

        when(getEventTypesUseCase.execute(any(GetEventTypesUseCase.Request.class))).thenReturn(useCaseResponse);

        var params = new PagingSortingParametersDTO();

        Assertions.assertAll(
            () -> {
                var response = eventTypesController.getEventTypesViaXRoadNetwork("EE/BUSINESS/123456789", "PatientPortal", params);
                assertThat(response.getStatusCode(), equalTo(OK));

                var eventTypes = Objects.requireNonNull(response.getBody()).eventTypes;
                assertThat(eventTypes, hasSize(2));

                assertThat(eventTypes.get(0).getId(), equalTo(firstEventType.getId()));
                assertThat(eventTypes.get(0).getIdentifier(), equalTo(firstEventType.getIdentifier()));
                assertThat(eventTypes.get(0).getCreatedAt(), equalTo(firstEventType.getCreatedAt()));
                assertThat(eventTypes.get(0).getCreatedBy(), equalTo(firstEventType.getCreatedBy()));
                assertThat(eventTypes.get(0).getVersions(), equalTo(1));

                assertThat(eventTypes.get(1).getId(), equalTo(secondEventType.getId()));
                assertThat(eventTypes.get(1).getIdentifier(), equalTo(secondEventType.getIdentifier()));
                assertThat(eventTypes.get(1).getCreatedAt(), equalTo(secondEventType.getCreatedAt()));
                assertThat(eventTypes.get(1).getCreatedBy(), equalTo(secondEventType.getCreatedBy()));
                assertThat(eventTypes.get(1).getVersions(), equalTo(3));
            },
            () -> {
                var captor = ArgumentCaptor.forClass(GetEventTypesUseCase.Request.class);
                verify(getEventTypesUseCase).execute(captor.capture());

                var request = captor.getValue();
                assertThat(request.manager().getIdentifier(), equalTo("EE/BUSINESS/123456789"));
                assertThat(request.room().getIdentifier(), equalTo("PatientPortal"));
            }
        );
    }

    @Test
    void testCreateEventTypeUseCase() {
        Assertions.assertAll(
            () -> {
                var eventType = CreateEventTypeDTO.builder()
                    .identifier("newPatient")
                    .versionNo(1)
                    .schema(versionSchema)
                    .build();

                var response = eventTypesController.createEventTypeViaXRoadNetwork("EE/BUSINESS/123456789", "PatientPortal", eventType);
                assertThat(response.getStatusCode(), equalTo(OK));
            },
            () -> {
                var captor = ArgumentCaptor.forClass(CreateEventTypesUseCase.Request.class);
                verify(createEventTypesUseCase).execute(captor.capture());

                var request = captor.getValue();
                assertThat(request.getManager().getIdentifier(), equalTo("EE/BUSINESS/123456789"));
                assertThat(request.getRoom().getIdentifier(), equalTo("PatientPortal"));
                assertThat(request.getEventType().getIdentifier(), equalTo("newPatient"));

                var version = request.getEventType().getVersion();
                assertThat(version.getVersionNo(), equalTo(1));
                assertThat(version.getJsonSchema().toString(), equalTo(versionSchema));
            }
        );
    }

    @Test
    void testDeleteEventTypeUseCase() {
        Assertions.assertAll(
            () -> {
                var response = eventTypesController.deleteEventTypeViaXRoad("EE/BUSINESS/123456789", "PatientPortal", "newPatient");
                assertThat(response.getStatusCode(), equalTo(OK));
            },
            () -> {
                var captor = ArgumentCaptor.forClass(DeleteEventTypesUseCase.Request.class);
                verify(deleteEventTypesUseCase).execute(captor.capture());

                var request = captor.getValue();
                assertThat(request.getManager().getIdentifier(), equalTo("EE/BUSINESS/123456789"));
                assertThat(request.getRoom().getIdentifier(), equalTo("PatientPortal"));
                assertThat(request.getEventType().getIdentifier(), equalTo("newPatient"));
            }
        );
    }
}
