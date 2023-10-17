package com.govstack.information_mediator.pubsub.management.domain.usecase.manager;

import com.govstack.information_mediator.pubsub.commons.exception.ManagerAlreadyExistsException;
import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator;
import com.govstack.information_mediator.pubsub.domain.entity.IdentifierType;
import com.govstack.information_mediator.pubsub.domain.entity.Manager;
import com.govstack.information_mediator.pubsub.management.domain.port.CreateManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.usecase.manager.CreateManagersUseCase.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateManagersServiceTest {

    @Mock
    private FetchManagersPort fetchManagersPort;

    @Mock
    private CreateManagersPort createManagersPort;

    @InjectMocks
    private CreateManagersService createManagersService;

    @ParameterizedTest
    @EnumSource(value = IdentifierType.class, names = {"XROAD", "INTERNAL"})
    void shouldCreateManager(IdentifierType identifierType) {
        // Given
        var id = UUIDGenerator.randomUUID();

        when(fetchManagersPort.fetchManager("EE/BUSINESS/123456789")).thenReturn(Optional.empty());
        when(createManagersPort.createManager(any(Manager.class))).thenReturn(id);

        // Then
        Assertions.assertAll(
            () -> {
                var response = createManagersService.execute(new Request("EE/BUSINESS/123456789", identifierType));
                assertThat(response.manager().getId(), comparesEqualTo(id));
                assertThat(response.manager().getIdentifier(), comparesEqualTo("EE/BUSINESS/123456789"));
                assertThat(response.manager().getIdentifierType(), comparesEqualTo(identifierType));
            }
        );
    }

    @ParameterizedTest
    @EnumSource(value = IdentifierType.class, names = {"XROAD", "INTERNAL"})
    void shouldThrowExceptionWhenManagerExists(IdentifierType identifierType) {
        // Given
        var existingManager = Manager.builder().build();

        when(fetchManagersPort.fetchManager("EE/BUSINESS/123456789")).thenReturn(Optional.of(existingManager));

        // Then
        Assertions.assertAll(
            () -> assertThatThrownBy(() -> createManagersService.execute(new Request("EE/BUSINESS/123456789", identifierType)))
                .isInstanceOf(ManagerAlreadyExistsException.class)
                .hasMessage("Manager already exists"),
            () -> verifyNoInteractions(createManagersPort)
        );
    }
}
