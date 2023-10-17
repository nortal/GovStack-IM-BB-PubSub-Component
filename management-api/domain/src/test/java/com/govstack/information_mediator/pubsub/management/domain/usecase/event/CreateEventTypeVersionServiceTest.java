package com.govstack.information_mediator.pubsub.management.domain.usecase.event;

import com.govstack.information_mediator.pubsub.commons.JsonService;
import com.govstack.information_mediator.pubsub.commons.ObjectMapperFactory;
import com.govstack.information_mediator.pubsub.commons.exception.BusinessViolationException;
import com.govstack.information_mediator.pubsub.commons.exception.DuplicateResourceException;
import com.govstack.information_mediator.pubsub.commons.exception.EventTypeNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.ManagerNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator;
import com.govstack.information_mediator.pubsub.commons.validation.Violation;
import com.govstack.information_mediator.pubsub.domain.entity.EventType.Version;
import com.govstack.information_mediator.pubsub.domain.entity.id.EventTypeID;
import com.govstack.information_mediator.pubsub.domain.entity.id.EventTypeVersionID;
import com.govstack.information_mediator.pubsub.domain.entity.id.ManagerID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.EventTypeIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.port.CreateEventTypesPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchEventTypesPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.CreateEventTypeVersionUseCase.Request;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.CreateEventTypeVersionUseCase.Request.VersionData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateEventTypeVersionServiceTest {

    private final RoomIdentifier roomIdentifier = new RoomIdentifier("PatientPortal");
    private final ManagerIdentifier managerIdentifier = new ManagerIdentifier("EE/BUSINESS/123456789");
    private final EventTypeIdentifier eventTypeIdentifier = new EventTypeIdentifier("newPatient");
    private static final String defaultJsonSchema = "{\"$id\":\"https://example.com/sample.json\",\"type\":\"object\",\"title\":\"Default event type version\",\"$schema\":\"https://json-schema.org/draft/2020-12/schema\",\"description\":\"A default event type version that accepts everything\"}";

    private static final JsonService jsonService = new JsonService(ObjectMapperFactory.getObjectMapper());

    @Spy
    private EventTypeVersionSchemaService eventTypeVersionSchemaService = new EventTypeVersionSchemaService(jsonService);

    @Mock
    private FetchRoomsPort fetchRoomsPort;

    @Mock
    private FetchManagersPort fetchManagersPort;

    @Mock
    private FetchEventTypesPort fetchEventTypesPort;

    @Mock
    private CreateEventTypesPort createEventTypesPort;

    @InjectMocks
    private CreateEventTypeVersionService createEventTypeVersionService;

    @Test
    void shouldCreateEventTypeVersion() {
        // Given
        var request = defaultRequest();

        var managerID = new ManagerID(UUIDGenerator.randomUUID());
        when(fetchManagersPort.fetchManagerID(request.getManagerIdentifier())).thenReturn(Optional.of(managerID));

        var roomID = new RoomID(UUIDGenerator.randomUUID());
        when(fetchRoomsPort.fetchRoomID(request.getRoomIdentifier(), managerID)).thenReturn(Optional.of(roomID));

        var eventTypeID = new EventTypeID(UUIDGenerator.randomUUID());
        when(fetchEventTypesPort.fetchEventTypeID(request.getEventTypeIdentifier(), roomID)).thenReturn(Optional.of(eventTypeID));

        when(fetchEventTypesPort.fetchEventTypeVersionID(eventTypeID, 12)).thenReturn(Optional.empty());

        // Then
        Assertions.assertAll(
            () -> assertThatNoException().isThrownBy(() -> createEventTypeVersionService.execute(request)),
            () -> {
                var captor = ArgumentCaptor.forClass(Version.class);
                verify(createEventTypesPort).createEventTypeVersion(captor.capture(), eq(eventTypeID));

                var version = captor.getValue();
                assertThat(version.getVersionNo(), equalTo(12));
                assertThat(version.getJsonSchema().getSchemaNode().toString(), equalTo(defaultJsonSchema));
            }
        );
    }

    @Test
    void shouldThrowExceptionWhenSchemaIsIncorrect() {
        // Given
        var request = withIncorrectSchema();

        var managerID = new ManagerID(UUIDGenerator.randomUUID());
        when(fetchManagersPort.fetchManagerID(request.getManagerIdentifier())).thenReturn(Optional.of(managerID));

        var roomID = new RoomID(UUIDGenerator.randomUUID());
        when(fetchRoomsPort.fetchRoomID(request.getRoomIdentifier(), managerID)).thenReturn(Optional.of(roomID));

        var eventTypeID = new EventTypeID(UUIDGenerator.randomUUID());
        when(fetchEventTypesPort.fetchEventTypeID(request.getEventTypeIdentifier(), roomID)).thenReturn(Optional.of(eventTypeID));

        when(fetchEventTypesPort.fetchEventTypeVersionID(eventTypeID, 12)).thenReturn(Optional.empty());

        // Then
        Assertions.assertAll(
            () -> assertThatThrownBy(() -> createEventTypeVersionService.execute(request))
                .isInstanceOf(BusinessViolationException.class)
                .hasMessage(Violation.EVENT_TYPE_VERSION_SCHEMA_INCORRECT_FORMAT),

            () -> verifyNoInteractions(createEventTypesPort)
        );
    }

    @Test
    void shouldThrowExceptionWhenVersionAlreadyExists() {
        // Given
        var request = defaultRequest();

        var managerID = new ManagerID(UUIDGenerator.randomUUID());
        when(fetchManagersPort.fetchManagerID(request.getManagerIdentifier())).thenReturn(Optional.of(managerID));

        var roomID = new RoomID(UUIDGenerator.randomUUID());
        when(fetchRoomsPort.fetchRoomID(request.getRoomIdentifier(), managerID)).thenReturn(Optional.of(roomID));

        var eventTypeID = new EventTypeID(UUIDGenerator.randomUUID());
        when(fetchEventTypesPort.fetchEventTypeID(request.getEventTypeIdentifier(), roomID)).thenReturn(Optional.of(eventTypeID));

        var eventTypeVersionId = new EventTypeVersionID(UUIDGenerator.randomUUID());
        when(fetchEventTypesPort.fetchEventTypeVersionID(eventTypeID, 12)).thenReturn(Optional.of(eventTypeVersionId));

        // Then
        Assertions.assertAll(
            () -> assertThatThrownBy(() -> createEventTypeVersionService.execute(defaultRequest()))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("The version already exists for the event type"),

            () -> verifyNoInteractions(createEventTypesPort)
        );
    }

    @Test
    void shouldThrowExceptionWhenEventTypeIsNotFound() {
        // Given
        var request = defaultRequest();

        var managerID = new ManagerID(UUIDGenerator.randomUUID());
        when(fetchManagersPort.fetchManagerID(request.getManagerIdentifier())).thenReturn(Optional.of(managerID));

        var roomID = new RoomID(UUIDGenerator.randomUUID());
        when(fetchRoomsPort.fetchRoomID(request.getRoomIdentifier(), managerID)).thenReturn(Optional.of(roomID));

        when(fetchEventTypesPort.fetchEventTypeID(request.getEventTypeIdentifier(), roomID)).thenReturn(Optional.empty());

        // Then
        Assertions.assertAll(
            () -> assertThatThrownBy(() -> createEventTypeVersionService.execute(request))
                .isInstanceOf(EventTypeNotFoundException.class)
                .hasMessage("Event Type was not found"),

            () -> verifyNoInteractions(createEventTypesPort)
        );
    }

    @Test
    void shouldThrowExceptionWhenRoomIsNotFound() {
        // Given
        var request = defaultRequest();

        var managerID = new ManagerID(UUIDGenerator.randomUUID());
        when(fetchManagersPort.fetchManagerID(request.getManagerIdentifier())).thenReturn(Optional.of(managerID));

        when(fetchRoomsPort.fetchRoomID(request.getRoomIdentifier(), managerID)).thenReturn(Optional.empty());

        // Then
        Assertions.assertAll(
            () -> assertThatThrownBy(() -> createEventTypeVersionService.execute(request))
                .isInstanceOf(RoomNotFoundException.class)
                .hasMessage("Room was not found"),

            () -> verifyNoInteractions(createEventTypesPort, fetchEventTypesPort)
        );
    }

    @Test
    void shouldThrowExceptionWhenManagerIsNotFound() {
        // Given
        var request = defaultRequest();

        when(fetchManagersPort.fetchManagerID(request.getManagerIdentifier())).thenReturn(Optional.empty());

        // Then
        Assertions.assertAll(
            () -> assertThatThrownBy(() -> createEventTypeVersionService.execute(request))
                .isInstanceOf(ManagerNotFoundException.class)
                .hasMessage("Manager was not found"),

            () -> verifyNoInteractions(createEventTypesPort, fetchEventTypesPort, fetchRoomsPort)
        );
    }

    private Request defaultRequest() {
        return Request.builder()
            .roomIdentifier(roomIdentifier)
            .managerIdentifier(managerIdentifier)
            .eventTypeIdentifier(eventTypeIdentifier)
            .version(VersionData.builder()
                .versionNo(12)
                .jsonSchema(defaultJsonSchema)
                .build())
            .build();
    }

    private Request withIncorrectSchema() {
        return Request.builder()
            .roomIdentifier(roomIdentifier)
            .managerIdentifier(managerIdentifier)
            .eventTypeIdentifier(eventTypeIdentifier)
            .version(VersionData.builder()
                .versionNo(12)
                .jsonSchema("{\"type\":\"array\"}")
                .build())
            .build();
    }
}
