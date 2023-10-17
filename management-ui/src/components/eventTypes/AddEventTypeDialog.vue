<template>
  <xrd-simple-dialog
    :disable-save="!formReady"
    :loading="loading"
    cancel-button-text="action.cancel"
    title="eventTypesList.actions.add"
    @cancel="cancel"
    @save="add"
  >
    <template #content>
      <v-text-field
        v-model="createEventType.identifier"
        :label="$t('eventTypesList.actions.addAction.identifier')"
        variant="outlined"
      />
      <v-text-field
        v-model="createEventType.versionNo"
        :label="$t('eventTypesList.actions.addAction.versionNo')"
        type="number"
        variant="outlined"
        disabled
      />
      <v-textarea
        v-model="createEventType.schema"
        :label="$t('eventTypesList.actions.addAction.schema')"
        variant="outlined"
      />
    </template>
  </xrd-simple-dialog>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { mapActions, mapStores } from 'pinia';
import { useNotifications } from '@/stores/notifications';
import { Event } from '@/ui-types';
import { useEventTypes } from '@/stores/event-types';
import { CreateEventType } from '@/generated';

export default defineComponent({
  emits: [Event.Cancel, Event.Save],
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
  data() {
    return {
      loading: false,
      createEventType: { versionNo: 1 } as CreateEventType,
    };
  },
  computed: {
    ...mapStores(useEventTypes),
    formReady(): boolean {
      return !!(
        this.createEventType.identifier &&
        this.createEventType.versionNo &&
        this.createEventType.schema
      );
    },
  },
  methods: {
    ...mapActions(useNotifications, ['showError', 'showSuccess']),
    cancel(): void {
      this.$emit(Event.Cancel);
      this.clearForm();
    },
    clearForm(): void {
      this.createEventType = { versionNo: 1 } as CreateEventType;
    },
    add(): void {
      this.loading = true;
      this.eventTypesStore
        .addEventType(this.managerId, this.roomId, this.createEventType)
        .then(() => {
          this.showSuccess(
            this.$t('eventTypesList.actions.addAction.success', {
              eventType: this.createEventType.identifier,
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

<style lang="scss" scoped>
@import '@/assets/colors';
@import '@/assets/tables';
@import '@/assets/global-style';
</style>
