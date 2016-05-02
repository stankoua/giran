'use strict'
// Setup Polyfills, one day these will go away
import './polyfills/object.assign'
import Promise from 'es6-promise'
Promise.polyfill()

import riot from 'riot'
import thunk  from 'redux-thunk'
import createLogger from 'redux-logger'
import {createStore, applyMiddleware} from 'redux'
import reducers from './reducers'
import './tags/router.tag.js'

riot.tag('raw', '<span></span>', function(opts) {
    this.root.innerHTML = opts.content;
});

//    riot.compile(function() {
//        riot.route(function() {
//            var str = "";
//            for (var i = 0; i < arguments.length; i++) {
//                str += '/' + arguments[i];
//            }
//            console.log('current route: ', str);
//        });
//
//        router = riot.mount('router')[0];
//        riot.route.start(true);
//    });
const logger = createLogger()
const finalCreateStore = applyMiddleware(thunk, logger) (createStore)
const store = finalCreateStore(reducers)

const router = riot.mount('router', {store: store})[0]

riot.route.start(true);
