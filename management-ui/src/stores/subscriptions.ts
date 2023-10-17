import { defineStore, mapActions } from 'pinia';
import {
  ListSubscriptionsAsAdministratorStatusEnum,
  PagingSortingParameters,
  SubscriptionEventDeliveryItem,
  SubscriptionListItem,
} from '@/generated';
import { subscriptionsApi } from '@/api';
import { DataQuery, PagingMetadata } from '@/ui-types';
import { AxiosRequestConfig } from 'axios';
import { useAuth } from '@/stores/auth';

export interface SubscriptionsState {
  subscriptions: SubscriptionListItem[];
  subscriptionEvents: SubscriptionEventDeliveryItem[];
  subscriptionStatusFilter:
    | ListSubscriptionsAsAdministratorStatusEnum
    | undefined;
  pagingOptions: PagingMetadata;
  subscriptionEventsPagingOptions: PagingMetadata;
}

export const useSubscriptions = defineStore('subscriptions', {
  state: (): SubscriptionsState => ({
    subscriptions: [],
    subscriptionEvents: [],
    subscriptionStatusFilter: undefined,
    pagingOptions: {
      total_items: 0,
      items: 0,
      limit: 25,
      offset: 0,
    },
    subscriptionEventsPagingOptions: {
      total_items: 0,
      items: 0,
      limit: 25,
      offset: 0,
    },
  }),

  actions: {
    ...mapActions(useAuth, ['isAdministrator', 'isManager']),

    async loadSubscriptions(
      memberId: string,
      roomId: string,
      dataOptions: DataQuery,
    ) {
      const offset = dataOptions?.page == null ? 0 : dataOptions.page - 1;
      const params = {
        offset: offset,
        limit: dataOptions.itemsPerPage,
        sortBy: dataOptions.sortBy,
        desc: dataOptions.sortOrder === 'desc',
      };
      const axiosParams: AxiosRequestConfig = { params };

      if (this.isAdministrator()) {
        return subscriptionsApi
          .listSubscriptionsAsAdministrator(
            memberId,
            roomId,
            {} as PagingSortingParameters,
            this.subscriptionStatusFilter,
            axiosParams,
          )
          .then((resp) => {
            this.subscriptions = resp.data.subscriptions || [];
            this.pagingOptions = resp.data.pagingMetadata;
          })
          .catch((error) => {
            throw error;
          });
      } else if (this.isManager()) {
        return subscriptionsApi
          .listSubscriptionsAsManager(
            roomId,
            {} as PagingSortingParameters,
            this.subscriptionStatusFilter,
            axiosParams,
          )
          .then((resp) => {
            this.subscriptions = resp.data.subscriptions || [];
            this.pagingOptions = resp.data.pagingMetadata;
          })
          .catch((error) => {
            throw error;
          });
      }
    },

    async loadSubscriptionEvents(
      memberId: string,
      roomId: string,
      subscriptionId: string,
      dataOptions: DataQuery,
      from: string,
      to: string,
      eventId: string,
      eventTypeIdentifier: string,
      deliveryStatus: string,
    ) {
      const offset = dataOptions?.page == null ? 0 : dataOptions.page - 1;
      const params = {
        offset: offset,
        limit: dataOptions.itemsPerPage,
        sortBy: dataOptions.sortBy,
        desc: dataOptions.sortOrder === 'desc',
      };
      const axiosParams: AxiosRequestConfig = { params };

      if (this.isAdministrator()) {
        return subscriptionsApi
          .listSubscriptionEventDeliveriesAsAdministrator(
            memberId,
            roomId,
            subscriptionId,
            {} as PagingSortingParameters,
            from,
            to,
            eventId,
            eventTypeIdentifier,
            deliveryStatus,
            axiosParams,
          )
          .then((resp) => {
            this.subscriptionEvents = resp.data.events || [];
            this.subscriptionEventsPagingOptions = resp.data.pagingMetadata;
          })
          .catch((error) => {
            throw error;
          });
      } else if (this.isManager()) {
        return subscriptionsApi
          .listSubscriptionEventDeliveriesAsManager(
            roomId,
            subscriptionId,
            {} as PagingSortingParameters,
            from,
            to,
            eventId,
            eventTypeIdentifier,
            deliveryStatus,
            axiosParams,
          )
          .then((resp) => {
            this.subscriptionEvents = resp.data.events || [];
            this.subscriptionEventsPagingOptions = resp.data.pagingMetadata;
          })
          .catch((error) => {
            throw error;
          });
      }
    },

    terminateSubscription(
      roomId: string,
      memberId: string,
      subscriptionId: string,
    ) {
      if (this.isAdministrator()) {
        return subscriptionsApi.terminateSubscriptionAsAdministrator(
          memberId,
          roomId,
          subscriptionId,
        );
      } else if (this.isManager()) {
        return subscriptionsApi.terminateSubscriptionAsManager(
          roomId,
          subscriptionId,
        );
      }
    },

    rejectSubscription(
      roomId: string,
      memberId: string,
      subscriptionId: string,
    ) {
      if (this.isAdministrator()) {
        return subscriptionsApi.updateSubscriptionStatusAsAdministrator(
          memberId,
          roomId,
          subscriptionId,
          { status: ListSubscriptionsAsAdministratorStatusEnum.Rejected },
        );
      } else if (this.isManager()) {
        return subscriptionsApi.updateSubscriptionStatusAsManager(
          roomId,
          subscriptionId,
          { status: ListSubscriptionsAsAdministratorStatusEnum.Rejected },
        );
      }
    },

    approveSubscription(
      roomId: string,
      memberId: string,
      subscriptionId: string,
    ) {
      if (this.isAdministrator()) {
        return subscriptionsApi.updateSubscriptionStatusAsAdministrator(
          memberId,
          roomId,
          subscriptionId,
          { status: ListSubscriptionsAsAdministratorStatusEnum.Active },
        );
      } else if (this.isManager()) {
        return subscriptionsApi.updateSubscriptionStatusAsManager(
          roomId,
          subscriptionId,
          { status: ListSubscriptionsAsAdministratorStatusEnum.Active },
        );
      }
    },
  },
});
