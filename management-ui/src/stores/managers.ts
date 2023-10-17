import { defineStore } from 'pinia';
import {
  ManagerResponse,
  CreateManagerRequest,
  PagingSortingParameters,
  PagedManagersResponse,
} from '@/generated';
import { managersApi } from '@/api';
import { DataQuery, PagingMetadata } from '@/ui-types';
import { AxiosRequestConfig, AxiosResponse } from 'axios';

export interface ManagersState {
  managers: ManagerResponse[];
  pagingOptions: PagingMetadata;
}

export const useManagers = defineStore('managers', {
  state: (): ManagersState => ({
    managers: [],
    pagingOptions: {
      total_items: 0,
      items: 0,
      limit: 25,
      offset: 0,
    },
  }),

  actions: {
    async loadManagersPage(dataOptions: DataQuery) {
      const offset = dataOptions?.page == null ? 0 : dataOptions.page - 1;
      const params = {
        offset: offset,
        limit: dataOptions.itemsPerPage,
        sortBy: dataOptions.sortBy,
        desc: dataOptions.sortOrder === 'desc',
      };
      const axiosParams: AxiosRequestConfig = { params };
      return managersApi
        .retrieveManagers({} as PagingSortingParameters, axiosParams)
        .then((resp: AxiosResponse<PagedManagersResponse>) => {
          this.managers = resp.data.managers;
          this.pagingOptions = resp.data.pagingMetadata;
        })
        .catch((error) => {
          throw error;
        });
    },

    loadAllManagers() {
      return managersApi.retrieveAllManagers();
    },

    createManager(manager: CreateManagerRequest) {
      return managersApi.createManager(manager);
    },

    terminateManager(managerId: string) {
      return managersApi.terminateManager(managerId);
    },
  },
});
