<!--
   The MIT License

   Copyright (c) 2019- Nordic Institute for Interoperability Solutions (NIIS)
   Copyright (c) 2018 Estonian Information System Authority (RIA),
   Nordic Institute for Interoperability Solutions (NIIS), Population Register Centre (VRK)
   Copyright (c) 2015-2017 Estonian Information System Authority (RIA), Population Register Centre (VRK)

   Permission is hereby granted, free of charge, to any person obtaining a copy
   of this software and associated documentation files (the "Software"), to deal
   in the Software without restriction, including without limitation the rights
   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
   copies of the Software, and to permit persons to whom the Software is
   furnished to do so, subject to the following conditions:

   The above copyright notice and this permission notice shall be included in
   all copies or substantial portions of the Software.

   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
   THE SOFTWARE.
 -->
<template>
  <v-footer class="footer">
    <v-container class="pb-13">
      <v-row>
        <v-col cols="2" class="pt-4">
          <v-img
            :src="logo"
            height="35"
            width="132"
            max-height="35"
            max-width="132"
          ></v-img>
        </v-col>
        <v-col cols="3" class="footer-col pt-5">
          <v-row>
            <v-col>
              <span class="footer-title">{{
                $t("footer.software.title")
              }}</span>
            </v-col>
          </v-row>
          <v-row>
            <v-col class="py-0">
              {{ $t("footer.software.versionPrefix") }}&nbsp;
              <span data-test="app-footer-server-version">{{
                appVersion
              }}</span>
            </v-col>
          </v-row>
        </v-col>
        <v-col class="footer-col pt-5">
          <v-row>
            <v-col>
              <span class="footer-title">{{
                $t("footer.copyright.title")
              }}</span>
            </v-col>
          </v-row>
          <v-row>
            <v-col class="py-0">
              <a
                rel="noopener"
                class="footer-link"
                href="https://www.govstack.global/"
                target="_blank"
              >
                {{ $t("footer.copyright.company") }}
              </a>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <a
                rel="noopener"
                class="footer-link"
                href="https://github.com/GovStackWorkingGroup/bb-information-mediator/blob/main/LICENSE.txt"
                target="_blank"
              >
                {{ $t("footer.copyright.licenceInfo") }}
              </a>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
    </v-container>
  </v-footer>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import version from "@/version";
import logo from "@/assets/bullhorn_black.svg";

export default defineComponent({
  name: "AppFooter",
  computed: {
    appVersion(): string {
      return version;
    },
    logo(): string {
      return logo;
    },
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/colors";
$text-color: $XRoad-Black100;

.footer {
  background: $XRoad-WarmGrey30;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.footer-title {
  color: $text-color;
  font-size: 0.9rem;
  font-weight: bold;
}

.footer-col {
  color: $text-color;
  font-size: 0.875rem;
}

.footer-link {
  color: $XRoad-Purple100;
}
</style>
