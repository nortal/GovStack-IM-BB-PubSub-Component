<template>
  <titled-view title-key="eventsList.title"/>
  <v-row>
    <v-col cols="12" sm="6" md="6" lg="4">
      <VueDatePicker v-model="period"
                     :format="formatDatePicker"
                     range
                     @update:model-value="fetchEvents()"/>
    </v-col>
  </v-row>
  <v-row>
    <v-col cols="5">
      <v-text-field
          v-model="eventTypeSearch"
          :label="$t('eventsList.search.eventType')"
          prepend-icon="mdi-magnify"
          clearable
          @update:model-value="fetchEvents"
      ></v-text-field>
    </v-col>
    <v-col cols="2">
      <v-text-field
          v-model="eventTypeVersionSearch"
          :label="$t('eventsList.search.eventTypeVersion')"
          :rules="[v => (!v || /^\d+$/.test(v)) || 'Only numbers are allowed']"
          clearable
          @update:model-value="fetchEvents"
      ></v-text-field>
    </v-col>
    <v-col cols="5">
      <v-text-field
          v-model="eventPublisherSearch"
          :label="$t('eventsList.search.eventPublisher')"
          clearable
          @update:model-value="fetchEvents"
      ></v-text-field>
    </v-col>
  </v-row>


  <v-data-table-server
      :loading="loading"
      :headers="headers"
      :items="eventStore.events as EventView[]"
      :items-per-page="10"
      :items-length="eventStore.pagingOptions.total_items"
      :items-per-page-options="itemsPerPageOptions"
      class="xrd-table elevation-0 rounded"
      item-key="identifier"
      density="compact"
      @update:options="changeOptions"
  >
    <template #[`item.createdAt`]="{ item }">
      {{ formatDateTimeSeconds(item.raw.createdAt) }}
    </template>

    <template #[`item.button`]="{ item }">
      <xrd-button class="xrd-clickable" text @click="openEventDetailsDialog(item.raw)">
        {{ $t('eventsList.actions.details') }}
      </xrd-button>
    </template>
  </v-data-table-server>

  <!--   View event details action -->
  <event-details-dialog
      v-if="showEventDetailsDialog"
      :event-id="selectedEventId || ''"
      :manager-id="managerId"
      :room-id="roomId"
      @cancel="showEventDetailsDialog = false"
  />
</template>

<script lang="ts">
import type {EventView} from "@/generated/api";
import {defineComponent} from 'vue';
import {mapActions, mapStores} from 'pinia';
import {useNotifications} from '@/stores/notifications';
import XrdButton from "@/components/XrdButton.vue";
import {DataQuery, DataTableHeader, PagingOptions} from "@/ui-types";
import {defaultItemsPerPageOptions} from "@/util/defaults";
import {useEvent} from "@/stores/events";
import {formatDateToUTC, formatTimestamp} from "@/util/helpers";
import EditRoomDialog from "@/components/rooms/EditRoomDialog.vue";
import EventDetailsDialog from "@/components/events/EventDetailsDialog.vue";
import {VDataTableServer} from "vuetify/labs/VDataTable";
import TitledView from "@/components/ui/TitledView.vue";
import {formatDateTimeSeconds} from "@/filters";

export default defineComponent({
  name: 'Events',
  components: {TitledView, EventDetailsDialog, EditRoomDialog, XrdButton, VDataTableServer},
  props: {
    roomId: {
      type: String,
      required: true,
    },
    managerId: {
      type: String,
      required: true,
    }
  },
  data() {
    return {
      search: "",
      loading: true,
      dataQuery: {page: 1, itemsPerPage: 10} as DataQuery,
      itemsPerPageOptions: defaultItemsPerPageOptions(),
      showEventDetailsDialog: false,
      selectedEventId: "" as string | undefined,
      period: [new Date(new Date().setDate(new Date().getDate() - 7)), new Date()],
      eventTypeSearch: undefined as string | undefined,
      eventTypeVersionSearch: undefined as number | undefined,
      eventPublisherSearch: undefined as string | undefined,
    };
  },
  computed: {
    ...mapStores(useEvent),
    headers(): DataTableHeader[] {
      return [
        {
          title: this.$t('eventsList.header.identifier'),
          align: 'start',
          key: 'id',
          sortable: true,
        },
        {
          title: this.$t('eventsList.header.eventType'),
          align: 'start',
          key: 'eventTypeIdentifier',
        },
        {
          title: this.$t('eventsList.header.eventTypeVersion'),
          align: 'start',
          key: 'eventTypeVersionNo',
        },
        {
          title: this.$t('eventsList.header.createdAt'),
          align: 'start',
          key: 'createdAt',
        },
        {
          title: this.$t('eventsList.header.publishedIdentifier'),
          align: 'start',
          key: 'publisherIdentifier',
        },
        {
          title: this.$t('eventsList.header.actions'),
          align: 'center',
          key: 'button',
          sortable: false,
        },
      ];
    },
  },
  methods: {
    formatDateTimeSeconds,
    ...mapActions(useNotifications, ['showError', 'showSuccess']),
    formatDatePicker(dates: Date[]): String {
      return formatTimestamp(dates[0]) + " - "
          + formatTimestamp(dates[1]);
    },
    openEventDetailsDialog(event: EventView): void {
      this.selectedEventId = event.id;
      this.showEventDetailsDialog = true;
    },
    fetchEvents: async function (): Promise<void> {
      this.dataQuery.search = this.search;
      this.loading = true;
      try {
      } catch (error: unknown) {
        this.showError(error);
      } finally {
        this.loading = false;
      }
      await this.eventStore.getEvents(
          this.managerId,
          this.roomId,
          this.period[0].toISOString(),
          this.period[1].toISOString(),
          this.dataQuery,
          undefined,
          this.eventPublisherSearch,
          this.eventTypeSearch,
          this.eventTypeVersionSearch,
      );
    },
    changeOptions: async function ({itemsPerPage, page, sortBy}: PagingOptions) {
      this.dataQuery.itemsPerPage = itemsPerPage;
      this.dataQuery.page = page;
      this.dataQuery.sortBy = sortBy[0]?.key;
      if (typeof sortBy[0]?.order === 'boolean') {
        this.dataQuery.sortOrder = sortBy[0].order ? 'asc' : 'desc';
      } else {
        this.dataQuery.sortOrder = sortBy[0]?.order;
      }
      await this.fetchEvents()
    },

  },
});
</script>

<style lang="scss" scoped>
@import '@/assets/colors';
@import '@/assets/tables';
@import '@/assets/global-style';
</style>
