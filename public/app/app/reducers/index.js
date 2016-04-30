import { combineReducers } from 'redux'
import search from './search'
import repo from './repo'

export default combineReducers({
  search,
  repo
})
