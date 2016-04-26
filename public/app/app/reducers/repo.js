import REPO_DATA from '../actions/repo'

export default function(state = {}, action) {
  switch (action.type) {
    case REPO_DATA:
      return action.data
      break;
    default:
      return state
  }
}
