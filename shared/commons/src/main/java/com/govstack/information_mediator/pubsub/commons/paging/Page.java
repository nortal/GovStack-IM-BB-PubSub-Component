package com.govstack.information_mediator.pubsub.commons.paging;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Page<T> {

    /**
     * Page content
     */
    private List<T> content;
    /**
     * The size of the {@link Page}.
     */
    private int maxItemsPerPage;
    /**
     * Number of the current {@link Page}. Is always non-negative
     */
    private int currentPageNumber;
    /**
     * Number of elements currently on this {@link Page}. Same as {@link #content} length
     */
    private int currentPageNumberOfElements;
    /**
     * Total amount of elements in repository
     */
    private int totalNumberOfElements;

}

