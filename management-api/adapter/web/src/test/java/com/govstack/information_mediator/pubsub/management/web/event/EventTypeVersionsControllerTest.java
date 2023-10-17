package com.govstack.information_mediator.pubsub.management.web.event;

import com.govstack.information_mediator.pubsub.commons.JsonService;
import com.govstack.information_mediator.pubsub.commons.ObjectMapperFactory;
import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.CreateEventTypeVersionUseCase;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.DeleteEventTypeVersionsUseCase;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.GetEventTypeVersionsUseCase;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.UpdateEventTypeVersionUseCase;
import com.govstack.information_mediator.pubsub.management.domain.view.EventTypeVersionOverview;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(MockitoExtension.class)
public class EventTypeVersionsControllerTest {

    private static final String versionSchema = "{\"$id\":\"https://example.com/sample.json\",\"type\":[\"string\",\"number\",\"integer\",\"object\",\"array\",\"boolean\"],\"title\":\"Default event type version\",\"$schema\":\"https://json-schema.org/draft/2020-12/schema\",\"description\":\"A default event type version that accepts everything\"}";


    @Mock
    private GetEventTypeVersionsUseCase getEventTypeVersionsUseCase;

    @Mock
    private CreateEventTypeVersionUseCase createEventTypeVersionUseCase;

    @Mock
    private UpdateEventTypeVersionUseCase updateEventTypeVersionUseCase;

    @Mock
    private DeleteEventTypeVersionsUseCase deleteEventTypeVersionsUseCase;

    @InjectMocks
    private EventTypeVersionsController eventTypeVersionsController;

    @Test
    void testGetEventTypeVersionsUseCase() {
        // Given
        var firstVersion = EventTypeVersionOverview.builder()
            .id(UUIDGenerator.randomUUID())
            .eventTypeId(UUIDGenerator.randomUUID())
            .versionNo(12)
            .jsonSchema("mock json schema")
            .createdAt(Instant.now())
            .createdBy("system-administrator")
            .modifiedAt(Instant.now())
            .modifiedBy("service-administrator-1")
            .build();

        var secondVersion = EventTypeVersionOverview.builder()
            .id(UUIDGenerator.randomUUID())
            .eventTypeId(UUIDGenerator.randomUUID())
            .versionNo(5)
            .jsonSchema("mock json schema 2")
            .createdAt(Instant.now())
            .createdBy("service-administrator")
            .modifiedAt(Instant.now())
            .modifiedBy("service-administrator-2")
            .build();

        when(getEventTypeVersionsUseCase.execute(any(GetEventTypeVersionsUseCase.Request.class)))
            .thenReturn(new GetEventTypeVersionsUseCase.Response(List.of(firstVersion, secondVersion)));

        // Then
        Assertions.assertAll(
            () -> {
                var response = eventTypeVersionsController.getEventTypeVersionsViaXRoad("EE/BUSINESS/123456789", "PatientPortal", "newPatient");
                assertThat(response.getStatusCode(), equalTo(OK));

                var eventTypeVersions = response.getBody();
                assertThat(eventTypeVersions, hasSize(2));

                assertThat(eventTypeVersions.get(0).getVersionNo(), equalTo(firstVersion.getVersionNo()));
                assertThat(eventTypeVersions.get(0).getJsonSchema(), equalTo(firstVersion.getJsonSchema()));
                assertThat(eventTypeVersions.get(0).getCreatedAt(), equalTo(firstVersion.getCreatedAt()));
                assertThat(eventTypeVersions.get(0).getCreatedBy(), equalTo(firstVersion.getCreatedBy()));
                assertThat(eventTypeVersions.get(0).getModifiedAt(), equalTo(firstVersion.getModifiedAt()));
                assertThat(eventTypeVersions.get(0).getModifiedBy(), equalTo(firstVersion.getModifiedBy()));

                assertThat(eventTypeVersions.get(1).getVersionNo(), equalTo(secondVersion.getVersionNo()));
                assertThat(eventTypeVersions.get(1).getJsonSchema(), equalTo(secondVersion.getJsonSchema()));
                assertThat(eventTypeVersions.get(1).getCreatedAt(), equalTo(secondVersion.getCreatedAt()));
                assertThat(eventTypeVersions.get(1).getCreatedBy(), equalTo(secondVersion.getCreatedBy()));
                assertThat(eventTypeVersions.get(1).getModifiedAt(), equalTo(secondVersion.getModifiedAt()));
                assertThat(eventTypeVersions.get(1).getModifiedBy(), equalTo(secondVersion.getModifiedBy()));
            },
            () -> {
                var captor = ArgumentCaptor.forClass(GetEventTypeVersionsUseCase.Request.class);
                verify(getEventTypeVersionsUseCase).execute(captor.capture());

                var request = captor.getValue();
                assertThat(request.getManagerIdentifier().getIdentifier(), equalTo("EE/BUSINESS/123456789"));
                assertThat(request.getRoomIdentifier().getIdentifier(), equalTo("PatientPortal"));
                assertThat(request.getEventTypeIdentifier().getIdentifier(), equalTo("newPatient"));
            }
        );
    }

