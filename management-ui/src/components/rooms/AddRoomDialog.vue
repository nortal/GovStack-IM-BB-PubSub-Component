<template>
  <xrd-simple-dialog
    :disable-save="!formReady"
    :loading="loading"
    cancel-button-text="action.cancel"
    title="roomsList.actions.createRoom"
    @cancel="cancel"
    @save="add"
  >
    <template #content>
      <v-text-field
        v-model="createRoomRequest.identifier"
        :label="$t('roomsList.actions.createAction.identifier')"
        variant="outlined"
        autofocus
      />
      <v-select
        v-model="createRoomRequest.managerIdentifier"
        :items="managersToSelect"
        :label="$t('roomsList.actions.createAction.managerIdentifier')"
        variant="outlined"
      />
      <v-text-field
        v-model="createRoomRequest.messageExpiration"
        :label="$t('roomsList.actions.createAction.messageExpiration')"
        variant="outlined"
        type="number"
        min="1"
        step="1"
      />
      <v-text-field
        v-model="createRoomRequest.deliveryDelay"
        :label="$t('roomsList.actions.createAction.deliveryDelay')"
        variant="outlined"
        type="number"
        min="1"
        step="1"
      />
      <v-text-field
        v-model="createRoomRequest.deliveryDelayMultiplier"
        :label="$t('roomsList.actions.createAction.deliveryDelayMultiplier')"
        variant="outlined"
        type="number"
        min="1.01"
        step="0.01"
      />
      <v-text-field
        v-model="createRoomRequest.deliveryAttempts"
        :label="$t('roomsList.actions.createAction.deliveryAttempts')"
        variant="outlined"
        type="number"
        min="1"
        step="1"
      />
    </template>
  </xrd-simple-dialog>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { mapActions, mapStores } from 'pinia';
import { useRooms } from '@/stores/rooms';
import { useNotifications } from '@/stores/notifications';
import { Event } from '@/ui-types';
import { CreateRoomRequest } from '@/generated';
import { useManagers } from '@/stores/managers';

export default defineComponent({
  emits: [Event.Cancel, Event.Save],
  data() {
    return {
      loading: false,
      managersToSelect: [] as string[],
      createRoomRequest: { deliveryDelayMultiplier: 1.01 } as CreateRoomRequest,
    };
  },
  computed: {
    ...mapStores(useRooms, useManagers),
    formReady(): boolean {
      return !!(
        this.createRoomRequest.identifier &&
        this.createRoomRequest.managerIdentifier &&
        this.createRoomRequest.messageExpiration &&
        this.createRoomRequest.deliveryDelay &&
        this.createRoomRequest.deliveryDelayMultiplier &&
        this.createRoomRequest.deliveryAttempts
      );
    },
  },
  created() {
    this.managersStore
      .loadAllManagers()
      .then((resp) => {
        this.managersToSelect = resp.data.flatMap(
          (manager) => manager.identifier,
        );
      })
      .catch((error) => {
        throw error;
      });
  },
  methods: {
    ...mapActions(useNotifications, ['showError', 'showSuccess']),
    cancel(): void {
      this.$emit(Event.Cancel);
      this.clearForm();
    },
    clearForm(): void {
      this.createRoomRequest = {} as CreateRoomRequest;
    },
    add(): void {
      this.loading = true;
      this.roomsStore
        .createRoom(this.createRoomRequest)
        .then(() => {
          this.showSuccess(
            this.$t('roomsList.actions.createAction.success', {
              identifier: this.createRoomRequest.identifier,
            }),
          );
          this.$emit(Event.Save);
          this.clearForm();
        })
        .catch((error) => {
          this.showError(error);
          this.$emit(Event.Cancel);
        })
        .finally(() => {
          this.loading = false;
        });
    },
  },
});
</script>
