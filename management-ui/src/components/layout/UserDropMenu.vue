<template>
  <div class="drop-menu">
    <v-menu location="bottom">
      <template #activator="{ props }">
        <v-btn
          variant="text"
          class="no-uppercase"
          data-test="username-button"
          v-bind="props"
        >
          {{ username }}
          <v-icon icon="mdi-chevron-down" />
        </v-btn>
      </template>

      <v-list>
        <v-list-item
          id="logout-list-tile"
          data-test="logout-list-tile"
          @click="logout"
        >
          <v-list-item-title id="logout-title"
            >{{ $t("logout.logoutButton") }}
          </v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { RouteName } from "@/global";
import { mapState } from "pinia";
import { useAuth } from "@/stores/auth";

export default defineComponent({
  computed: {
    ...mapState(useAuth, ["username"]),
  },
  methods: {
    logout(): void {
      sessionStorage.clear();
      this.$router.push({ name: RouteName.Logout });
    },
  },
});
</script>

<style lang="scss" scoped>
.drop-menu {
  margin-left: auto;
  margin-right: 70px;
  display: flex;
  align-items: center;

  .no-uppercase {
    text-transform: none;
    font-weight: 600;
  }
}
</style>
