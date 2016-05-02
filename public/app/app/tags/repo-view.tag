require('./repo-committers.tag')
require('./repo-commits.tag')
require('./repo-contribution.tag')
<repo-view>

    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                <h1>Repo <i>{opts.owner}/{opts.name}</i></h1>

                <p>This template has a responsive menu toggling system. The menu will appear collapsed on smaller screens, and will appear non-collapsed on larger screens. When toggled using the button below, the menu will appear/disappear. On small screens, the page content will be pushed off canvas.</p>
                <p>Use search box below to find a repo</p>

                <div>
                    <span class="label label-default">{repo.forks} forks</span>
                    <span class="label label-success">{repo.watchers} watchers</span>
                    <span class="label label-danger">{repo.openIssues} open issues</span>
                    <select id="nbCommits" onchange={onChangeNbCommits}>
                        <option>30</option>
                        <option>60</option>
                        <option>90</option>
                        <option>100</option>
                    </select>
                </div>

                <div id="repo-tabs">
                    <select id="sections" class="pull-right" onchange={onChangeSection}>
                        <option value="100">Committers</option>
                        <option value="010">Contributions</option>
                        <option value="001">Commits</option>
                    </select>

                    <div id="section-content">
                        <repo-committers if={sections._1} committers={repo.committers}/>

                        <repo-contribution if={sections._2} commits={repo.commits}/>

                        <repo-commits if={sections._3} commits={repo.commits}/>
                    </div>
                </div>

            </div>
        </div>
    </div>

    <script type="es6">
        // import repoDataResults from '../actions/repo'
        let fetchCommitters = require('../actions/repo').fetchCommitters
        let loadRepo = require('../actions/repo').loadRepo
        let fetchCommits = require('../actions/repo').fetchCommits
        const {dispatch} = this.opts.store
        this.repo = opts.store.getState().repo
        const owner = this.opts.owner
        const name = this.opts.name
        this.sections = {
            _1 : true,
            _2 : false,
            _3 : false
        }
        this.onChangeSection = () => {
            let sections = document.getElementById("sections").value
            sections = sections.split('').map((v, i) => {
                    let res = {}
                    let index  = '_' + i
                    res[index] = (v == 1) ? true : false
                    return res
                }).reduce((acc, cur) => {
                    return Object.assign({}, acc, cur)
                }, {})
            // console.log(sections);
            this.update({
                sections: sections
            })
        }
        this.onChangeNbCommits = () => {
            let nb = document.getElementById("nbCommits").value
            console.log(nb);
            dispatch(fetchCommits(owner, name, nb))
        }
        this.on('mount', () => {
            let nb = document.getElementById("nbCommits").value
            dispatch(loadRepo(owner, name))
            dispatch(fetchCommitters(owner, name))
            dispatch(fetchCommits(owner, name, 30))
        })
        this.on('update', () => {
            console.log(this.sections);
        })
        let unsubscribe = this.opts.store.subscribe(() => {
            this.update({
                repo: this.opts.store.getState().repo
            })
        })
    </script>
</repo-view>
