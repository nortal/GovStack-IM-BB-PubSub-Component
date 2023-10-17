<script lang="ts">
// The root component of the Vue app
import { defineComponent } from "vue";
import Snackbar from "@/components/ui/Snackbar.vue";
import AppFooter from "@/components/layout/AppFooter.vue";
import AppToolbar from "@/components/layout/AppToolbar.vue";
import { RouteName } from "@/global";

export default defineComponent({
  name: "App",

  components: {
    AppFooter,
    AppToolbar,
    Snackbar,
  },
  computed: {
    loginView(): boolean {
      return this.$route.name !== RouteName.Login;
    },
  },
});
</script>

<template>
  <v-app class="xrd-app">
    <!-- Dont show toolbar or footer in login view -->
    <app-toolbar v-if="loginView" />
    <v-main app>
      <router-view v-slot="{ Component }">
        <transition name="fade" mode="out-in">
          <component :is="Component" />
        </transition>
      </router-view>
    </v-main>
    <snackbar />
    <app-footer v-if="loginView" />
  </v-app>
</template>

<style scoped></style>
