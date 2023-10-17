<template>
  <v-row>
    <titled-view title-key="publishersList.title" />
    <xrd-button class="xrd-button" @click="showAddPublisherDialog = true">
      <v-icon class="xrd-large-button-icon">icon-Add</v-icon>
      {{ $t('publishersList.actions.add') }}
    </xrd-button>
  </v-row>
  <v-data-table-server
    :loading="loading"
    :headers="headers"
    :items="publishersStore.publishers"
    :items-per-page="10"
    :items-length="publishersStore.pagingOptions.total_items"
    :items-per-page-options="itemsPerPageOptions"
    class="xrd-table elevation-0 rounded"
    item-key="identifier"
    density="compact"
    @update:options="changeOptions"
  >
    <template #[`item.createdAt`]="{ item }">
      <div>
        {{ $filters.formatDateTime(item.columns.createdAt) }}
      </div>
    </template>
    <template #[`item.button`]="{ item }">
      <xrd-button
        class="xrd-clickable"
        text
        @click="openViewConstrains(item.raw)"
      >
        {{ $t('publishersList.actions.viewConstraints') }}
      </xrd-button>
      <xrd-button
        class="xrd-clickable"
        text
        @click="openUpdatePublisherDialog(item.raw)"
      >
        {{ $t('publishersList.actions.update') }}
      </xrd-button>
      <xrd-button
        class="xrd-clickable"
        text
        @click="openTerminateConfirmationDialog(item.raw)"
      >
        {{ $t('publishersList.actions.terminate') }}
      </xrd-button>
    </template>
  </v-data-table-server>

  <!--  Create publishers action-->
  <view-publisher-constraints-dialog
    v-if="showPublisherConstraints"
    :publisher="selectedPublisher"
    @cancel="showPublisherConstraints = false"
    @save="hideUpdatePublishersDialogAndRefetch"
  />

  <!--  Create publishers action-->
  <add-publisher-dialog
    v-if="showAddPublisherDialog"
    :room-id="roomId"
    :manager-id="managerId"
    @cancel="showAddPublisherDialog = false"
    @save="hideAddPublisherDialogAndRefetch"
  />

  <!--  Update publishers action-->
  <update-publishers-dialog
    v-if="showUpdatePublisherDialog"
    :room-id="roomId"
    :manager-id="managerId"
    :publisher="selectedPublisher"
    @cancel="showUpdatePublisherDialog = false"
    @save="hideAddPublisherDialogAndRefetch"
  />

  <!--  Terminate publishers action -->
  <xrd-confirm-dialog
    v-if="showTerminatePublisherDialog"
    title="publishersList.actions.terminateAction.terminateTitle"
    text="publishersList.actions.terminateAction.terminateConfirm"
    :data="{ publisher: selectedPublisher.identifier }"
    :loading="terminatingPublisher"
    @cancel="showTerminatePublisherDialog = false"
    @accept="terminatePublisher()"
  />
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { mapActions, mapStores } from 'pinia';
import { useNotifications } from '@/stores/notifications';
import TitledView from '@/components/ui/TitledView.vue';
import XrdButton from '@/components/XrdButton.vue';
import { VDataTableServer } from 'vuetify/labs/VDataTable';
import { DataQuery, DataTableHeader } from '@/ui-types';
import { defaultItemsPerPageOptions } from '@/util/defaults';
import { usePublishers } from '@/stores/publishers';
import XrdConfirmDialog from '@/components/XrdConfirmDialog.vue';
import { PublisherViewResponse } from '@/generated';
import XrdIconKey from '@/components/icons/XrdIconKey.vue';
import AddPublisherDialog from '@/components/publishers/AddPublisherDialog.vue';
import ViewPublisherConstraintsDialog from '@/components/publishers/ViewPublisherConstraintsDialog.vue';
import UpdatePublishersDialog from '@/components/publishers/UpdatePublishersDialog.vue';

export default defineComponent({
  name: 'Publishers',
  components: {
    UpdatePublishersDialog,
    ViewPublisherConstraintsDialog,
    AddPublisherDialog,
    XrdIconKey,
    XrdConfirmDialog,
    XrdButton,
    VDataTableServer,
    TitledView,
  },
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
    const options = defaultItemsPerPageOptions();
    return {
      search: '',
      loading: true,
      itemsPerPageOptions: defaultItemsPerPageOptions(),
      showPublisherConstraints: false,
      showAddPublisherDialog: false,
      showUpdatePublisherDialog: false,
      showTerminatePublisherDialog: false,
      terminatingPublisher: false,
      selectedPublisher: {} as PublisherViewResponse,
      pagingOptions: { page: 1, itemsPerPage: options[0].value } as DataQuery,
    };
  },
  computed: {
    ...mapStores(usePublishers),
    headers(): DataTableHeader[] {
      return [
        {
          title: this.$t('publishersList.header.identifier'),
          align: 'start',
          key: 'identifier',
        },
        {
          title: this.$t('publishersList.header.identifierType'),
          align: 'start',
          key: 'identifierType',
        },
        {
          title: this.$t('publishersList.header.createdAt'),
          align: 'start',
          key: 'createdAt',
        },
        {
          title: this.$t('publishersList.header.createdBy'),
          align: 'start',
          key: 'createdBy',
        },
        {
          title: this.$t('publishersList.header.actions'),
          align: 'center',
          key: 'button',
          sortable: false,
        },
      ];
    },
  },
  methods: {
    ...mapActions(useNotifications, ['showError', 'showSuccess']),

    fetchPublishers: async function (): Promise<void> {
      this.loading = true;
      try {
        await this.publishersStore.fetchPublishers(
          this.managerId,
          this.roomId,
          this.pagingOptions,
        );
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
      this.pagingOptions.search = '';
      await this.fetchPublishers();
    },
    hideAddPublisherDialogAndRefetch() {
      this.showAddPublisherDialog = false;
      this.fetchPublishers();
    },
    hideUpdatePublishersDialogAndRefetch() {
      this.showUpdatePublisherDialog = false;
      this.selectedPublisher = {} as PublisherViewResponse;
      this.fetchPublishers();
    },
    openTerminateConfirmationDialog(publisher: PublisherViewResponse): void {
      this.selectedPublisher = publisher;
      this.showTerminatePublisherDialog = true;
    },
    openViewConstrains(publisher: PublisherViewResponse): void {
      this.selectedPublisher = publisher;
      this.showPublisherConstraints = true;
    },
    openUpdatePublisherDialog(publisher: PublisherViewResponse): void {
      this.selectedPublisher = publisher;
      this.showUpdatePublisherDialog = true;
    },
    terminatePublisher(): void {
      if (!this.selectedPublisher) return;

      this.terminatingPublisher = true;
      this.publishersStore
        .terminatePublisher(
          this.managerId,
          this.roomId,
          this.selectedPublisher.id,
        )
        .then(() => {
          this.showSuccess(
            this.$t('publishersList.actions.terminateAction.success', {
              publisher: this.selectedPublisher.identifier,
            }),
          );
          this.showTerminatePublisherDialog = false;
          this.terminatingPublisher = false;
          this.selectedPublisher = {} as PublisherViewResponse;
          this.fetchPublishers();
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
</style>
