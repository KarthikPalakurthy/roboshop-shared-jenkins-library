def call () {
    node ('test-work') {
        stage('test') {
            echo ' HI'
        }

    }
}