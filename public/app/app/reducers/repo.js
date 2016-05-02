import {REPO_DATA, REPO_COMMITTER_DATA, REPO_COMMITS} from '../actions/repo'

export default function(state = {}, action) {
  switch (action.type) {
    case REPO_DATA:
      return Object.assign({}, state, action.data)
      break;
    case REPO_COMMITTER_DATA:
      return Object.assign({}, state, {committers: action.data})
    case REPO_COMMITS:
      return Object.assign({}, state, {commits: action.data})
    default:
      return state
  }
}
