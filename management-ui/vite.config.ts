import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'
import {resolve} from "node:path";
import vuetify from "vite-plugin-vuetify";

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [
        vue(),
        vueJsx(),
        vuetify({autoImport: true}),],
    resolve: {
        alias: {
            '@': resolve(__dirname, 'src'),
            'vue-i18n': 'vue-i18n/dist/vue-i18n.cjs.js',
        }
    },
    build: {
        cssCodeSplit: false,
    },
    define: {
        __VUE_I18N_FULL_INSTALL__: true,
        __VUE_I18N_LEGACY_API__: false
    },
    server: {
        https: false,
        port: 5173,
        host: 'localhost',
        fs: {
            // Allow serving files from one level up to the project root
            allow: ['.'],
        }
    }
})
