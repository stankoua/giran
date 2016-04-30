import getAndDispatch from '../common/fetcher'

export const SEARCH_DATA = 'SEARCH_DATA'

export function searchDataResults(data) {
  return {
    type: SEARCH_DATA,
    data: data
  }
}

export function searchFor(searchTerm, page) {
  return getAndDispatch(dispatch, '/search?q=' + searchTerm + '&page=' + page, searchDataResults)
}
