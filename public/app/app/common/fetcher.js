import fetch from 'isomorphic-fetch'

export function getAndDispatch(url, actionCreator) {
  return (dispatch) => {
    get(url).then((data) => { dispatch(actionCreator(data)) })
  }
}

export function get(url) {
  return fetch(url, {
      method: 'get',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    })
    .then((response) => { return response.json() })
}
