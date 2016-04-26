<search-results>

    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                <p if={!canDisplayResults}>Aucun résultat</p>
                <ul class="list-group" if={canDisplayResults}>
                    <li class="list-group-item" each="{item in opts.results.items}">
                        <a href="/repos/{item.fullName}">{item.fullName}</a>
                        <!-- <span class="label label-info">{item.language}</span> -->
                        <span class="label label-default">{item.forks} forks</span>
                        <span class="label label-success">{item.watchers} watchers</span>
                        <span class="label label-danger">{item.openIssues} open issues</span>
                        <!-- <span class="badge"></span> -->
                    </li>
                </ul>
                <nav if={canDisplayResults}>
                    <ul class="pager">
                        <li><a href="#" if={canPrevious} onclick="{previous}">Previous</a></li>
                        <li><a href="#" if={canNext} onclick="{next}">Next</a></li>
                    </ul>
                </nav>
            </div>
        </div>
    </div>


  <script type="es6">
  // import searchFor from '../actions/search'
  let searchFor = require('../actions/search').searchFor
  const {dispatch} = this.opts.store
  let launchSearch = (page) => {
    dispatch(searchFor(this.opts.q, page))
  }
  this.next = () => launchSearch(this.opts.results.page + 1)
  this.previous = () => launchSearch(this.opts.results.page - 1)
  this.on('update', () => {
      console.log('update search results');
      this.canDisplayResults = this.opts.results && Object.getOwnPropertyNames(this.opts.results).length > 0
      this.canPrevious = this.canDisplayResults && (this.opts.results.page > 1)
      this.canNext = this.canDisplayResults && ((this.opts.results.items.length * this.opts.results.page) <= this.opts.results.total)
      console.log(this.canDisplayResults);
      console.log("===========>>>>>> results: ", this.opts.results);
  })
  this.on('unmount', () => {
    console.log("=================>> search results unmount");
  })
  </script>

</search-results>
