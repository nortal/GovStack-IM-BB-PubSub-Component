import { defineStore, mapActions } from 'pinia';
import { DataQuery, PagingMetadata } from '@/ui-types';
import { AxiosRequestConfig } from 'axios';
import { managersApi, membersApi } from '@/api';
import { MemberData, PagingSortingParameters } from '@/generated';
import { useAuth } from '@/stores/auth';

export interface State {
  members: MemberData[];
  pagingOptions: PagingMetadata;
}

export interface Member {
  identifier: string;
  name: string;
  subsystemCode: string;
  memberCode: string;
  memberClass: string;
  xRoadInstance: string;
}

export const useMember = defineStore('member', {
  state: (): State => ({
    members: [],
    pagingOptions: {
      total_items: 0,
      items: 0,
      limit: 25,
      offset: 0,
    },
  }),

  actions: {
    ...mapActions(useAuth, ['isAdministrator', 'isManager']),
    async getMembers(dataOptions: DataQuery): Promise<void> {
      const offset = dataOptions?.page == null ? 0 : dataOptions.page - 1;
      const params: unknown = {
        limit: dataOptions.itemsPerPage,
        offset: offset,
        sort: dataOptions.sortBy,
        desc: dataOptions.sortOrder === 'desc',
        filterBy: dataOptions.search,
      };
      const axiosParams: AxiosRequestConfig = { params };

      return membersApi
        .retrieveMembersPage({} as PagingSortingParameters, axiosParams)
        .then((resp) => {
          this.members = resp.data.members || [];
          this.pagingOptions = resp.data.pagingMetadata;
        });
    },
    loadAllMembers() {
      if (this.isAdministrator()) {
        return membersApi.exportMembers();
      } else if (this.isManager()) {
        return membersApi.exportMembers1();
      }
    },
  },
});
