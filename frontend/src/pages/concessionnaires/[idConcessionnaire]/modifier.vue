<template>
  <v-card class="ma-3 pa-3" style="max-width: 600px">
    <EditionConcessionnaire v-if="id" :idConcessionnaire="id"/>
  </v-card>
</template>

<script setup>
import EditionConcessionnaire from "@/components/EditionConcessionnaire";
import {useRoute, useRouter} from "vue-router";
import {onMounted, ref} from "vue";

const router = useRouter();
const route = useRoute();

const id = ref(null)

onMounted(() => {
  try {
    id.value = parseInt(route.params.idConcessionnaire)
  } catch (e) {
    console.error(e)
    router.replace("/")
  }
})

</script>
