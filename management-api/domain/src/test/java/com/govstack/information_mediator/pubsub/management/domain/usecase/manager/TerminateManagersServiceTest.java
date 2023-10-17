package com.govstack.information_mediator.pubsub.management.domain.usecase.manager;

import com.govstack.information_mediator.pubsub.commons.exception.ManagerNotFoundException;
import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator;
import com.govstack.information_mediator.pubsub.domain.entity.Manager;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.TerminateManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.usecase.manager.TerminateManagersUseCase.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TerminateManagersServiceTest {

    @Mock
    private FetchManagersPort fetchManagersPort;

    @Mock
    private TerminateManagersPort terminateManagersPort;

    @InjectMocks
    private TerminateManagersService terminateManagersService;

    @Test
    void shouldTerminateManager() {
        // Given
        var managerId = UUIDGenerator.randomUUID();
        var manager = Manager.builder().build();
        when(fetchManagersPort.fetchManager(managerId)).thenReturn(Optional.of(manager));

        // Then
        Assertions.assertAll(
            () -> {
                terminateManagersService.execute(new Request(managerId));
                verify(terminateManagersPort).terminate(manager);
            }
        );
    }

    @Test
    void shouldThrowExceptionWhenManagerIsNotFound() {
        // Given
        var managerId = UUIDGenerator.randomUUID();
        when(fetchManagersPort.fetchManager(managerId)).thenReturn(Optional.empty());

        // Then
        Assertions.assertAll(
            () -> assertThatThrownBy(() -> terminateManagersService.execute(new Request(managerId)))
                .isInstanceOf(ManagerNotFoundException.class)
                .hasMessage("Manager was not found"),
            () -> verifyNoInteractions(terminateManagersPort)
        );
    }
}
