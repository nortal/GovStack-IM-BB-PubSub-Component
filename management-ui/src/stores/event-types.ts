import { DataQuery, PagingMetadata } from '@/ui-types';
import {
  CreateEventType,
  EventTypeView,
  PagingSortingParameters,
} from '@/generated';
import { defineStore, mapActions } from 'pinia';
import { useAuth } from '@/stores/auth';
import { eventTypesApi } from '@/api';
import { AxiosRequestConfig } from 'axios';

export interface State {
  eventTypes: EventTypeView[];
  pagingOptions: PagingMetadata;
}

export const useEventTypes = defineStore('eventTypes', {
  state: (): State => ({
    eventTypes: [],
    pagingOptions: {
      total_items: 0,
      items: 0,
      limit: 25,
      offset: 0,
    },
  }),

  actions: {
    ...mapActions(useAuth, ['isAdministrator', 'isManager']),

    async fetchEventTypes(
      managerId: string,
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
        return await eventTypesApi
          .getEventTypesAsAdministrator(
            managerId,
            roomId,
            {} as PagingSortingParameters,
            axiosParams,
          )
          .then((resp) => {
            this.eventTypes = resp.data.eventTypes || [];
            this.pagingOptions = resp.data.pagingMetadata;
          });
      } else if (this.isManager()) {
        return await eventTypesApi
          .getEventTypesAsManager(
            roomId,
            {} as PagingSortingParameters,
            axiosParams,
          )
          .then((resp) => {
            this.eventTypes = resp.data.eventTypes || [];
            this.pagingOptions = resp.data.pagingMetadata;
          });
      }
    },
    loadAllEventTypes(memberId: string, roomId: string) {
      if (this.isAdministrator()) {
        return eventTypesApi.exportEventTypeIdentifiersAsAdministrator(
          memberId,
          roomId,
        );
      } else if (this.isManager()) {
        return eventTypesApi.exportEventTypeIdentifiersAsManager(roomId);
      }
    },
    terminateEventType(managerId: string, roomId: string, eventType: string) {
      if (this.isAdministrator()) {
        return eventTypesApi.deleteEventTypeAsAdministrator(
          managerId,
          roomId,
          eventType,
        );
      } else if (this.isManager()) {
        return eventTypesApi.deleteEventTypeAsManager(roomId, eventType);
      }
    },
    addEventType(
      managerId: string,
      roomId: string,
      createEventType: CreateEventType,
    ) {
      if (this.isAdministrator()) {
        return eventTypesApi.createEventTypeAsAdministrator(
          managerId,
          roomId,
          createEventType,
        );
      } else if (this.isManager()) {
        return eventTypesApi.createEventTypeAsManager(roomId, createEventType);
      }
    },
  },
});
