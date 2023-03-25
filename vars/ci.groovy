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
                        script{
                            //common.unittests()
                            echo 'Hi'
                        }
                    }
                }
                stage('Quality Control') {
                    environment {
                        SONAR_USER= '$(aws ssm get-parameters --region us-east-1 --names sonarqube.user --query Parameters[0].Value | sed \'s/"/g\')'
                        SONAR_PASS= '$(aws ssm get-parameters --region us-east-1 --names sonarqube.pass --query Parameters[0].Value | sed \'s/"/g\')'

                    }
                    steps {
                        script{
                            sh "sonar-scanner -Dsonar.host.url=http://172.31.3.246:9000 -Dsonar.login=${SONAR_USER} -Dsonar.password=${SONAR_PASS} -Dsonar.projectKey=cart"
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