    @Test
    void testCreateEventTypeVersionUseCase() {
        Assertions.assertAll(
            () -> {
                var eventTypeVersion = CreateEventTypeVersionDTO.builder()
                    .versionNo(12)
                    .schema(versionSchema)
                    .build();

                var response = eventTypeVersionsController.createEventTypeVersionViaXRoad("EE/BUSINESS/123456789", "PatientPortal", "newPatient", eventTypeVersion);
                assertThat(response.getStatusCode(), equalTo(OK));
            },
            () -> {
                var captor = ArgumentCaptor.forClass(CreateEventTypeVersionUseCase.Request.class);
                verify(createEventTypeVersionUseCase).execute(captor.capture());

                var request = captor.getValue();
                assertThat(request.getManagerIdentifier().getIdentifier(), equalTo("EE/BUSINESS/123456789"));
                assertThat(request.getRoomIdentifier().getIdentifier(), equalTo("PatientPortal"));
                assertThat(request.getEventTypeIdentifier().getIdentifier(), equalTo("newPatient"));

                var version = request.getVersion();
                assertThat(version.getVersionNo(), equalTo(12));
                assertThat(version.getJsonSchema().toString(), equalTo(versionSchema));
            }
        );
    }

    @Test
    void testUpdateEventTypeVersionUseCase() {
        Assertions.assertAll(
            () -> {
                var eventTypeVersion = UpdateEventTypeVersionDTO.builder()
                    .versionNo(12)
                    .schema(versionSchema)
                    .build();

                var response = eventTypeVersionsController.updateEventTypeVersionViaXRoad("EE/BUSINESS/123456789", "PatientPortal", "newPatient", eventTypeVersion);
                assertThat(response.getStatusCode(), equalTo(OK));
            },
            () -> {
                var captor = ArgumentCaptor.forClass(UpdateEventTypeVersionUseCase.Request.class);
                verify(updateEventTypeVersionUseCase).execute(captor.capture());

                var request = captor.getValue();
                assertThat(request.getManagerIdentifier().getIdentifier(), equalTo("EE/BUSINESS/123456789"));
                assertThat(request.getRoomIdentifier().getIdentifier(), equalTo("PatientPortal"));
                assertThat(request.getEventTypeIdentifier().getIdentifier(), equalTo("newPatient"));

                var version = request.getVersion();
                assertThat(version.getVersionNo(), equalTo(12));
                assertThat(version.getJsonSchema().toString(), equalTo(versionSchema));
            }
        );
    }

    @Test
    void testDeleteEventTypeVersionUseCase() {
        Assertions.assertAll(
            () -> {
                var response = eventTypeVersionsController.deleteEventTypeVersionViaXRoad("EE/BUSINESS/123456789", "PatientPortal", "newPatient", 12);
                assertThat(response.getStatusCode(), equalTo(OK));
            },
            () -> {
                var captor = ArgumentCaptor.forClass(DeleteEventTypeVersionsUseCase.Request.class);
                verify(deleteEventTypeVersionsUseCase).execute(captor.capture());

                var request = captor.getValue();
                assertThat(request.getManagerIdentifier().getIdentifier(), equalTo("EE/BUSINESS/123456789"));
                assertThat(request.getRoomIdentifier().getIdentifier(), equalTo("PatientPortal"));
                assertThat(request.getEventTypeIdentifier().getIdentifier(), equalTo("newPatient"));
                assertThat(request.getVersionNo(), equalTo(12));
            }
        );
    }
}
