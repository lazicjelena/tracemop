async function main() {
  const {DefaultArtifactClient} = require('@actions/artifact')
  const artifact = new DefaultArtifactClient()
  const {id, size} = await artifact.uploadArtifact('traces', ['./traces.tar.gz'], '.')
}

main();
