// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import VueMeta from 'vue-meta'
import App from './App'
import router from './router'

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    components: {App},
    template: '<App/>',
});

Vue.filter('truncate', function (text, length, clamp) {
    clamp = clamp || '...';
    let node = document.createElement('div');
    node.innerHTML = text;
    let content = node.textContent;
    return content.length > length ? content.slice(0, length) + clamp : content;
});

Vue.filter('formatPrice', function (text, length, clamp) {
    let node = document.createElement('div');
    node.innerHTML = (parseInt(text) / 100).toFixed(2) + ' грн.';
    return node.textContent;
});

Vue.use(VueMeta, {
    refreshOnceOnNavigation: true
})
