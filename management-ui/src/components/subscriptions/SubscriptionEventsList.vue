<template>
  <div class="list-view">
    <titled-view title-key="subscriptionEventsList.title">
      <v-row class="ml-1">
        <v-select
          class="subscription-status-filter"
          v-model="selectedDeliveryStatus"
          :items="Object.values(DeliveryStatusEnum)"
          :label="$t('subscriptionsList.filterByStatus')"
          variant="plain"
          @update:modelValue="filterByStatus"
          clearable
        />
      </v-row>
      <v-row>
        <v-col cols="5">
          <v-text-field
            v-model="eventSearch"
            :label="$t('subscriptionEventsList.search.eventId')"
            clearable
            @update:model-value="fetchSubscriptionEvents"
          ></v-text-field>
        </v-col>
        <v-col cols="2">
          <v-text-field
            v-model="eventTypeSearch"
            :label="$t('subscriptionEventsList.search.eventType')"
            clearable
            @update:model-value="fetchSubscriptionEvents"
          ></v-text-field>
        </v-col>
        <v-col cols="5">
          <VueDatePicker
            v-model="period"
            :format="formatDatePicker"
            range
            @update:model-value="fetchSubscriptionEvents()"
          />
        </v-col>
      </v-row>

      <v-data-table-server
        :loading="loading"
        :headers="headers"
        :items="subscriptionsStore.subscriptionEvents"
        :items-per-page="10"
        :items-length="
          subscriptionsStore.subscriptionEventsPagingOptions.total_items
        "
        :items-per-page-options="itemsPerPageOptions"
        class="xrd-table elevation-0 rounded pb-3"
        item-key="eventId"
        density="compact"
        @update:options="changeOptions"
      >
        <template #[`item.deliveryStatus`]="{ item }">
          <div>
            <xrd-icon-base v-if="item.columns.deliveryStatus === 'DELIVERED'">
              <XrdIconChecked :color="colors.Success100" />
            </xrd-icon-base>
            {{
              $t(
                `subscriptionEventsList.details.status.${item.columns.deliveryStatus}`,
              )
            }}
          </div>
        </template>
        <template #[`item.createdAt`]="{ item }">
          <div>
            {{ $filters.formatDateTime(item.columns.createdAt) }}
          </div>
        </template>
      </v-data-table-server>
    </titled-view>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { mapActions, mapStores } from 'pinia';
import { useNotifications } from '@/stores/notifications';
import { VDataTableServer } from 'vuetify/labs/VDataTable';
import TitledView from '@/components/ui/TitledView.vue';
import { DataQuery, DataTableHeader } from '@/ui-types';
import { defaultItemsPerPageOptions } from '@/util/defaults';
import { DeliveryStatus } from '@/generated';
import { useSubscriptions } from '@/stores/subscriptions';
import XrdIconError from '@/components/icons/XrdIconError.vue';
import XrdIconDeclined from '@/components/icons/XrdIconDeclined.vue';
import XrdIconWarning from '@/components/icons/XrdIconWarning.vue';
import { Colors } from '@/global';
import { formatTimestamp } from '@/util/helpers';

export default defineComponent({
  components: {
    XrdIconWarning,
    XrdIconDeclined,
    XrdIconError,
    TitledView,
    VDataTableServer,
  },
  props: {
    managerId: {
      type: String,
      required: true,
    },
    roomId: {
      type: String,
      required: true,
    },
    subscriptionId: {
      type: String,
      required: true,
    },
  },
  data() {
    const options = defaultItemsPerPageOptions();
    return {
      colors: Colors,
      loading: true,
      dataQuery: { page: 1, itemsPerPage: 10 } as DataQuery,
      itemsPerPageOptions: options,
      pagingOptions: { page: 1, itemsPerPage: options[0].value } as DataQuery,
      period: [
        new Date(new Date().setDate(new Date().getDate() - 7)),
        new Date(),
      ],
      eventSearch: undefined as string | undefined,
      eventTypeSearch: undefined as string | undefined,
      selectedDeliveryStatus: null as DeliveryStatus | null,
    };
  },
  computed: {
    DeliveryStatusEnum() {
      return DeliveryStatus;
    },
    ...mapStores(useSubscriptions),
    headers(): DataTableHeader[] {
      return [
        {
          title: this.$t('subscriptionEventsList.header.eventId'),
          align: 'start',
          key: 'eventId',
        },
        {
          title: this.$t('subscriptionEventsList.header.eventType'),
          align: 'start',
          key: 'eventTypeIdentifier',
          sortable: true,
        },
        {
          title: this.$t('subscriptionEventsList.header.status'),
          align: 'start',
          key: 'deliveryStatus',
          sortable: true,
        },
        {
          title: this.$t('subscriptionEventsList.header.deliveryAttempts'),
          align: 'start',
          key: 'deliveryAttempts',
          sortable: true,
        },
        {
          title: this.$t('subscriptionEventsList.header.createdAt'),
          align: 'start',
          key: 'createdAt',
        },
      ];
    },
  },
  methods: {
    ...mapActions(useNotifications, ['showError', 'showSuccess']),
    formatDatePicker(dates: Date[]): String {
      return formatTimestamp(dates[0]) + ' - ' + formatTimestamp(dates[1]);
    },
    fetchSubscriptionEvents: async function (): Promise<void> {
      this.loading = true;
      await this.subscriptionsStore.loadSubscriptionEvents(
        this.managerId,
        this.roomId,
        this.subscriptionId,
        this.pagingOptions,
        this.period[0].toISOString(),
        this.period[1].toISOString(),
        this.eventSearch,
        this.eventTypeSearch,
        this.selectedDeliveryStatus,
      );
      try {
      } catch (error: unknown) {
        this.showError(error);
      } finally {
        this.loading = false;
      }
    },
    changeOptions: async function ({ itemsPerPage, page, sortBy }) {
      this.pagingOptions.itemsPerPage = itemsPerPage;
      this.pagingOptions.page = page;
      this.pagingOptions.sortBy = sortBy[0]?.key;
      this.pagingOptions.sortOrder = sortBy[0]?.order;
      await this.fetchSubscriptionEvents();
    },
    filterByStatus: async function () {
      if (!this.selectedSubscriptionStatus) {
        this.subscriptionsStore.subscriptionStatusFilter = undefined;
      } else {
        this.subscriptionsStore.subscriptionStatusFilter =
          this.selectedSubscriptionStatus;
      }
      await this.fetchSubscriptionEvents();
    },
  },
});
</script>

<style lang="scss" scoped>
@import '@/assets/colors';
@import '@/assets/tables';
@import '@/assets/global-style';

.list-view {
  min-height: 600px; /* this prevents datepicker from hiding behind parent elements and always have room to open */
}

.subscription-status-filter {
  max-width: 300px;
}
</style>
