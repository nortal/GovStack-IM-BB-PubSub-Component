package com.govstack.information_mediator.pubsub.management.domain.usecase.event;

import com.govstack.information_mediator.pubsub.commons.JsonService;
import com.govstack.information_mediator.pubsub.commons.ObjectMapperFactory;
import com.govstack.information_mediator.pubsub.commons.exception.BusinessViolationException;
import com.govstack.information_mediator.pubsub.commons.exception.DuplicateResourceException;
import com.govstack.information_mediator.pubsub.commons.exception.ManagerNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.commons.validation.Violation;
import com.govstack.information_mediator.pubsub.domain.entity.EventType;
import com.govstack.information_mediator.pubsub.domain.entity.Manager;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.management.domain.port.CreateEventTypesPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchEventTypesPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.CreateEventTypesUseCase.Request;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.CreateEventTypesUseCase.Request.EventTypeData;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.CreateEventTypesUseCase.Request.EventTypeData.VersionData;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.CreateEventTypesUseCase.Request.ManagerData;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.CreateEventTypesUseCase.Request.RoomData;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateEventTypesServiceTest {

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
    private CreateEventTypesService createEventTypesService;

    @Test
    void shouldCreateEventType() {
        // Given
        var manager = Manager.builder().build();
        when(fetchManagersPort.fetchManager("EE/BUSINESS/123456789")).thenReturn(Optional.of(manager));

        var room = Room.builder().build();
        when(fetchRoomsPort.fetchRoom("PatientPortal", manager)).thenReturn(Optional.of(room));

        when(fetchEventTypesPort.fetchEventType("newPatient", room)).thenReturn(Optional.empty());

        // When
        Assertions.assertAll(
            () -> assertThatNoException().isThrownBy(() -> createEventTypesService.execute(defaultRequest())),
            () -> {
                var captor = ArgumentCaptor.forClass(EventType.class);
                verify(createEventTypesPort).createEventType(captor.capture());
                var eventType = captor.getValue();

                assertThat(eventType.getIdentifier(), equalTo("newPatient"));

                var version = eventType.getVersion();
                assertThat(version.getVersionNo(), equalTo(1));
                assertThat(version.getJsonSchema().getSchemaNode().toString(), equalTo(defaultJsonSchema));
            }
        );
    }

    @Test
    void shouldThrowExceptionWhenEventTypeVersionSchemaIsIncorrect() {
        // Given
        var manager = Manager.builder().build();
        when(fetchManagersPort.fetchManager("EE/BUSINESS/123456789")).thenReturn(Optional.of(manager));

        var room = Room.builder().build();
        when(fetchRoomsPort.fetchRoom("PatientPortal", manager)).thenReturn(Optional.of(room));

        when(fetchEventTypesPort.fetchEventType("newPatient", room)).thenReturn(Optional.empty());

        // When
        Assertions.assertAll(
            () -> assertThatThrownBy(() -> createEventTypesService.execute(withIncorrectSchema()))
                .isInstanceOf(BusinessViolationException.class)
                .hasMessage(Violation.EVENT_TYPE_VERSION_SCHEMA_INCORRECT_FORMAT),
            () -> verifyNoInteractions(createEventTypesPort)
        );
    }

    @Test
    void shouldThrowExceptionWhenEventTypeAlreadyExists() {
        // Given
        var manager = Manager.builder().build();
        when(fetchManagersPort.fetchManager("EE/BUSINESS/123456789")).thenReturn(Optional.of(manager));

        var room = Room.builder().build();
        when(fetchRoomsPort.fetchRoom("PatientPortal", manager)).thenReturn(Optional.of(room));

        var eventType = EventType.builder().build();
        when(fetchEventTypesPort.fetchEventType("newPatient", room)).thenReturn(Optional.of(eventType));

        // When
        Assertions.assertAll(
            () -> assertThatThrownBy(() -> createEventTypesService.execute(defaultRequest()))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Event Type already exists"),

            () -> verifyNoInteractions(createEventTypesPort)
        );
    }

    @Test
    void shouldThrowExceptionWhenRoomIsNotFound() {
        // Given
        var manager = Manager.builder().build();
        when(fetchManagersPort.fetchManager("EE/BUSINESS/123456789")).thenReturn(Optional.of(manager));

        when(fetchRoomsPort.fetchRoom("PatientPortal", manager)).thenReturn(Optional.empty());

        // When
        Assertions.assertAll(
            () -> assertThatThrownBy(() -> createEventTypesService.execute(defaultRequest()))
                .isInstanceOf(RoomNotFoundException.class)
                .hasMessage("Room was not found"),

            () -> verifyNoInteractions(fetchEventTypesPort, createEventTypesPort)
        );
    }

    @Test
    void shouldThrowExceptionWhenManagerIsNotFound() {
        // Given
        when(fetchManagersPort.fetchManager("EE/BUSINESS/123456789")).thenReturn(Optional.empty());

        // When
        Assertions.assertAll(
            () -> assertThatThrownBy(() -> createEventTypesService.execute(defaultRequest()))
                .isInstanceOf(ManagerNotFoundException.class)
                .hasMessage("Manager was not found"),

            () -> verifyNoInteractions(fetchRoomsPort, fetchEventTypesPort, createEventTypesPort)
        );
    }

    private static Request defaultRequest() {
        return Request.builder()
            .manager(ManagerData.of("EE/BUSINESS/123456789"))
            .room(RoomData.of("PatientPortal"))
            .eventType(EventTypeData.builder()
                .identifier("newPatient")
                .version(VersionData.of(1, defaultJsonSchema))
                .build())
            .build();
    }


    private static Request withIncorrectSchema() {
        return Request.builder()
            .manager(ManagerData.of("EE/BUSINESS/123456789"))
            .room(RoomData.of("PatientPortal"))
            .eventType(EventTypeData.builder()
                .identifier("newPatient")
                .version(VersionData.of(1, "{\"type\":\"array\"}"))
                .build())
            .build();
    }
}
