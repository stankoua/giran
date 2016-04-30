import fetch from 'isomorphic-fetch'

export default function getAndDispatch(url, actionCreator) {
  return (dispatch) => {
    fetch(url, {
        method: 'get',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        }
      })
      .then((response) => { return response.json() })
      .then((data) => { dispatch(actionCreator(data)) })
  }
}
