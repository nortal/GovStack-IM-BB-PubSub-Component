/*
 * This file is generated by jOOQ.
 */
package com.govstack.information_mediator.pubsub.shared.jooq;


import com.govstack.information_mediator.pubsub.shared.jooq.tables.EventTypeVersions;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.EventTypes;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.Events;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.Managers;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.PublishedMessages;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.PublisherConstraints;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.Publishers;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.Rooms;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.SubscriptionEventView;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.SubscriptionStatus;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.Subscriptions;

import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ImMsg extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>im_msg</code>
     */
    public static final ImMsg IM_MSG = new ImMsg();

    /**
     * record of different versions of a specific event type
     */
    public final EventTypeVersions EVENT_TYPE_VERSIONS = EventTypeVersions.EVENT_TYPE_VERSIONS;

    /**
     * record of event types available in specific room
     */
    public final EventTypes EVENT_TYPES = EventTypes.EVENT_TYPES;

    /**
     * record of all published events
     */
    public final Events EVENTS = Events.EVENTS;

    /**
     * record of managers in charge of specific rooms
     */
    public final Managers MANAGERS = Managers.MANAGERS;

    /**
     * record a message published to a subscriber
     */
    public final PublishedMessages PUBLISHED_MESSAGES = PublishedMessages.PUBLISHED_MESSAGES;

    /**
     * record of event types a specific publisher is allowed to create
     */
    public final PublisherConstraints PUBLISHER_CONSTRAINTS = PublisherConstraints.PUBLISHER_CONSTRAINTS;

    /**
     * record of publishers allowed to create events in specific rooms
     */
    public final Publishers PUBLISHERS = Publishers.PUBLISHERS;

    /**
     * record of rooms where events can be published
     */
    public final Rooms ROOMS = Rooms.ROOMS;

    /**
     * The table <code>im_msg.subscription_event_view</code>.
     */
    public final SubscriptionEventView SUBSCRIPTION_EVENT_VIEW = SubscriptionEventView.SUBSCRIPTION_EVENT_VIEW;

    /**
     * record of the status of a specific subscription
     */
    public final SubscriptionStatus SUBSCRIPTION_STATUS = SubscriptionStatus.SUBSCRIPTION_STATUS;

    /**
     * record of subscriptions to a specific room
     */
    public final Subscriptions SUBSCRIPTIONS = Subscriptions.SUBSCRIPTIONS;

    /**
     * No further instances allowed
     */
    private ImMsg() {
        super("im_msg", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.asList(
            EventTypeVersions.EVENT_TYPE_VERSIONS,
            EventTypes.EVENT_TYPES,
            Events.EVENTS,
            Managers.MANAGERS,
            PublishedMessages.PUBLISHED_MESSAGES,
            PublisherConstraints.PUBLISHER_CONSTRAINTS,
            Publishers.PUBLISHERS,
            Rooms.ROOMS,
            SubscriptionEventView.SUBSCRIPTION_EVENT_VIEW,
            SubscriptionStatus.SUBSCRIPTION_STATUS,
            Subscriptions.SUBSCRIPTIONS
        );
    }
}
