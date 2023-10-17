import type { NavigationGuardNext, RouteLocation } from "vue-router";
import { createRouter, createWebHistory } from "vue-router";
import routes from "@/router/routes";
import { RouteName, UserRole } from "@/global";
import { useAuth } from "@/stores/auth";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: routes,
});

router.beforeEach(
  async (to: RouteLocation, from: RouteLocation, next: NavigationGuardNext) => {
    const authStore = useAuth();
    await authStore.checkAuthentication();
    if (to.name === RouteName.Login) {
      if (authStore.isAuthenticated && authStore.isSessionAlive) {
        next({ name: RouteName.BaseRoute });
      } else {
        next();
      }
      return;
    }

    if (authStore.isAuthenticated && authStore.isSessionAlive) {
      if (!to?.meta?.roles) {
        next({ name: RouteName.BaseRoute });
      } else if (await authStore.hasAnyOfRoles(to.meta.roles as UserRole[])) {
        next();
      } else {
        next({
          name: RouteName.Forbidden,
        });
      }
      return;
    } else if (to.name === RouteName.OidcCallback) {
      next();
    } else {
      next({
        name: RouteName.Login,
      });
    }
    return;
  },
);

export default router;
