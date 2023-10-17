<template>
  <!-- Maybe some spinner ? -->
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { signInRedirectCallback } from "@/oidc/oidc";
import { useRouter } from "vue-router";
import { RouteName } from "@/global";
import { mapActions } from "pinia";
import { useNotifications } from "@/stores/notifications";

export default defineComponent({
  methods: {
    ...mapActions(useNotifications, ["showError"]),
  },
  async mounted() {
    const router = useRouter();
    try {
      await signInRedirectCallback();
      await router.push({ name: RouteName.Rooms });
      router.go(0)
    } catch (error) {
      this.showError({ error: error });
      await router.push({ name: RouteName.Login });
    }
  },
});
</script>
