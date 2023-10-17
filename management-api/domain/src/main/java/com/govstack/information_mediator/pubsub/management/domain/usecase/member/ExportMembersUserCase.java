package com.govstack.information_mediator.pubsub.management.domain.usecase.member;

import com.govstack.information_mediator.pubsub.domain.entity.Member;

import java.util.List;

public interface ExportMembersUserCase {

    Response execute();

    record Response(List<Member> members) {

    }
}
