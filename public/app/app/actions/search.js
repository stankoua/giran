import fetch from 'isomorphic-fetch'
import Promise from 'es6-promise'

export const SEARCH_DATA = 'SEARCH_DATA'

export function searchDataResults(data) {
  return {
    type: SEARCH_DATA,
    data: data
  }
}

export function searchFor(searchTerm, page) {
  return function(dispatch) {
    const url = '/search?q=' + searchTerm + '&page=' + page
    fetch(url, {
        method: 'get',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        }
      })
      .then((response) => {
        return response.json()
      })
      .then((data) => {
        console.log(data);
        dispatch(searchDataResults(data))
      })
  }
}
