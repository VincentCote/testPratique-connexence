<template>
  <h1>{{ concessionnaire.nom }}</h1>
  <v-btn text="Créer une voiture" color="success" :to="`/concessionnaires/${props.idConcessionnaire}/voitures/creer`"/>
  <v-data-iterator :items="voitures" class="mt-3">
    <template v-slot:default="{ items }">
      <template
        v-for="(item, i) in items"
        :key="i"
      >
        <v-card v-bind="item.raw">
          <v-card-title>{{ item.raw.marque }} {{ item.raw.modele }} {{ item.raw.annee }} {{ formatPrix(item.raw.prix) }}</v-card-title>
          <v-card-actions>
            <v-btn icon="mdi-pencil" :to="`/voitures/${item.raw.id}/modifier`"/>
            <v-btn icon="mdi-delete-forever" @click="onSuppressionVoiture(item.raw.id)"/>
          </v-card-actions>
        </v-card>

        <br>
      </template>
    </template>
  </v-data-iterator>
</template>

<script setup>

import {useRoute, useRouter} from "vue-router";
import {defineProps, onMounted, ref} from "vue";

const router = useRouter();
const route = useRoute();

const props = defineProps({
  idConcessionnaire: {type: Number, required: true}
});

const concessionnaire = ref({})
const voitures = ref([])

onMounted(async () => {
  await Promise.all([
    chargerConcessionnaire(),
    chargerVoitures()
  ])
})

const formatPrix = (value) => {
  if (value == null) return '';
  
  return new Intl.NumberFormat('en-CA', {
    style: 'currency',
    currency: 'CAD',
  }).format(value);
};

async function chargerConcessionnaire() {
  const res = await fetch(`/api/concessionnaires/${route.params.idConcessionnaire}`)
  if (!res.ok) return router.replace("/")

  concessionnaire.value = await res.json();
}

async function chargerVoitures() {
  const res = await fetch(`/api/concessionnaires/${route.params.idConcessionnaire}/voitures`)
  if (!res.ok) return router.replace("/")

  voitures.value = await res.json()
}

async function onSuppressionVoiture(voitureId) {
  let apiUrl = `/api/voitures/${voitureId}`
  let apiMethod = "DELETE"

  const res = await fetch(apiUrl, {
    method: apiMethod,
    headers: {
      "Content-Type": "application/json",
    }
  })

  if (!res.ok) {
    alert("Erreur lors de la suppression")
  }
  else {
    voitures.value = voitures.value.filter(voiture => voiture.id !== voitureId);
  }
}
</script>
