<template>
  <xrd-simple-dialog
    :loading="loading"
    :disable-save="edited"
    cancel-button-text="action.cancel"
    save-button-text="action.save"
    title="roomsList.actions.edit"
    @cancel="cancel"
    @save="edit"
  >
    <template #content>
      <v-text-field
        v-if="!isManagersSelfEdit()"
        v-model="roomToEdit.identifier"
        :label="$t('roomsList.actions.createAction.identifier')"
        variant="outlined"
        :disabled="loading"
      />
      <v-select
        v-if="!isManagersSelfEdit()"
        v-model="roomToEdit.managerIdentifier"
        :items="managersToSelect"
        :label="$t('roomsList.actions.createAction.managerIdentifier')"
        variant="outlined"
        :disabled="loading"
      />
      <v-text-field
        v-model.number="roomToEdit.messageExpiration"
        :label="$t('roomsList.actions.createAction.messageExpiration')"
        variant="outlined"
        :disabled="loading"
        type="number"
        min="1"
        step="1"
      />
      <v-text-field
        v-model.number="roomToEdit.deliveryDelay"
        :label="$t('roomsList.actions.createAction.deliveryDelay')"
        variant="outlined"
        :disabled="loading"
        type="number"
        min="1"
        step="1"
      />
      <v-text-field
        v-model.number="roomToEdit.deliveryDelayMultiplier"
        :label="$t('roomsList.actions.createAction.deliveryDelayMultiplier')"
        variant="outlined"
        :disabled="loading"
        type="number"
        min="1.01"
        step="0.01"
      />
      <v-text-field
        v-model.number="roomToEdit.deliveryAttempts"
        :label="$t('roomsList.actions.createAction.deliveryAttempts')"
        variant="outlined"
        :disabled="loading"
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
import { ModifyRoomRequest } from '@/generated';
import { useAuth } from '@/stores/auth';
import { useManagers } from '@/stores/managers';

export default defineComponent({
  props: {
    roomId: {
      type: String,
      required: true,
    },
    managerId: {
      type: String,
      required: true,
    },
  },
  emits: [Event.Cancel, Event.Save],
  created() {
    this.loading = true;
    this.roomsStore.loadRoom(this.managerId, this.roomId).then((resp) => {
      this.roomToEdit = {
        identifier: resp.data.identifier,
        managerIdentifier: resp.data.managerIdentifier,
        messageExpiration: resp.data.messageExpiration,
        deliveryDelay: resp.data.deliveryDelay,
        deliveryDelayMultiplier: resp.data.deliveryDelayMultiplier,
        deliveryAttempts: resp.data.deliveryAttempts,
      };
      if (this.isManagersSelfEdit()) {
        this.roomToEdit.identifier = undefined;
        this.roomToEdit.managerIdentifier = undefined;
      }
      // deep copy
      this.originalRoom = JSON.parse(JSON.stringify(this.roomToEdit));
      this.loading = false;
    });
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
  data() {
    return {
      loading: false,
      managersToSelect: [] as string[],
      roomToEdit: {} as ModifyRoomRequest,
      originalRoom: {} as ModifyRoomRequest,
    };
  },
  computed: {
    ...mapStores(useRooms, useManagers),
    edited(): boolean {
      return (
        this.roomToEdit.identifier === this.originalRoom.identifier &&
        this.roomToEdit.managerIdentifier ===
          this.originalRoom.managerIdentifier &&
        this.roomToEdit.messageExpiration ===
          this.originalRoom.messageExpiration &&
        this.roomToEdit.deliveryDelay === this.originalRoom.deliveryDelay &&
        this.roomToEdit.deliveryDelayMultiplier ===
          this.originalRoom.deliveryDelayMultiplier &&
        this.roomToEdit.deliveryAttempts === this.originalRoom.deliveryAttempts
      );
    },
  },
  methods: {
    ...mapActions(useNotifications, ['showError', 'showSuccess']),
    ...mapActions(useAuth, ['isManager']),
    isManagersSelfEdit(): boolean {
      return this.isManager();
    },
    cancel(): void {
      this.$emit(Event.Cancel);
    },
    edit() {
      this.loading = true;
      this.roomsStore
        .modifyRoom(this.roomId, this.managerId, this.roomToEdit)
        .then(() => {
          this.showSuccess(
            this.$t('roomsList.actions.editAction.success', {
              identifier: this.roomToEdit.identifier,
            }),
          );
          this.$emit(Event.Save);
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
