const getAndDispatch = require('../common/fetcher').getAndDispatch;


export const SEARCH_DATA = 'SEARCH_DATA'

export function searchDataResults(data) {
  return {
    type: SEARCH_DATA,
    data: data
  }
}

export function searchFor(searchTerm, page) {
  return getAndDispatch(`/search?q=${searchTerm}&page=${page}`, searchDataResults)
}
