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
                    <select class="" id="nbCommits" onchange={onChangeNbCommits}>
                        <option>30</option>
                        <option>60</option>
                        <option>90</option>
                        <option>100</option>
                    </select>
                </div>

                <!-- <repo-committers committers={repo.committers}/> -->

                <!--<repo-commits commits={repo.commits}/>-->

                <repo-contribution commits={repo.commits}/>

                <!--<div id="repo-tabs">
                    <ul class="nav nav-pills">
                        <li role="presentation" class="active"><a href="#">Home</a></li>
                        <li role="presentation"><a href="#">Profile</a></li>
                        <li role="presentation"><a href="#">Messages</a></li>
                    </ul>
                    <div id="myTabContent" class="tab-content">
                        <div class="tab-pane fade active in" id="home">
                            <p>Raw denim you probably haven't heard of them jean shorts Austin. Nesciunt tofu stumptown aliqua, retro synth master cleanse. Mustache cliche tempor, williamsburg carles vegan helvetica. Reprehenderit butcher retro keffiyeh dreamcatcher synth. Cosby sweater eu banh mi, qui irure terry richardson ex squid. Aliquip placeat salvia cillum iphone. Seitan aliquip quis cardigan american apparel, butcher voluptate nisi qui.</p>
                        </div>
                        <div class="tab-pane fade" id="profile">
                            <p>Food truck fixie locavore, accusamus mcsweeney's marfa nulla single-origin coffee squid. Exercitation +1 labore velit, blog sartorial PBR leggings next level wes anderson artisan four loko farm-to-table craft beer twee. Qui photo booth letterpress, commodo enim craft beer mlkshk aliquip jean shorts ullamco ad vinyl cillum PBR. Homo nostrud organic, assumenda labore aesthetic magna delectus mollit.</p>
                        </div>
                        <div class="tab-pane fade" id="messages">
                            <p>Etsy mixtape wayfarers, ethical wes anderson tofu before they sold out mcsweeney's organic lomo retro fanny pack lo-fi farm-to-table readymade. Messenger bag gentrify pitchfork tattooed craft beer, iphone skateboard locavore carles etsy salvia banksy hoodie helvetica. DIY synth PBR banksy irony. Leggings gentrify squid 8-bit cred pitchfork.</p>
                        </div>
                    </div>
                </div>-->

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
        let unsubscribe = this.opts.store.subscribe(() => {
            this.update({
                repo: this.opts.store.getState().repo
            })
        })
    </script>
</repo-view>
