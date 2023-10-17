package com.govstack.information_mediator.pubsub.management.web.manager;

import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator;
import com.govstack.information_mediator.pubsub.domain.entity.Manager;
import com.govstack.information_mediator.pubsub.management.domain.usecase.manager.CreateManagersUseCase;
import com.govstack.information_mediator.pubsub.management.domain.usecase.manager.CreateManagersUseCase.Request;
import com.govstack.information_mediator.pubsub.management.domain.usecase.manager.CreateManagersUseCase.Response;
import com.govstack.information_mediator.pubsub.management.domain.usecase.manager.ExportManagersUseCase;
import com.govstack.information_mediator.pubsub.management.domain.usecase.manager.TerminateManagersUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.govstack.information_mediator.pubsub.domain.entity.IdentifierType.INTERNAL;
import static com.govstack.information_mediator.pubsub.domain.entity.IdentifierType.XROAD;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@ExtendWith(MockitoExtension.class)
class ManagersControllerTest {

    @Mock
    private ExportManagersUseCase exportManagersUseCase;

    @Mock
    private CreateManagersUseCase createManagersUseCase;

    @Mock
    private TerminateManagersUseCase terminateManagersUseCase;

    @InjectMocks
    private ManagersController managersController;

    @Test
    void testRetrieveAllManagersUseCaseIsCalled() {
        // Given
        when(exportManagersUseCase.execute()).thenReturn(new ExportManagersUseCase.Response(List.of(
            Manager.builder().identifier("EE/BUSINESS/123456789").identifierType(XROAD).build(),
            Manager.builder().identifier("EE/BUSINESS/987654321").identifierType(INTERNAL).build()
        )));

        // Then
        Assertions.assertAll(
            () -> {
                var managers = managersController.retrieveAllManagers().getBody();

                assertThat(managers, hasSize(2));

                assertThat(managers.get(0).getIdentifier(), comparesEqualTo("EE/BUSINESS/123456789"));
                assertThat(managers.get(0).getIdentifierType(), comparesEqualTo("XROAD"));

                assertThat(managers.get(1).getIdentifier(), comparesEqualTo("EE/BUSINESS/987654321"));
                assertThat(managers.get(1).getIdentifierType(), comparesEqualTo("INTERNAL"));
            }
        );
    }

    @Test
    void testRetrieveEmptyListWhenAllManagersUseCaseIsCalled() {
        // Given
        when(exportManagersUseCase.execute()).thenReturn(new ExportManagersUseCase.Response(List.of()));

        // Then
        Assertions.assertAll(
            () -> {
                var managers = managersController.retrieveAllManagers().getBody();
                assertThat(managers, hasSize(0));
            }
        );
    }

    @Test
    void testCreateManagerUseCaseIsCalled() {
        // Given
        var manager = Manager.builder()
            .id(UUIDGenerator.randomUUID())
            .identifier("EE/BUSINESS/123456789")
            .identifierType(XROAD)
            .build();

        when(createManagersUseCase.execute(any(Request.class))).thenReturn(new Response(manager));

        var createManagerDTO = CreateManagerDTO.builder()
            .identifier("EE/BUSINESS/123456789")
            .identifierType("XROAD")
            .build();

        // Then
        Assertions.assertAll(
            () -> {
                var response = managersController.createManager(createManagerDTO);
                assertThat(response.getBody().getIdentifier(), comparesEqualTo("EE/BUSINESS/123456789"));
                assertThat(response.getBody().getIdentifierType(), comparesEqualTo("XROAD"));
            },
            () -> {
                var captor = ArgumentCaptor.forClass(CreateManagersUseCase.Request.class);
                verify(createManagersUseCase).execute(captor.capture());

                var request = captor.getValue();
                assertThat(request.identifier(), comparesEqualTo("EE/BUSINESS/123456789"));
                assertThat(request.identifierType(), comparesEqualTo(XROAD));
            }
        );
    }

    @Test
    void testTerminateManagerUseCaseIsCalled() {
        // Given
        var managerId = UUIDGenerator.randomUUID();
        // Then
        Assertions.assertAll(
            () -> {
                var response = managersController.terminateManager(managerId);
                assertThat(response.getStatusCode(), equalTo(NO_CONTENT));
            },
            () -> {
                var captor = ArgumentCaptor.forClass(TerminateManagersUseCase.Request.class);
                verify(terminateManagersUseCase).execute(captor.capture());

                var request = captor.getValue();
                assertThat(request.id(), comparesEqualTo(managerId));
            }
        );
    }
}
