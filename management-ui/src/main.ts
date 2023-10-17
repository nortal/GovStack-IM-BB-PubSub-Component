import { createApp } from 'vue';
import { createPinia } from 'pinia';
import App from './App.vue';
import vuetify from '@/plugins/vuetify';
import router from '@/router/router';
import '@fontsource/open-sans/800.css';
import '@fontsource/open-sans/700.css';
import '@fontsource/open-sans';
import '@/oidc/oidc';
import i18n from '@/i18n';
import { createPersistedState } from 'pinia-plugin-persistedstate';
import '@/assets/icons.css';
import XrdIconFolderOutline from '@/components/icons/XrdIconFolderOutline.vue';
import XrdIconBase from '@/components/icons/XrdIconBase.vue';
import XrdIconChecker from '@/components/icons/XrdIconChecker.vue';
import XrdIconClose from '@/components/icons/XrdIconClose.vue';
import XrdIconChecked from '@/components/icons/XrdIconChecked.vue';
import XrdIconAdd from '@/components/icons/XrdIconAdd.vue';
import XrdIconCopy from '@/components/icons/XrdIconCopy.vue';
import XrdIconCertificate from '@/components/icons/XrdIconCertificate.vue';
import XrdButton from '@/components/XrdButton.vue';
import XrdSearch from '@/components/XrdSearch.vue';
import XrdCloseButton from '@/components/XrdCloseButton.vue';
import XrdSubViewContainer from '@/components/XrdSubViewContainer.vue';
import XrdSimpleDialog from '@/components/XrdSimpleDialog.vue';
import XrdConfirmDialog from '@/components/XrdConfirmDialog.vue';
import XrdEmptyPlaceholder from '@/components/XrdEmptyPlaceholder.vue';
import XrdSubViewTitle from '@/components/XrdSubViewTitle.vue';
import { createFilters } from '@/filters';
import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css';

const app = createApp(App);

const pinia = createPinia();
pinia.use(
  createPersistedState({
    storage: sessionStorage,
  }),
);

app.use(pinia);
app.use(router);
app.use(vuetify);
app.use(i18n);
app.use(createFilters());

//icons
app.component('XrdIconFolderOutline', XrdIconFolderOutline);
app.component('XrdIconBase', XrdIconBase);
app.component('XrdIconChecker', XrdIconChecker);
app.component('XrdIconClose', XrdIconClose);
app.component('XrdIconChecked', XrdIconChecked);
app.component('XrdIconAdd', XrdIconAdd);
app.component('XrdIconCopy', XrdIconCopy);
app.component('XrdIconCertificate', XrdIconCertificate);
//components
app.component('XrdButton', XrdButton);
app.component('XrdSearch', XrdSearch);
app.component('XrdCloseButton', XrdCloseButton);
app.component('XrdSubViewContainer', XrdSubViewContainer);
app.component('XrdSimpleDialog', XrdSimpleDialog);
app.component('XrdConfirmDialog', XrdConfirmDialog);
app.component('XrdEmptyPlaceholder', XrdEmptyPlaceholder);
app.component('XrdSubViewTitle', XrdSubViewTitle);

app.component('VueDatePicker', VueDatePicker);

app.mount('#app');
