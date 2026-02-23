<template>
  <v-text-field label="Marque" v-model="marque"/>
  <v-text-field label="Modèle" v-model="modele"/>
  <v-text-field label="Année" v-model="annee" type="number"/>
  <v-text-field label="Prix" v-model="prix" type="number" @keydown="forcerNombreComplet"/>
  <v-btn text="Sauvegarder" @click="onSauvegarder" color="success"/>
</template>

<script setup>
import {onMounted, ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter();

const props = defineProps({
  idConcessionnaire: {type: Number, default: null},
  idVoiture: {type: Number, default: null}
})

let pIdConcessionnaire = null

const marque = ref("")
const modele = ref("")
const annee = ref(null)
const prix = ref(null)

onMounted(async () => {
  if (props.idVoiture) {
    await chargerVoiture()
  } else {
    pIdConcessionnaire = props.idConcessionnaire
  }
})

async function chargerVoiture() {
  const res = await fetch(`/api/voitures/${props.idVoiture}`)
  const data = await res.json()
  marque.value = data.marque
  modele.value = data.modele
  annee.value = data.annee
  prix.value = data.prix
  pIdConcessionnaire = data.idConcessionnaire
}

async function onSauvegarder() {
  let apiUrl = `/api/concessionnaires/${pIdConcessionnaire}/voitures`
  let apiMethod = "POST"
  if (props.idVoiture) {
    apiUrl = `/api/voitures/${props.idVoiture}`
    apiMethod = "PUT"
  }
  const data = {
    marque: marque.value,
    modele: modele.value,
    annee: annee.value,
    prix: prix.value
  }
  const res = await fetch(apiUrl, {
    method: apiMethod,
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data)
  })
  if (!res.ok) {
    alert("Erreur lors de la sauvegarde")
  }
  await router.replace(`/concessionnaires/${pIdConcessionnaire}`)
}

function forcerNombreComplet(e) {
  if (e.key === '.') {
    e.preventDefault()
  }
}
</script>
