import {
  SEARCH_DATA
} from '../actions/search'

export default function(state = {}, action) {
  switch (action.type) {
    case SEARCH_DATA:
      console.log(action);
      return action.data
      break;
    default:
      return state
  }
}
