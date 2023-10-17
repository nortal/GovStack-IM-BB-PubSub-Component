package com.govstack.information_mediator.pubsub.management.web.member;

import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.domain.entity.Member;
import com.govstack.information_mediator.pubsub.management.web.paging.PagingMetadataDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Members Response", name = "MembersResponse")
public class PagedMembersDTO {

    @Schema(description = "List of members", requiredMode = REQUIRED)
    List<MemberDTO> members = null;

    @Schema(description = "Paging metadata", requiredMode = REQUIRED)
    PagingMetadataDTO pagingMetadata = null;

    static PagedMembersDTO from(Page<Member> membersPage) {
        return PagedMembersDTO.builder()
            .members(
                membersPage.getContent()
                    .stream()
                    .map(MemberDTO::fromDomain)
                    .toList())
            .pagingMetadata(
                PagingMetadataDTO.builder()
                    .items(membersPage.getCurrentPageNumberOfElements())
                    .limit(membersPage.getMaxItemsPerPage())
                    .totalItems(membersPage.getTotalNumberOfElements())
                    .offset(membersPage.getCurrentPageNumber())
                    .build())
            .build();
    }

}
