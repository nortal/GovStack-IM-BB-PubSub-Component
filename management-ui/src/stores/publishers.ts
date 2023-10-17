import { defineStore, mapActions } from 'pinia';
import {
  CreatePublisherRequest,
  CreateRoomRequest,
  ModifyRoomRequest,
  PagingSortingParameters,
  PublisherViewResponse,
  UpdatePublisherRequest,
} from '@/generated';
import { DataQuery, PagingMetadata } from '@/ui-types';
import { publishersApi } from '@/api';
import { AxiosRequestConfig } from 'axios/index';
import { useAuth } from '@/stores/auth';
import { ro } from 'vuetify/locale';

export interface State {
  publishers: PublisherViewResponse[];
  pagingOptions: PagingMetadata;
}

export const usePublishers = defineStore('publishers', {
  state: (): State => ({
    publishers: [],
    pagingOptions: {
      total_items: 0,
      items: 0,
      limit: 25,
      offset: 0,
    },
  }),

  actions: {
    ...mapActions(useAuth, ['isAdministrator', 'isManager']),

    async fetchPublishers(
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
        return await publishersApi
          .getPublishersAsAdministrator(
            memberId,
            roomId,
            {} as PagingSortingParameters,
            axiosParams,
          )
          .then((resp) => {
            this.publishers = resp.data.publishers || [];
            this.pagingOptions = resp.data.pagingMetadata;
          });
      } else if (this.isManager()) {
        return await publishersApi
          .getPublishersAsManager(
            roomId,
            {} as PagingSortingParameters,
            axiosParams,
          )
          .then((resp) => {
            this.publishers = resp.data.publishers || [];
            this.pagingOptions = resp.data.pagingMetadata;
          });
      }
    },

    addPublisher(
      memberId: string,
      roomId: string,
      createPublisherRequest: CreatePublisherRequest,
    ) {
      if (this.isAdministrator()) {
        return publishersApi.createPublisherAsAdministrator(
          memberId,
          roomId,
          createPublisherRequest,
        );
      } else if (this.isManager()) {
        return publishersApi.createPublisherAsManager(
          roomId,
          createPublisherRequest,
        );
      }
    },

    updatePublisher(
      memberId: string,
      roomId: string,
      publisherId: string,
      editedPublisher: UpdatePublisherRequest,
    ) {
      if (this.isAdministrator()) {
        return publishersApi.updatePublisherAsAdministrator(
          memberId,
          roomId,
          publisherId,
          editedPublisher,
        );
      } else if (this.isManager()) {
        return publishersApi.updatePublisherAsManager(
          roomId,
          publisherId,
          editedPublisher,
        );
      }
    },

    terminatePublisher(memberId: string, roomId: string, publisherId: string) {
      if (this.isAdministrator()) {
        return publishersApi.terminatePublisherAsAdministrator(
          memberId,
          roomId,
          publisherId,
        );
      } else if (this.isManager()) {
        return publishersApi.terminatePublisherAsManager(roomId, publisherId);
      }
    },
  },
});
