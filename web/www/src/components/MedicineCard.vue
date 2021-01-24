<template>

  <div class="card" style="cursor: pointer;" v-on:click="view(medicine.alias)">
    <div class="card-body ">
      <div class="overflow-hidden text-center">
        <img v-if="medicine.media" v-bind:src="'/images/204x177/'+medicine.media.name" class="rounded" alt="..."
             height="160">
        <img v-else src="/empty.png" class="rounded" alt="..." height="160">
      </div>
      <h5 class="fw-light card-title fs-6 mt-3" style="height: 90px;">
        {{ medicine.name | truncate(60, '...') }}</h5>
      <p class="fw-light fst-italic" style="font-size: 14px;" v-if="medicine.registrations.length">
        р/н: <span v-for="reg in medicine.registrations">{{ reg.code }}</span>
      </p>
      <p class="fw-bold fst-italic card-text p-1" v-if="medicine.prices.length"><span
          v-for="price in medicine.prices">від {{ price.price | formatPrice }}</span></p>
      <p class="fw-light fst-italic card-text bg-light p-1" v-else>Ціна відсутня</p>
    </div>
  </div>

</template>

<script>
export default {
  name: "MedicineCard",
  props: {
    medicine: {
      type: Object,
      required: true
    }
  },
  methods:{
    view: function (alias) {
      this.$router.push({name: 'medicine', params: {alias: alias}})
    },
  }
}
</script>

<style scoped>

</style>