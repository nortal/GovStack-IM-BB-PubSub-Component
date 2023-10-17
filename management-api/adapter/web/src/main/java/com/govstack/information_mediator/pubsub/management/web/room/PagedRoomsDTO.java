package com.govstack.information_mediator.pubsub.management.web.room;

import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.domain.entity.Member;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.domain.view.RoomView;
import com.govstack.information_mediator.pubsub.management.web.member.MemberDTO;
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
@Schema(description = "Paginated rooms response", name = "PaginatedRoomsResponse")
public class PagedRoomsDTO {

    @Schema(description = "List of rooms", requiredMode = REQUIRED)
    List<RoomResponseDTO> rooms = null;

    @Schema(description = "Paging metadata", requiredMode = REQUIRED)
    PagingMetadataDTO pagingMetadata = null;

    static PagedRoomsDTO from(Page<RoomView> roomPage) {
        return PagedRoomsDTO.builder()
            .rooms(
                roomPage.getContent().stream()
                    .map(room -> RoomResponseDTO.builder()
                            .identifier(room.getIdentifier())
                            .managerIdentifier(room.getManagerIdentifier())
                        .build())
                    .toList())
            .pagingMetadata(
                PagingMetadataDTO.builder()
                    .items(roomPage.getCurrentPageNumberOfElements())
                    .limit(roomPage.getMaxItemsPerPage())
                    .totalItems(roomPage.getTotalNumberOfElements())
                    .offset(roomPage.getCurrentPageNumber())
                    .build())
            .build();
    }

}
