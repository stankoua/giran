<repo-committers>

    <h2>Committers</h2>
    <ul class="list-group col-md-12">
        <li class="list-group-item col-md-3" each="{committer in opts.committers}">
            <img src="{committer.avatarUrl}" class="col-md-6" width="100px" height="100px" />
            <div class="col-md-6">
                <p><strong>{committer.login}</strong></p>
            </div>
        </li>
    </ul>

    <script type="es6">
        this.on("update", () => { })
    </script>

</repo-committers>
