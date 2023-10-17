<template>
  <div>
    <router-view name="top" />
    <v-row align="center" justify="center" style="margin-top: 0">
      <transition name="fade" mode="out-in">
        <div class="base-full-width">
          <div class="sticky">
            <router-view name="alerts" />
          </div>
          <v-row
            align="center"
            justify="center"
            class="base-full-width bottom-pad"
          >
            <router-view />
          </v-row>
        </div>
      </transition>
    </v-row>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { useAuth } from "@/stores/auth";
import { mapState } from "pinia";
import { signOutRedirect } from "@/oidc/oidc";

export default defineComponent({
  data() {
    return {};
  },
  computed: {
    ...mapState(useAuth, ["isSessionAlive"]),
  },
  methods: {
    logout(): void {
      signOutRedirect();
    },
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/shared";

.logout-text {
  font-size: 14px !important;
}

.sticky {
  position: -webkit-sticky;
  position: sticky;
  top: 4px;
  z-index: 7; // Vuetify drop menu has z-index 8 so this goes just under those. Modals/dialogs have z-index 202
}

.base-full-width {
  width: 100%;
  padding-bottom: 40px;
}

.fade-enter-active,
.fade-leave-active {
  transition-duration: 0.2s;
  transition-property: opacity;
  transition-timing-function: ease;
}

.fade-enter,
.fade-leave-active {
  opacity: 0;
}
</style>
