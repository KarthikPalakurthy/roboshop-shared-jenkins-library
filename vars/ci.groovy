def call(){

    pipeline {
        agent {
            label 'workstation'
        }

        stages {
            stage('Compile/build') {
                steps {
                    script {
                        common.compile()
                    }
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