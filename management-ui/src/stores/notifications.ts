import { ActionError, Notification } from "@/ui-types";
import { defineStore } from "pinia";
import { TranslateResult } from "vue-i18n";
import axios from "axios";

// Helper functions

// Finds if an array of notifications contains a similar notification.
function containsNotification(
  errorNotifications: Notification[],
  notification: Notification,
): number {
  if (!notification || !errorNotifications || errorNotifications.length === 0) {
    return -1;
  }
  return errorNotifications.findIndex((e: Notification) => {
    if (notification?.responseData !== e?.responseData) {
      return false;
    }

    if (notification?.url !== e?.url) {
      return false;
    }

    if (notification?.status !== e?.status) {
      return false;
    }

    if (notification?.errorCode !== e?.errorCode) {
      return false;
    }

    return notification?.errorMessage === e?.errorMessage;
  });
}

// Add error notification to the store
function addErrorNotification(
  errorNotifications: Notification[],
  notification: Notification,
): Notification[] {
  // Check for duplicate
  const index = containsNotification(errorNotifications, notification);

  if (index > -1) {
    // If there is a duplicate, remove it and increase the count
    notification.count = errorNotifications[index].count + 1;
    errorNotifications.splice(index, 1);
  }

  errorNotifications.push(notification);
  return errorNotifications;
}

function createEmptyNotification(timeout: number): Notification {
  // Returns a new "empty" notification
  return {
    timeout: timeout,
    timeAdded: Date.now(),
    show: true,
    count: 1,
  };
}

export const useNotifications = defineStore("notifications", {
  state: () => {
    return {
      errorNotifications: [] as Notification[],
      successNotifications: [] as Notification[],
      continueInitialisation: false,
    };
  },

  actions: {
    deleteNotification(id: number): void {
      this.errorNotifications = this.errorNotifications.filter(
        (item: Notification) => item.timeAdded !== id,
      );
    },

    deleteSuccessNotification(id: number): void {
      this.successNotifications = this.successNotifications.filter(
        (item: Notification) => item.timeAdded !== id,
      );
    },

    resetNotifications() {
      const preserved: Notification[] = [];
      this.successNotifications
        .filter((not) => not.preserve)
        .forEach((not) => {
          not.preserve = false;
          preserved.push(not);
        });
      // Clear the store state
      this.$reset();
      this.successNotifications.push(...preserved);
    },

    // Show error notification with axios error object
    showError(errorObject: unknown): void {
      // Show error using the x-road specific data in an axios error object
      // Don't show errors when the errorcode is 401 which is usually because of session expiring
      if (axios.isAxiosError(errorObject)) {
        if (errorObject?.response?.status !== 401) {
          const notification = createEmptyNotification(-1);

          // Store error object as a string that can be shown to the user
          notification.errorObjectAsString = errorObject.response?.data.message;

          // Data shown in nofitication component
          notification.errorCode = errorObject?.response?.data?.error?.code;
          notification.metaData = errorObject?.response?.data?.error?.metadata;
          notification.responseData = errorObject?.response?.config?.data;
          notification.errorId =
            errorObject?.response?.headers["x-road-ui-correlation-id"];

          // Data needed to compare with other notificatios for handling duplicates
          notification.url = errorObject?.response?.config?.url;
          notification.status = String(errorObject?.response?.status);

          this.errorNotifications = addErrorNotification(
            this.errorNotifications,
            notification,
          );
        }
      } else if (errorObject instanceof Error) {
        this.showErrorMessage(errorObject.message);
      } else {
        this.showErrorMessage("Unexpected error");
      }
    },

    showErrorMessage(messageText: string | TranslateResult): void {
      // Show error snackbar with text string
      const notification = createEmptyNotification(-1);
      notification.errorMessage = messageText as string;
      this.errorNotifications = addErrorNotification(
        this.errorNotifications,
        notification,
      );
    },

    showSuccess(messageText: string | TranslateResult, preserve = false): void {
      // Show success snackbar with text string
      const notification = createEmptyNotification(3000);
      notification.successMessage = messageText as string;
      notification.preserve = preserve;
      this.successNotifications.push(notification);
    },

    // Add error with an action
    setErrorAction(val: ActionError): void {
      const notification = createEmptyNotification(-1);
      notification.action = val.action;
      notification.errorMessage = val.errorMessage;
      this.errorNotifications = addErrorNotification(
        this.errorNotifications,
        notification,
      );
    },
  },
});
