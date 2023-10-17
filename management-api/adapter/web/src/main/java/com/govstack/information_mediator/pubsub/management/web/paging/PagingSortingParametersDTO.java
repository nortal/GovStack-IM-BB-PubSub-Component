package com.govstack.information_mediator.pubsub.management.web.paging;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Paging and sorting input data", name = "PagingSortingParameters")
public class PagingSortingParametersDTO {

    @Schema(description = "Indicates item property that is to be used as filtering key", requiredMode = NOT_REQUIRED)
    @Nullable
    private String filterBy;

    @Schema(description = "Indicates item property that is to be used as sorting key", requiredMode = NOT_REQUIRED)
    @Nullable
    private String sortBy;

    @Schema(description = "Used sorting direction. Value 'true'  indicates descending and 'false' or omitted " +
        "parameter indicates ascending direction", example = "false", requiredMode = NOT_REQUIRED)
    @Nullable
    private Boolean desc = false;

    @Schema(description = "Indicates the maximum number of items for the returned page. " +
        "If parameter is omitted, then the default amount of 25 is returned.", requiredMode = NOT_REQUIRED)
    @Nullable
    private Integer limit = 25;

    @Schema(description = "Offset in pages from the beginning of the query result after filtering and sorting " +
        "parameters are taken into account. If the parameter omitted or the value 0 (zero) is given, the first page " +
        "is returned.", requiredMode = NOT_REQUIRED)
    @Nullable
    private Integer offset = 0;

}
