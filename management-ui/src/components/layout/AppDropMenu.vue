<template>
  <div class="drop-menu">
    <v-menu bottom right>
      <template #activator="{ props }">
        <v-btn variant="text" class="no-uppercase" data-test="username-button" v-bind="props">
          {{ username }}
          <v-icon>mdi-chevron-down</v-icon>
        </v-btn>
      </template>

      <v-list>
        <v-list-item
          id="logout-list-tile"
          data-test="logout-list-tile"
          @click="logout"
        >
          <v-list-item-title id="logout-title"
            >{{ $t("login.logOut") }}
          </v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { mapState } from "pinia";
import { RouteName } from "@/global";
import { useAuth } from "@/stores/auth";

export default defineComponent({
  computed: {
    ...mapState(useAuth, ["username"]),
  },
  methods: {
    logout(): void {
      this.$router.replace({ name: RouteName.Logout });
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
}

.no-uppercase {
  text-transform: none;
  font-weight: 600;
}
</style>
