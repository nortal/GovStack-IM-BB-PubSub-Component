<template>
  <div>
    <titled-view title-key="subscriptionsList.title">
      <v-row>
        <v-select
          class="subscription-status-filter"
          v-model="selectedSubscriptionStatus"
          :items="Object.values(ListSubscriptionsAsAdministratorStatusEnum)"
          :label="$t('subscriptionsList.filterByStatus')"
          variant="plain"
          @update:modelValue="filterByStatus"
          clearable
        />
      </v-row>

      <v-data-table-server
        :loading="loading"
        :headers="headers"
        :items="subscriptionsStore.subscriptions"
        :items-per-page="10"
        :items-length="subscriptionsStore.pagingOptions.total_items"
        :items-per-page-options="itemsPerPageOptions"
        class="xrd-table elevation-0 rounded"
        item-key="identifier"
        density="compact"
        @update:options="changeOptions"
      >
        <template #[`item.status`]="{ item }">
          <div>
            <xrd-icon-base>
              <XrdIconChecked
                v-if="item.columns.status == 'ACTIVE'"
                :color="colors.Success100"
              />
              <XrdIconError
                v-if="item.columns.status == 'PENDING'"
                :color="colors.Yellow"
              />
              <XrdIconDeclined
                v-if="item.columns.status == 'TERMINATED'"
                :color="colors.Error"
              />
              <XrdIconDeclined
                v-if="item.columns.status == 'REJECTED'"
                :color="colors.Error"
              />
            </xrd-icon-base>
            {{ $t('subscriptionsList.details.status.' + item.columns.status) }}
          </div>
        </template>
        <template #[`item.createdAt`]="{ item }">
          <div>
            {{ $filters.formatDateTime(item.columns.createdAt) }}
          </div>
        </template>
        <template #[`item.button`]="{ item }">
          <xrd-button
            v-if="
              item.raw.status ===
              ListSubscriptionsAsAdministratorStatusEnum.Active
            "
            class="xrd-clickable"
            text
            @click="openTerminateConfirmationDialog(item.raw)"
          >
            {{ $t('subscriptionsList.actions.terminate') }}
          </xrd-button>
          <xrd-button
            v-if="
              item.raw.status ===
              ListSubscriptionsAsAdministratorStatusEnum.Pending
            "
            class="xrd-clickable"
            color="error"
            text
            @click="openRejectConfirmationDialog(item.raw)"
          >
            {{ $t('subscriptionsList.actions.reject') }}
          </xrd-button>
          <xrd-button
            v-if="
              item.raw.status ===
              ListSubscriptionsAsAdministratorStatusEnum.Pending
            "
            class="xrd-clickable"
            color="success"
            text
            @click="approveSubscription(item.raw)"
          >
            {{ $t('subscriptionsList.actions.approve') }}
          </xrd-button>
          <xrd-button
            class="xrd-clickable"
            text
            @click="openSubscriptionDetailsDialog(item.raw)"
          >
            {{ $t('subscriptionsList.actions.details') }}
          </xrd-button>
        </template>
      </v-data-table-server>
    </titled-view>
  </div>

  <!--  Terminate subscription action -->
  <xrd-confirm-dialog
    v-if="showTerminateSubscriptionDialog"
    title="subscriptionsList.actions.terminateAction.terminateTitle"
    text="subscriptionsList.actions.terminateAction.terminateConfirm"
    :data="{ name: selectedSubscription.identifier }"
    :loading="terminatingSubscription"
    @cancel="showTerminateSubscriptionDialog = false"
    @accept="terminateSubscription()"
  />
  <!--  Reject subscription action -->
  <xrd-confirm-dialog
    v-if="showRejectSubscriptionDialog"
    title="subscriptionsList.actions.rejectAction.rejectTitle"
    text="subscriptionsList.actions.rejectAction.rejectConfirm"
    :data="{ name: selectedSubscription.identifier }"
    :loading="rejectingSubscription"
    @cancel="showRejectSubscriptionDialog = false"
    @accept="rejectSubscription()"
  />

  <!--  Subscription details modal-->
  <subscription-details-dialog
    v-if="showSubscriptionDetailsDialog"
    :subscription="selectedSubscription"
    :manager-id="managerId"
    :room-id="roomId"
    @cancel="closeSubscriptionDetailsDialog"
  />
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { mapActions, mapStores } from 'pinia';
import { useNotifications } from '@/stores/notifications';
import { VDataTableServer } from 'vuetify/labs/VDataTable';
import TitledView from '@/components/ui/TitledView.vue';
import XrdConfirmDialog from '@/components/XrdConfirmDialog.vue';
import XrdButton from '@/components/XrdButton.vue';
import { DataQuery, DataTableHeader } from '@/ui-types';
import { defaultItemsPerPageOptions } from '@/util/defaults';
import {
  ListSubscriptionsAsAdministratorStatusEnum,
  Subscription,
} from '@/generated';
import { useSubscriptions } from '@/stores/subscriptions';
import UpdatePublishersDialog from '@/components/publishers/UpdatePublishersDialog.vue';
import SubscriptionDetailsDialog from '@/components/subscriptions/SubscriptionDetailsDialog.vue';
import { Colors } from '@/global';
import XrdIconError from '@/components/icons/XrdIconError.vue';
import XrdIconDeclined from '@/components/icons/XrdIconDeclined.vue';
import XrdIconWarning from '@/components/icons/XrdIconWarning.vue';

