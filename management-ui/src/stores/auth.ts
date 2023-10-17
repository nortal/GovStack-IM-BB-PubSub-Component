import { defineStore } from 'pinia';
import { getUser } from '@/oidc/oidc';
import type { User } from 'oidc-client-ts';
import type { Nullable } from 'vitest';
import { UserRole } from '@/global';

export interface State {
  user: User | null;
  isAuthenticated: boolean;
  userRoles: UserRole[];
  isSessionAlive: boolean;
}

export const useAuth = defineStore('auth', {
  state: (): State => ({
    user: null as User | null,
    isAuthenticated: false,
    userRoles: [] as UserRole[],
    isSessionAlive: false,
  }),

  actions: {
    async checkAuthentication() {
      const user: User | null = await getUser();
      this.user = user;
      this.isAuthenticated = !!user;
      this.isSessionAlive = !user?.expired;
      if (user?.profile?.roles) {
        await this.setUserRoles(user);
      }
    },
    async hasAnyOfRoles(roles: UserRole[]) {
      return roles?.some((role) => this.userRoles.includes(role));
    },
    async setUserRoles(user: User) {
      if (user && user.profile) {
        const roles = user.profile['roles'];
        if (roles && Array.isArray(roles)) {
          this.userRoles =
            roles
              .filter((role) =>
                Object.values(UserRole).includes(role as UserRole),
              )
              .map((role) => role as UserRole) || [];
        }
      }
    },
    isAdministrator(): boolean {
      return (
        this.userRoles.includes(UserRole.SystemAdministrator) ||
        this.userRoles.includes(UserRole.ServiceAdministrator)
      );
    },
    isManager(): boolean {
      return this.userRoles.includes(UserRole.PubSubManager);
    },
  },

  getters: {
    username(): Nullable<string> {
      return this.user?.profile.preferred_username || null;
    },
  },
});
