<template>
  <xrd-simple-dialog
    cancel-button-text="Close"
    title="publishersList.actions.viewConstraintsAction.title"
    @cancel="cancel"
    hide-save-button
  >
    <template #content>
      <v-data-table-virtual
        :headers="headers"
        :items="publisher.constraints"
        class="xrd-table elevation-0 rounded"
        item-key="eventTypeId"
        density="compact"
      >
        <template #[`item.createdAt`]="{ item }">
          <div>
            {{ $filters.formatDateTime(item.columns.createdAt) }}
          </div>
        </template>
      </v-data-table-virtual>
    </template>
  </xrd-simple-dialog>
</template>

<script lang="ts">
import { defineComponent, PropType } from 'vue';
import { mapActions } from 'pinia';
import { useNotifications } from '@/stores/notifications';
import { DataTableHeader, Event } from '@/ui-types';
import { PublisherViewResponse } from '@/generated';
import { VDataTableVirtual } from 'vuetify/labs/VDataTable';

export default defineComponent({
  components: {
    VDataTableVirtual,
  },
  emits: [Event.Cancel],
  props: {
    publisher: {
      type: Object as PropType<PublisherViewResponse>,
      required: true,
    },
  },
  computed: {
    headers(): DataTableHeader[] {
      return [
        {
          title: this.$t(
            'publishersList.actions.viewConstraintsAction.eventIdentifier',
          ),
          align: 'start',
          key: 'eventIdentifier',
          sortable: false,
        },
        {
          title: this.$t(
            'publishersList.actions.viewConstraintsAction.createdAt',
          ),
          align: 'start',
          key: 'createdAt',
          sortable: false,
        },
      ];
    },
  },
  methods: {
    ...mapActions(useNotifications, ['showError', 'showSuccess']),
    cancel(): void {
      this.$emit(Event.Cancel);
    },
  },
});
</script>

<style lang="scss" scoped>
@import '@/assets/colors';
@import '@/assets/tables';
@import '@/assets/global-style';
</style>
