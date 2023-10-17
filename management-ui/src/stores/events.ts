import {defineStore, mapActions} from 'pinia';
import {EventDetails, EventView, PagingSortingParameters} from '@/generated';
import {DataQuery, PagingMetadata} from '@/ui-types';
import {eventsApi} from "@/api";
import {AxiosRequestConfig} from "axios";
import {useAuth} from "@/stores/auth";

export interface State {
    events: EventView[];
    pagingOptions: PagingMetadata;
    currentEvent: EventDetails | null;
}

export const useEvent = defineStore('event', {
    state: (): State => ({
        events: [],
        pagingOptions: {
            total_items: 0,
            items: 0,
            limit: 25,
            offset: 0,
        },
        currentEvent: null,
    }),
    persist: true,
    actions: {
        ...mapActions(useAuth, ['isAdministrator', 'isManager']),
        async getEventDetails(memberId: string, roomId: string, eventId: string): Promise<EventDetails> {
            return eventsApi.getEventDetailsAsAdministrator(memberId, roomId, eventId).then((resp) => {
                return resp.data;
            });
        },
        async getEvents(memberId: string, roomId: string, from: string, to: string, dataOptions: DataQuery,
                        publisherId?: string, publisherIdentifier?: string, eventTypeIdentifier?: string,
                        eventTypeVersion?: number): Promise<void> {
            const offset = dataOptions?.page == null ? 0 : dataOptions.page - 1;
            const params: PagingSortingParameters = {
                limit: dataOptions.itemsPerPage,
                offset: offset,
                sortBy: dataOptions.sortBy,
                desc: dataOptions.sortOrder === "desc"
            }
            const axiosParams: AxiosRequestConfig = {params};
            if (this.isAdministrator()) {
                return eventsApi.getEventsViewAsAdministrator(memberId, roomId, from, to, {} as PagingSortingParameters, publisherId,
                    publisherIdentifier, eventTypeIdentifier, eventTypeVersion, axiosParams)
                    .then((resp: any) => {
                        this.events = resp.data.events || [];
                        this.pagingOptions = resp.data.pagingMetadata;
                    });
            } else {
                return eventsApi.getEventsViewAsManager(roomId, from, to, {} as PagingSortingParameters, publisherId,
                    publisherIdentifier, eventTypeIdentifier, eventTypeVersion, axiosParams)
                    .then((resp: any) => {
                        this.events = resp.data.events || [];
                        this.pagingOptions = resp.data.pagingMetadata;
                    });
            }
        },

    },
});
