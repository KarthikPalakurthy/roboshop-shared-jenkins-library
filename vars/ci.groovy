def call() {
    try {
        pipeline {
            agent {
                label 'test-work'
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
                        script {
                            common.unittests()
                        }
                    }
                }
                stage('Quality Control') {
                    environment {
                        SONAR_USER = '$(aws ssm get-parameters --region us-east-1 --names sonarqube.user --with-decryption --query Parameters[0].Value | sed \'s/"//g\')'
                        SONAR_PASS = '$(aws ssm get-parameters --region us-east-1 --names sonarqube.pass --with-decryption --query Parameters[0].Value | sed \'s/"//g\')'

                    }
                    steps {
                        script {
                            maskPasswords(varPasswordPairs: [[password: $ { SONAR_PASS }, var: 'admin']]) {
                                sh "sonar-scanner -Dsonar.host.url=http://172.31.3.246:9000 -Dsonar.login=${SONAR_USER} -Dsonar.password=${SONAR_PASS} -Dsonar.projectKey=cart"
                            }
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
        } catch (Exception e) {
            common.email("Failed")
        }
    }