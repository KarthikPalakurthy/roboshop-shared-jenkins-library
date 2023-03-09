def call(){

    pipeline {
        agent {
            label 'ansible'
        }

        stages {
            stage('Hello') {
                steps {
                    echo 'Hello World!'
                }
            }
            stage('Hello1') {
                steps {
                    echo 'Hello World!, This is a test message'
                }
            }
            stage('Hello2') {
                steps {
                    echo 'Thank you'
                }
            }
            stage('Upload code to centralised place') {
                steps {
                    echo ' Uploading code'
                }
            }
        }
        post {
            always {
                echo ' Sending E-mail '
            }

        }

    }


}