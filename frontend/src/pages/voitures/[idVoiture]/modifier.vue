<template>
  <v-card class="ma-3 pa-3" style="max-width: 600px">
    <EditionVoiture v-if="id" :idVoiture="id"/>
  </v-card>
</template>

<script setup>
import EditionVoiture from "@/components/EditionVoiture.vue"
import {useRoute, useRouter} from "vue-router"
import {onMounted, ref} from "vue"

const router = useRouter()
const route = useRoute()

const id = ref(null)

onMounted(() => {
  try {
    id.value = parseInt(route.params.idVoiture)
  } catch (e) {
    console.error(e)
    router.replace("/")
  }
})
</script>
