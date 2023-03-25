def call() {
    try {
        node ('test-work') {
            stage('Cleanup') {
                cleanWs()
            }


        }
    } catch (Exception e) {
        common.email("Failed")
    }
}
