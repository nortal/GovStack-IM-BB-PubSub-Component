package com.govstack.information_mediator.pubsub.management.web.paging;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Paging Metadata", name = "PagingMetadata")
public class PagingMetadataDTO {

    @Schema(description = "Total number of items in the query results",
        example = "30", requiredMode = REQUIRED)
    @JsonProperty("total_items")
    private Integer totalItems;

    @Schema(description = "Number of items on the current page of query results",
        example = "10", requiredMode = REQUIRED)
    private Integer items;

    @Schema(description = "Indicates the maximum number of items for the returned page. ",
        example = "10", requiredMode = REQUIRED)
    private Integer limit = 25;

    @Schema(description = "Offset in pages from the beginning of the query result after filtering and sorting " +
        "parameters are taken into account.",
        example = "2", requiredMode = REQUIRED)
    private Integer offset = 0;

}
