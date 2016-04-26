<repo-view>

    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                <h1>Repo <i>{opts.owner}/{this.name}</i></h1>
            </div>
        </div>
    </div>

    <script type="es6">
        // import repoDataResults from '../actions/repo'
        let repoDataResults = require('../actions/repo').repoDataResults
        const {dispatch} = this.opts.store
        this.on('mount', () => {
            console.log("===================>> repo-view mount");
        })
    </script>
</repo-view>
