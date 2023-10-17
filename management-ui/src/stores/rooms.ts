import { defineStore, mapActions } from 'pinia';
import {
  CreateRoomRequest,
  ModifyRoomRequest,
  PagingSortingParameters,
  RoomResponse,
} from '@/generated';
import { DataQuery, PagingMetadata } from '@/ui-types';
import { roomsApi } from '@/api';
import { AxiosRequestConfig } from 'axios/index';
import { useAuth } from '@/stores/auth';

export interface State {
  rooms: RoomResponse[];
  managerIdToFilter: string | null;
  pagingOptions: PagingMetadata;
}

export const useRooms = defineStore('rooms', {
  state: (): State => ({
    rooms: [],
    managerIdToFilter: null,
    pagingOptions: {
      total_items: 0,
      items: 0,
      limit: 25,
      offset: 0,
    },
  }),
  persist: true,

  actions: {
    ...mapActions(useAuth, ['isAdministrator', 'isManager']),
    loadRoom(managerId: string, roomId: string) {
      if (this.isAdministrator()) {
        return roomsApi.getRoomDetailsAsAdministrator(managerId, roomId);
      } else if (this.isManager()) {
        return roomsApi.getRoomDetailsAsManager(roomId);
      }
    },

    async fetchRooms(dataOptions: DataQuery) {
      const offset = dataOptions?.page == null ? 0 : dataOptions.page - 1;
      const params = {
        offset: offset,
        limit: dataOptions.itemsPerPage,
        sortBy: dataOptions.sortBy,
        desc: dataOptions.sortOrder === 'desc',
      };
      const axiosParams: AxiosRequestConfig = { params };

      if (this.isAdministrator()) {
        return await roomsApi
          .listRoomsAsAdministrator(
            {} as PagingSortingParameters,
            this.managerIdToFilter as string,
            axiosParams,
          )
          .then((resp) => {
            this.rooms = resp.data.rooms || [];
            this.pagingOptions = resp.data.pagingMetadata;
          });
      } else if (this.isManager()) {
        return await roomsApi
          .listRoomsAsManager({} as PagingSortingParameters, axiosParams)
          .then((resp) => {
            this.rooms = resp.data.rooms || [];
            this.pagingOptions = resp.data.pagingMetadata;
          });
      }
    },

    createRoom(room: CreateRoomRequest) {
      return roomsApi.createRoom(room);
    },

    modifyRoom(
      roomId: string,
      managerId: string,
      editedRoom: ModifyRoomRequest,
    ) {
      if (this.isAdministrator()) {
        return roomsApi.modifyRoomAsAdministrator(
          managerId,
          roomId,
          editedRoom,
        );
      } else if (this.isManager()) {
        return roomsApi.modifyRoomAsManager(roomId, editedRoom);
      }
    },

    terminateRoom(managerId: string, roomId: string) {
      if (this.isAdministrator()) {
        return roomsApi.terminateRoomAsAdministrator(managerId, roomId);
      } else if (this.isManager()) {
        return roomsApi.terminateRoomAsManager(roomId);
      }
    },
  },
});
