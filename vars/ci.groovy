def call() {
    try {
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
                stage('Unit Tests') {
                    steps {
                        echo 'Unit testing'
                    }
                }
                stage('Quality Control') {
                    steps {
                        script {
                            common.unittests()
                        }
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
        }catch(Exception e) {
        common.email ("Failed")
    }
}