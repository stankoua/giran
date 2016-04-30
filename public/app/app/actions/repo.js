import getAndDispatch from '../common/fetcher'

export const REPO_DATA = "REPO_DATA"

export const REPO_COMMITTER_DATA = "REPO_COMMITTER_DATA"

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

export function loadRepo(owner, name) {
  return getAndDispatch(`/repos/${owner}/${name}`, repoDataResults)
}

export function fetchCommitters(owner, name) {
  return getAndDispatch(`/repos/${owner}/${name}/committers`, repoCommittersDataResults)
}