export default defineComponent({
  name: 'Subscriptions',
  components: {
    XrdIconWarning,
    XrdIconDeclined,
    XrdIconError,
    SubscriptionDetailsDialog,
    UpdatePublishersDialog,
    TitledView,
    VDataTableServer,
    XrdConfirmDialog,
    XrdButton,
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
  },
  data() {
    const options = defaultItemsPerPageOptions();
    return {
      colors: Colors,
      loading: true,
      dataQuery: { page: 1, itemsPerPage: 10 } as DataQuery,
      showSubscriptionDetailsDialog: false,
      showTerminateSubscriptionDialog: false,
      showRejectSubscriptionDialog: false,
      terminatingSubscription: false,
      rejectingSubscription: false,
      selectedSubscriptionStatus:
        null as ListSubscriptionsAsAdministratorStatusEnum | null,
      itemsPerPageOptions: options,
      pagingOptions: { page: 1, itemsPerPage: options[0].value } as DataQuery,
      selectedSubscription: {} as Subscription,
    };
  },
  computed: {
    ListSubscriptionsAsAdministratorStatusEnum() {
      return ListSubscriptionsAsAdministratorStatusEnum;
    },
    ...mapStores(useSubscriptions),
    headers(): DataTableHeader[] {
      return [
        {
          title: this.$t('subscriptionsList.header.identifier'),
          align: 'start',
          key: 'identifier',
        },
        {
          title: this.$t('subscriptionsList.header.status'),
          align: 'start',
          key: 'status',
          sortable: true,
        },
        {
          title: this.$t('subscriptionsList.header.method'),
          align: 'start',
          key: 'method',
          sortable: false,
        },
        {
          title: this.$t('subscriptionsList.header.createdAt'),
          align: 'start',
          key: 'createdAt',
        },
        {
          title: this.$t('subscriptionsList.header.actions'),
          align: 'end',
          key: 'button',
          sortable: false,
        },
      ];
    },
  },
  methods: {
    ...mapActions(useNotifications, ['showError', 'showSuccess']),
    fetchSubscriptions: async function (): Promise<void> {
      this.loading = true;
      await this.subscriptionsStore.loadSubscriptions(
        this.managerId,
        this.roomId,
        this.pagingOptions,
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
      await this.fetchSubscriptions();
    },
    filterByStatus: async function () {
      if (!this.selectedSubscriptionStatus) {
        this.subscriptionsStore.subscriptionStatusFilter = undefined;
      } else {
        this.subscriptionsStore.subscriptionStatusFilter =
          this.selectedSubscriptionStatus;
      }
      await this.fetchSubscriptions();
    },
    openSubscriptionDetailsDialog(subscription: Subscription): void {
      this.selectedSubscription = subscription;
      this.showSubscriptionDetailsDialog = true;
    },
    closeSubscriptionDetailsDialog(): void {
      console.log('trigger');
      this.selectedSubscription = {} as Subscription;
      this.showSubscriptionDetailsDialog = false;
    },
    openTerminateConfirmationDialog(subscription: Subscription): void {
      this.selectedSubscription = subscription;
      this.showTerminateSubscriptionDialog = true;
    },
    terminateSubscription(): void {
      if (!this.selectedSubscription) return;

      this.terminatingSubscription = true;
      this.subscriptionsStore
        .terminateSubscription(
          this.roomId,
          this.managerId,
          this.selectedSubscription.id,
        )
        .then(() => {
          this.showSuccess(
            this.$t('subscriptionsList.actions.terminateAction.success', {
              subscriptionIdentifier: this.selectedSubscription.identifier,
            }),
          );
          this.showTerminateSubscriptionDialog = false;
          this.terminatingSubscription = false;
          this.selectedSubscription = {} as Subscription;
          this.fetchSubscriptions();
        })
        .catch((error) => {
          this.showError(error);
        });
    },
    openRejectConfirmationDialog(subscription: Subscription): void {
      this.selectedSubscription = subscription;
      this.showRejectSubscriptionDialog = true;
    },
    rejectSubscription(): void {
      if (!this.selectedSubscription) return;

      this.rejectingSubscription = true;
      this.subscriptionsStore
        .rejectSubscription(
          this.roomId,
          this.managerId,
          this.selectedSubscription.id,
        )
        .then(() => {
          this.showSuccess(
            this.$t('subscriptionsList.actions.rejectAction.success', {
              subscriptionIdentifier: this.selectedSubscription.identifier,
            }),
          );
          this.showRejectSubscriptionDialog = false;
          this.rejectingSubscription = false;
          this.selectedSubscription = {} as Subscription;
          this.fetchSubscriptions();
        })
        .catch((error) => {
          this.showError(error);
        });
    },
    approveSubscription(subscription: Subscription): void {
      this.subscriptionsStore
        .approveSubscription(this.roomId, this.managerId, subscription.id)
        .then(() => {
          this.showSuccess(
            this.$t('subscriptionsList.actions.approveAction.success', {
              subscriptionIdentifier: subscription.identifier,
            }),
          );
          this.fetchSubscriptions();
        })
        .catch((error) => {
          this.showError(error);
        });
    },
  },
});
</script>

<style lang="scss" scoped>
@import '@/assets/colors';
@import '@/assets/tables';
@import '@/assets/global-style';

.subscription-status-filter {
  max-width: 300px;
}
</style>
