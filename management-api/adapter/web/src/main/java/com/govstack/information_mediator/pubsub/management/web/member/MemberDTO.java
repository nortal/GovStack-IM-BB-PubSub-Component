package com.govstack.information_mediator.pubsub.management.web.member;

import com.govstack.information_mediator.pubsub.domain.entity.Member;
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
@Schema(description = "Member data", name = "MemberData")
public class MemberDTO {

    @Schema(description = "X-Road member subsystem identifier", example = "GOVSTACK:ORG:MEMBER:VEHICLES", requiredMode = REQUIRED)
    String identifier;

    @Schema(description = "X-Road member name", example = "Vehicle registry subsystem", requiredMode = REQUIRED)
    String name;

    @Schema(description = "X-Road member subsystem code", example = "VEHICLES", requiredMode = REQUIRED)
    String subsystemCode;

    @Schema(description = "X-Road member code", example = "MEMBER", requiredMode = REQUIRED)
    String memberCode;

    @Schema(description = "X-Road member class", example = "ORG", requiredMode = REQUIRED)
    String memberClass;

    @Schema(description = "X-Road instance", example = "GOVSTACK", requiredMode = REQUIRED)
    String xRoadInstance;

    static MemberDTO fromDomain(Member member) {
        return MemberDTO.builder()
            .identifier(member.getIdentifier())
            .name(member.getName())
            .subsystemCode(member.getSubsystemCode())
            .memberCode(member.getMemberCode())
            .memberClass(member.getMemberClass())
            .xRoadInstance(member.getXRoadInstance())
            .build();
    }

}
