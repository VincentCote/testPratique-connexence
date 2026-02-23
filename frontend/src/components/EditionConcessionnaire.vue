<template>
  <v-text-field label="Nom" v-model="nom"/>
  <v-btn text="Sauvegarder" @click="sauvegarder" color="success"/>
</template>

<script setup lang="ts">
import {onMounted, ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter();

const props = defineProps({idConcessionnaire: {type: Number, default: null}})

const nom = ref("")

onMounted(async () => {
  if (props.idConcessionnaire) {
    await chargerConcessionnaire()
  }
})

async function chargerConcessionnaire() {
  const res = await fetch(`/api/concessionnaires/${props.idConcessionnaire}`)
  const data = await res.json()
  nom.value = data.nom
}

async function sauvegarder() {
  let apiUrl = "/api/concessionnaires"
  let apiMethod = "POST"
  if (props.idConcessionnaire) {
    apiUrl = `/api/concessionnaires/${props.idConcessionnaire}`
    apiMethod = "PUT"
  }

  const res = await fetch(apiUrl, {
    method: apiMethod,
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({nom: nom.value})
  })
  if (!res.ok) {
    alert("Erreur lors de la sauvegarde")
  }
  await router.replace("/")
}

</script>
