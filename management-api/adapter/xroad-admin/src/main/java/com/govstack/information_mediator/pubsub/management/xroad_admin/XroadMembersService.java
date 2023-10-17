package com.govstack.information_mediator.pubsub.management.xroad_admin;

import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.domain.entity.Member;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchMembersPort;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.ApiClient;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.api.ClientsApi;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.ServiceClient;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.ServiceClientType;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

@Service
public class XroadMembersService implements FetchMembersPort {

    private final ClientsApi clientsApi;

    @Value("${management.xroad-admin-client.pubsub-subsystem-identifier}")
    String pubsubSubsystemIdentifier;

    public XroadMembersService(ApiClient apiClient) {
        this.clientsApi = new ClientsApi(apiClient);
    }

    @Override
    public List<Member> fetchMembers() {
        Flux<ServiceClient> serviceClientFlux = clientsApi.getClientServiceClients(pubsubSubsystemIdentifier);
        List<ServiceClient> serviceClients = serviceClientFlux.collectList().block();

        assert serviceClients != null;

        return serviceClients.stream()
            // Returning only SUBSYSTEM type of clients, LocalGroups and GlobalGroups not supported
            .filter(client -> client.getServiceClientType() != null &&
                client.getServiceClientType().equals(ServiceClientType.SUBSYSTEM))
            // Map all items to Member objects
            .map(serviceClient -> Member.fromIdentifier(serviceClient.getId(), serviceClient.getName()))
            // Sort by the given field
            .sorted(Comparator.comparing(Member::getName))
            .toList();
    }

    @Override
    public Page<Member> fetchMembers(PageRequest pageRequest) {
        Flux<ServiceClient> serviceClientFlux = clientsApi.getClientServiceClients(pubsubSubsystemIdentifier);
        List<ServiceClient> serviceClients = serviceClientFlux.collectList().block();

        assert serviceClients != null;

        var filteredClients = getFilteredAndSortedMembers(pageRequest, serviceClients);

        var pageContents = filteredClients.stream()
                .skip((long) pageRequest.getPageOffset() * pageRequest.getMaxItemsPerPage())
                .limit(pageRequest.getMaxItemsPerPage())
                .toList();

        return Page.<Member>builder()
                .content(pageContents)

                .maxItemsPerPage(pageRequest.getMaxItemsPerPage())
                .currentPageNumber(pageRequest.getPageOffset())
                .currentPageNumberOfElements(pageContents.size())

                .totalNumberOfElements(filteredClients.size())
                .build();
    }

    private static List<Member> getFilteredAndSortedMembers(PageRequest pageRequest, List<ServiceClient> serviceClients) {
        return serviceClients.stream()
                // Returning only SUBSYSTEM type of clients, LocalGroups and GlobalGroups not supported
                .filter(client -> client.getServiceClientType() != null &&
                        client.getServiceClientType().equals(ServiceClientType.SUBSYSTEM))
                // Return only clients that match the filter query
                // Supporting filtering by name and client identifier substring
                .filter(client -> StringUtils.isEmpty(pageRequest.getFilterQuery()) ||
                        client.getId().toLowerCase().contains(pageRequest.getFilterQuery().toLowerCase()) ||
                        (client.getName() != null && client.getName().toLowerCase().contains(pageRequest.getFilterQuery().toLowerCase())))
                // Map all items to Member objects
                .map(serviceClient -> Member.fromIdentifier(serviceClient.getId(), serviceClient.getName()))
                // Sort by the given field
                .sorted(getComparator(pageRequest))
                .toList();
    }

    private static Comparator<Member> getComparator(PageRequest pageRequest) {
        Comparator<Member> comparator = Comparator.comparing(getSortFunction(pageRequest.getSortBy()));
        if (pageRequest.isDescendingOrder()) {
            return comparator.reversed().thenComparing(Member::getName);
        }
        return comparator.thenComparing(Member::getName);
    }

    public static Function<Member, String> getSortFunction(String fieldName) {
        if (fieldName == null) {
            return Member::getName;
        }
        return switch (fieldName) {
            case "memberCode" -> Member::getName;
            case "memberClass" -> Member::getMemberClass;
            case "subsystemCode" -> Member::getSubsystemCode;
            case "xRoadInstance" -> Member::getXRoadInstance;
            default -> throw new IllegalArgumentException("Invalid field name for sorting");
        };
    }

}
