<template>
  <div>
    <!-- Success -->
    <v-snackbar
      v-for="notification in successNotifications"
      :key="notification.timeAdded"
      v-model="notification.show"
      transition="fab-transition"
      :timeout="snackbarTimeout(notification.timeout)"
      :color="colors.Success10"
      :min-width="760"
      data-test="success-snackbar"
      multi-line
      class="success-snackbar"
      @input="closeSuccess(notification.timeAdded)"
    >
      <div class="row-wrapper-top scrollable identifier-wrap">
        <xrd-icon-base :color="colors.Success100">
          <xrd-icon-checker />
        </xrd-icon-base>

        <div v-if="notification.successMessage" class="row-wrapper">
          {{ notification.successMessage }}
        </div>
      </div>
      <template #actions>
        <v-btn
          icon
          variant="text"
          rounded
          :color="colors.Black100"
          data-test="close-snackbar"
          @click="closeSuccess(notification.timeAdded)"
        >
          <xrd-icon-base>
            <xrd-icon-close />
          </xrd-icon-base>
        </v-btn>
      </template>
    </v-snackbar>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { Colors } from "@/global";
import { mapActions, mapState } from "pinia";
import { useNotifications } from "@/stores/notifications";

declare global {
  interface Window {
    e2eTestingMode?: boolean;
  }
}

export default defineComponent({
  // Component for snackbar notifications
  data() {
    return {
      colors: Colors,
    };
  },
  computed: {
    ...mapState(useNotifications, ["successNotifications"]),
    // Check global window value to see if e2e testing mode should be enabled
    transitionName: () => (window.e2eTestingMode === true ? null : "fade"),
  },
  methods: {
    ...mapActions(useNotifications, ["deleteSuccessNotification"]),
    closeSuccess(id: number): void {
      this.deleteSuccessNotification(id);
    },
    // Check global window value to see if e2e testing mode should be enabled
    snackbarTimeout(timeout: number) {
      return window.e2eTestingMode === true ? -1 : timeout;
    },
  },
});
</script>

<style lang="scss">
.success-snackbar > .v-snack__wrapper {
  // Customised size for snackbar
  min-height: 88px;
  min-width: 760px;
}
</style>

<style lang="scss" scoped>
.row-wrapper-top {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  padding-left: 14px;

  .row-wrapper {
    display: flex;
    flex-direction: column;
    width: 100%;
    overflow-wrap: break-word;
    justify-content: flex-start;
    margin-right: 30px;
    margin-left: 26px;
    color: #211e1e;
    font-style: normal;
    font-weight: bold;
    font-size: 18px;
    line-height: 24px;
  }
}

.scrollable {
  overflow-y: auto;
  max-height: 300px;
}
</style>
