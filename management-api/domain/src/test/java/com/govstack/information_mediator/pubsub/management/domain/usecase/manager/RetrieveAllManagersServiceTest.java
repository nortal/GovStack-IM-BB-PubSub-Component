package com.govstack.information_mediator.pubsub.management.domain.usecase.manager;

import com.govstack.information_mediator.pubsub.domain.entity.Manager;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.govstack.information_mediator.pubsub.domain.entity.IdentifierType.XROAD;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RetrieveAllManagersServiceTest {

    @Mock
    private FetchManagersPort fetchManagersPort;

    @InjectMocks
    private ExportManagersService exportManagersService;

    @Test
    void shouldRetrieveManagers() {
        // Given
        when(fetchManagersPort.fetchAllManagers()).thenReturn(List.of(
           Manager.builder().identifier("EE/BUSINESS/123456789").identifierType(XROAD).build(),
           Manager.builder().identifier("EE/BUSINESS/987654321").identifierType(XROAD).build()
        ));

        // Then
        Assertions.assertAll(
            () -> {
                var response = exportManagersService.execute();
                assertThat(response.managers(), hasSize(2));

                assertThat(response.managers().get(0).getIdentifier(), comparesEqualTo("EE/BUSINESS/123456789"));
                assertThat(response.managers().get(0).getIdentifierType(), comparesEqualTo(XROAD));

                assertThat(response.managers().get(1).getIdentifier(), comparesEqualTo("EE/BUSINESS/987654321"));
                assertThat(response.managers().get(1).getIdentifierType(), comparesEqualTo(XROAD));
            }
        );
    }

    @Test
    void shouldRetrieveEmptyList() {
        // Given
        when(fetchManagersPort.fetchAllManagers()).thenReturn(emptyList());

        // Then
        Assertions.assertAll(
            () -> {
                var response = exportManagersService.execute();
                assertThat(response.managers(), hasSize(0));
            }
        );
    }
}
