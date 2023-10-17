package com.govstack.information_mediator.pubsub.management.web.publisher;

import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator;
import com.govstack.information_mediator.pubsub.management.domain.usecase.publisher.GetPublishersUseCase;
import com.govstack.information_mediator.pubsub.management.domain.view.PublisherView;
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
import java.util.UUID;

import static com.govstack.information_mediator.pubsub.domain.entity.IdentifierType.INTERNAL;
import static com.govstack.information_mediator.pubsub.domain.entity.IdentifierType.XROAD;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PublishersControllerTest {

    @Mock
    private GetPublishersUseCase getPublishersUseCase;

    @InjectMocks
    private PublishersController publishersController;

    @Test
    void testPublishersUseCase() {
        // Given
        var constraint1 = PublisherView.Constraint.builder()
            .eventTypeId(UUIDGenerator.randomUUID())
            .eventTypeIdentifier("newPatient").
            createdAt(Instant.now())
            .build();

        var constraint2 = PublisherView.Constraint.builder()
            .eventTypeId(UUIDGenerator.randomUUID())
            .eventTypeIdentifier("medicalRecord")
            .createdAt(Instant.now())
            .build();

        var constraint3 = PublisherView.Constraint.builder()
            .eventTypeId(UUIDGenerator.randomUUID())
            .eventTypeIdentifier("birthCertificate")
            .createdAt(Instant.now())
            .build();

        var publisher1 = PublisherView.builder()
            .id(UUID.randomUUID())
            .identifier("EE/PUB/111")
            .identifierType(XROAD)
            .constraints(List.of(constraint1, constraint2))
            .createdAt(Instant.now())
            .createdBy("jane.doe")
            .build();

        var publisher2 = PublisherView.builder()
            .id(UUID.randomUUID())
            .identifier("EE/PUB/222")
            .identifierType(INTERNAL)
            .constraints(List.of(constraint3))
            .createdAt(Instant.now())
            .createdBy("h2o")
            .build();

        var result = Page.<PublisherView>builder().content(List.of(publisher1, publisher2)).build();
        var mockResponse = new GetPublishersUseCase.Response(result);
        when(getPublishersUseCase.execute(any(GetPublishersUseCase.Request.class))).thenReturn(mockResponse);

        Assertions.assertAll(
            () -> {
                var response = publishersController.getPublishersViaXroad("/EE/MEMBER/123456789", "PatientPortal", new PagingSortingParametersDTO());
                var publishers = response.getBody().getPublishers();

                assertThat(publishers, hasSize(2));

                assertThat(publishers.get(0).getId(), equalTo(publisher1.getId()));
                assertThat(publishers.get(0).getIdentifier(), equalTo(publisher1.getIdentifier()));
                assertThat(publishers.get(0).getIdentifierType(), equalTo(publisher1.getIdentifierType().name()));
                assertThat(publishers.get(0).getCreatedAt(), equalTo(publisher1.getCreatedAt()));
                assertThat(publishers.get(0).getCreatedBy(), equalTo(publisher1.getCreatedBy()));
                assertThat(publishers.get(0).getConstraints(), hasSize(2));
                assertThat(publishers.get(0).getConstraints().get(0).getEventTypeId(), equalTo(constraint1.getEventTypeId()));
                assertThat(publishers.get(0).getConstraints().get(0).getEventIdentifier(), equalTo(constraint1.getEventTypeIdentifier()));
                assertThat(publishers.get(0).getConstraints().get(0).getCreatedAt(), equalTo(constraint1.getCreatedAt()));
                assertThat(publishers.get(0).getConstraints().get(1).getEventTypeId(), equalTo(constraint2.getEventTypeId()));
                assertThat(publishers.get(0).getConstraints().get(1).getEventIdentifier(), equalTo(constraint2.getEventTypeIdentifier()));
                assertThat(publishers.get(0).getConstraints().get(1).getCreatedAt(), equalTo(constraint2.getCreatedAt()));


                assertThat(publishers.get(1).getId(), equalTo(publisher2.getId()));
                assertThat(publishers.get(1).getIdentifier(), equalTo(publisher2.getIdentifier()));
                assertThat(publishers.get(1).getIdentifierType(), equalTo(publisher2.getIdentifierType().name()));
                assertThat(publishers.get(1).getCreatedAt(), equalTo(publisher2.getCreatedAt()));
                assertThat(publishers.get(1).getCreatedBy(), equalTo(publisher2.getCreatedBy()));
                assertThat(publishers.get(1).getConstraints(), hasSize(1));
                assertThat(publishers.get(1).getConstraints().get(0).getEventTypeId(), equalTo(constraint3.getEventTypeId()));
                assertThat(publishers.get(1).getConstraints().get(0).getEventIdentifier(), equalTo(constraint3.getEventTypeIdentifier()));
                assertThat(publishers.get(1).getConstraints().get(0).getCreatedAt(), equalTo(constraint3.getCreatedAt()));
            },
            () -> {
                var captor = ArgumentCaptor.forClass(GetPublishersUseCase.Request.class);
                verify(getPublishersUseCase).execute(captor.capture());

                assertThat(captor.getValue().member().getIdentifier(), equalTo("/EE/MEMBER/123456789"));
                assertThat(captor.getValue().room().getIdentifier(), equalTo("PatientPortal"));
            }
        );
    }

    private static PageRequest emptyPageRequest() {
        return PageRequest.builder().build();
    }
}
