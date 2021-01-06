// Определяем новый компонент, названный button-counter
Vue.component('button-counter', {
    data: function () {
        return {
            count: 0
        }
    },
    template: '<button v-on:click="count++">Счётчик кликов — {{ count }}</button>'
})

new Vue({ el: '#components-demo' })

var app = new Vue({
    el: '#main',
    data: {
        search: '',
        results: [],
        minSearchLen: 4,
        timer: null,
        searchStatus: 'Добре.. Давай щось пошукаємо...'
    },
    methods: {
        live: function () {

            if (this.timer) {
                clearTimeout(this.timer);
                this.timer = null;
            }

            if (!this.search.length) {
                this.searchStatus = 'Добре.. Давай щось пошукаємо...';
                this.results = [];
                return;
            }

            this.timer = setTimeout(() => {

                if (this.search.length < this.minSearchLen) {
                    this.searchStatus = 'Пошуковий запит має бути більше ' + this.minSearchLen + ' символів';
                    return;
                }

                this.searchStatus = 'Шукаю...';
                fetch('/v1/live?s=' + this.search)
                    .then(res => res.json())
                    .then(res => {
                        this.results = res.data;
                        this.searchStatus = this.results.length === 0 ? 'Вибачте, але нічого не знайдено!' : 'Ось що я знайшов:';
                    });


            }, 200);
        }
    }
})