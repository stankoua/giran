'use strict';
import riot   from 'riot'
import Promise from 'es6-promise'
import fetch from 'isomorphic-fetch'

import './index-view.tag'

let current_tag = undefined

riot.tag('router', '', function(opts) {

    const {dispatch} = opts.store
    let unsubscribe = () => undefined

    riot.route.base('/');
    const r = riot.route.create();
    r('/',              home       )

    riot.route(function() {
      unsubscribe()
      unsubscribe = () => undefined
    });

    function home() {
      current_tag = riot.mount('#app', 'index-view', {store: opts.store, searchResults: {}})[0]
    }


});

export default function getCurrentTag() {
  return current_tag
}
