<search-results>

    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                <p if={!canDisplayResults}>Aucun résultat</p>
                <ul if={canDisplayResults}>
                    <li each="{item in opts.results.items}"><a href="{item.url}">{item.fullName}</a></li>
                </ul>
            </div>
        </div>
    </div>


  <script type="es6">
  // this.canDisplayResults = this.opts.results && this.opts.results.length > 0
  this.on('update', () => {
      console.log('update search results');
      this.canDisplayResults = this.opts.results && Object.getOwnPropertyNames(this.opts.results).length > 0
      console.log(this.canDisplayResults);
      console.log("===========>>>>>> results: ", this.opts.results);
  })
  </script>

</search-results>
