package com.govstack.information_mediator.pubsub.management.domain.port;

import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.domain.entity.Member;

import java.util.List;

public interface FetchMembersPort {

    List<Member> fetchMembers();

    Page<Member> fetchMembers(PageRequest pagedRequest);
}
