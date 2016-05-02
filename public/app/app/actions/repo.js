import {getAndDispatch, get} from '../common/fetcher'
import _ from 'lodash'
import {Promise } from 'es6-promise'


export const REPO_DATA = "REPO_DATA"

export const REPO_COMMITTER_DATA = "REPO_COMMITTER_DATA"

export const REPO_COMMITS = 'REPO_COMMITS'

export function repoDataResults(data) {
  return {
    type: REPO_DATA,
    data: data
  }
}

export function repoCommittersDataResults(data) {
  return {
    type: REPO_COMMITTER_DATA,
    data: data
  }
}

export function repoCommitsDataResults(data) {
  return {
    type: REPO_COMMITS,
    data: data
  }
}

export function loadRepo(owner, name) {
  return getAndDispatch(`/repos/${owner}/${name}`, repoDataResults)
}

export function fetchCommitters(owner, name) {
  return getAndDispatch(`/repos/${owner}/${name}/committers`, repoCommittersDataResults)
}

export function fetchCommits(owner, name, number) {
  return (dispatch) => {
    const pagination = 30
    const pageNb = Math.ceil(number / pagination) + 1
    const pages = _.range(1, pageNb)
    // console.log(pages)
    let futures = _(pages).map((page) => get(`/repos/${owner}/${name}/commits?page=${page}`)).value()
    // console.log(futures);
    // console.log(Promise);
    Promise.all(futures)
      .then((lists) => _.flatten(lists))
      .then((commits) => dispatch(repoCommitsDataResults(commits)))
  }
}
