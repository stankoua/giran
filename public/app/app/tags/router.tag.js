'use strict';
import riot   from 'riot'
import Promise from 'es6-promise'
import fetch from 'isomorphic-fetch'

import './index-view.tag'
import './repo-view.tag'

let current_tag = undefined

riot.tag('router', '', function(opts) {

  riot.route.base('/');
  const r = riot.route.create();
  r('/',              home       )
  r('/repos/*/*',     repo       )
  r(                  home       )

  function home() {
    current_tag = riot.mount('#app', 'index-view', {store: opts.store, searchResults: {}})[0]
  }

  function repo(owner, name) {
    current_tag = riot.mount('#app', 'repo-view', {store: opts.store, ...owner, ...name, repo: {}})[0]
  }

});

export default function getCurrentTag() {
  return current_tag
}
