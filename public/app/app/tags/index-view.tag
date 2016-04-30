require('./search-results.tag')
<index-view>

    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                <h1>Welcome to Github Repo ANalyser</h1>
                <p>
                    This template has a responsive menu toggling system. The menu will appear collapsed on smaller screens, and will appear non-collapsed on larger screens. When toggled using the button below, the menu will appear/disappear. On small screens, the page content will be pushed off canvas.
                </p>
                <p>Use search box below to find a repo</p>
                <form novalidate="true" onSubmit={searchRepo}>
                    <div class="col-md-3">
                        <div class="input-group">
                            <span class="input-group-btn">
                                <input id="searchTerm" name="searchTerm" type="text" class="form-control" placeholder="Search for ..." />
                                <button type="submit" class="btn btn-default">Go!</button>
                            </span>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <search-results results={searchResults} store={this.opts.store} q={searchTerm.value} />

    <script type="es6">
        // import searchFor from '../actions/search'
        let searchFor = require('../actions/search').searchFor
        const {dispatch} = this.opts.store

        this.searchResults = opts.store.getState().search
        this.searchRepo = () => {
          const searchTerm = document.getElementById('searchTerm').value
          dispatch(searchFor(searchTerm, 1))
        }
        let unsubscribe = opts.store.subscribe(() => {
          this.update({searchResults: opts.store.getState().search})
        })
        this.on('update', () => {
            console.log(this.searchResults);
        })
        this.on('unmount', () => unsubscribe())
    </script>

</index-view>
