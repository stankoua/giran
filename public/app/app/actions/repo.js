import fetch from 'isomorphic-fetch'

export const REPO_DATA = "REPO_DATA"

export function repoDataResults(data) {
  return {
    type: REPO_DATA,
    ...data
  }
}

export function loadRepo(owner, name) {
  return function(dispatch) {
    const url = `/repo/$owner/$name`
    fetch(url, {
        method: 'get',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        }
      }).then((response) => {
        return response.json()
      }).then((data) => {
        dispatch(repoDataResults(data))
      })
  }
}
