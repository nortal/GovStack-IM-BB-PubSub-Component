<script lang="ts">
import { computed, defineComponent, onMounted } from "vue";
import { useAuth } from "@/stores/auth";
import { RouterLink } from "vue-router";

export default defineComponent({
  components: { RouterLink },
  setup() {
    const authStore = useAuth();

    return {
      isAuthenticated: computed(() => authStore.isAuthenticated),
      username: computed(() => authStore.username),
    };
  },
});
</script>

<template>
  <div v-if="isAuthenticated" class="toolbar">
    <RouterLink to="/">Base</RouterLink>
    |
    <RouterLink to="/home">Home</RouterLink>
    |
    <RouterLink to="/about">About</RouterLink>
    |
    <RouterLink to="/logout">Logout</RouterLink>
    |
    <p>Logged in as : {{ username }}</p>
  </div>
</template>

<style scoped></style>
