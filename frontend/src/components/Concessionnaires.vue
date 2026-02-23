<template>
  <div class="mb-3">
    <v-btn :to="'/concessionnaires/creer'" color="success" text="Créer un concessionnaire"/>
  </div>
  <v-data-iterator :items="concessionnaires">
    <template #default="{ items }">
      <template
        v-for="(item, i) in items"
        :key="i"
      >
        <v-card v-bind="item.raw.id">
          <v-card-title>{{item.raw.nom}} - {{item.raw.nombreVoitures}} voiture(s) disponible(s) </v-card-title>
          <v-card-actions>
            <v-btn icon="mdi-eye" :to="`/concessionnaires/${item.raw.id}`"/>
            <v-btn icon="mdi-pencil" :to="`/concessionnaires/${item.raw.id}/modifier`"/>
          </v-card-actions>
        </v-card>

        <br>
      </template>
    </template>
  </v-data-iterator>
</template>

<script setup>
import {onMounted, ref} from "vue";

const concessionnaires = ref([]);

onMounted( async () => {
  await getConcessionnaires()
})

async function getConcessionnaires() {
  const res = await fetch("/api/concessionnaires");
  const data = await res.json();
  console.log("/api/concessionnaires", data);
  concessionnaires.value.length = 0
  data.forEach(item => {
    concessionnaires.value.push(item);
  })
  console.log(concessionnaires.value)
}

</script>
