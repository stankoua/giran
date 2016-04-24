require('./search-results.tag')
<index-view>

    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                <p>Use search box below to find a repo</p>
                <form novalidate="true" onSubmit={searchRepo}>
                    <div class="col-md-3">
                        <div class="input-group">
                            <span class="input-group-btn">
                                <input id="searchTerm" type="text" class="form-control" placeholder="Search for ..." />
                                <button type="submit" class="btn btn-default">Go!</button>
                            </span>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <search-results results={searchResults} store={store} />

    <script type="es6">
        // import searchFor from '../actions/search'
        var searchFor = require('../actions/search').searchFor
        const {dispatch} = this.opts.store

        this.searchResults = opts.store.getState().search
        this.searchRepo = () => {
          const searchTerm = document.getElementById('searchTerm').value
          dispatch(searchFor(searchTerm))
        }
        this.on('update', () => {
            console.log(this.searchResults);
        })
        opts.store.subscribe(() => {
          this.update({searchResults: opts.store.getState().search})
        })
    </script>

</index-view>
