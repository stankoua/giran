import fetch from 'isomorphic-fetch'
import Promise from 'es6-promise'

export const SEARCH_DATA = 'SEARCH_DATA'

export function searchDataResults(data) {
  return {
    type: SEARCH_DATA,
    data: data
  }
}

export function searchFor(searchTerm) {
  return function(dispatch) {
    const url = '/search?q=' + searchTerm
    fetch(url, {
        method: 'get',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        }
      })
      .then((response) => {
        // response.json().then((r) => console.log(r))
        return response.json()
      })
      .then((data) => {
        console.log(data);
        dispatch(searchDataResults(data))
      })
      // .catch((error) => dispatch(searchDataActionCreator(response.data)))
  }
}
