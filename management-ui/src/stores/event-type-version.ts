import { UpdateEventTypeVersion } from '@/generated';
import { defineStore, mapActions } from 'pinia';
import { useAuth } from '@/stores/auth';
import { eventTypesVersionApi } from '@/api';
import { saveResponseAsFile } from '@/util/helpers';

export const useEventTypeVersion = defineStore('eventTypeVersion', {
  actions: {
    ...mapActions(useAuth, ['isAdministrator', 'isManager']),

    loadAllEventTypesVersion(
      memberId: string,
      roomId: string,
      eventType: string,
    ) {
      if (this.isAdministrator()) {
        return eventTypesVersionApi.getEventTypeVersionsAsAdministrator(
          memberId,
          roomId,
          eventType,
        );
      } else if (this.isManager()) {
        return eventTypesVersionApi.getEventTypeVersionsAsManager(
          roomId,
          eventType,
        );
      }
    },
    createEventTypeVersion(
      memberId: string,
      roomId: string,
      eventType: string,
      updateEventTypeVersion: UpdateEventTypeVersion,
    ) {
      if (this.isAdministrator()) {
        return eventTypesVersionApi.createEventTypeVersionAsAdministrator(
          memberId,
          roomId,
          eventType,
          updateEventTypeVersion,
        );
      } else if (this.isManager()) {
        return eventTypesVersionApi.createEventTypeVersionAsManager(
          roomId,
          eventType,
          updateEventTypeVersion,
        );
      }
    },
    updateEventTypeVersion(
      memberId: string,
      roomId: string,
      eventType: string,
      updateEventTypeVersion: UpdateEventTypeVersion,
    ) {
      if (this.isAdministrator()) {
        return eventTypesVersionApi.updateEventTypeVersionAsAdministrator(
          memberId,
          roomId,
          eventType,
          updateEventTypeVersion,
        );
      } else if (this.isManager()) {
        return eventTypesVersionApi.updateEventTypeVersionAsManager(
          roomId,
          eventType,
          updateEventTypeVersion,
        );
      }
    },

    downloadSchema(schema: string) {
      saveResponseAsFile(schema);
    },
    terminateEventTypeVersion(
      memberId: string,
      roomId: string,
      eventType: string,
      version: number,
    ) {
      if (this.isAdministrator()) {
        return eventTypesVersionApi.deleteEventTypeVersionAsAdministrator(
          memberId,
          roomId,
          eventType,
          version,
        );
      } else if (this.isManager()) {
        return eventTypesVersionApi.deleteEventTypeVersionAsManager(
          roomId,
          eventType,
          version,
        );
      }
    },
  },
});
