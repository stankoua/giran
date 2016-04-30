<repo-committers>

    <ul class="list-group col-md-12">
        <li class="list-group-item col-md-3" each="{committer in opts.committers}">
            <img src="{committer.avatarUrl}" class="col-md-6" width="100px" height="100px" />
            <div class="col-md-6">
                <p><strong>{committer.login}</strong></p>
                <p><span><span class="badge">{committer.contributions}</span>contributions</span></p>
            </div>
        </li>
    </ul>

    <script type="es6">
        // this.on("update", () => {
        //     console.log(this.opts.committers);
        // })
    </script>

</repo-committers>
