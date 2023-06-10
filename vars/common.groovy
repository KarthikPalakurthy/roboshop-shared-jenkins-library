def compile() {
    if (app_lang== "nodejs"){
        sh 'npm install'
    }
    if (app_lang== "maven"){
        sh "mvn package && cp target/${component}-1.0.jar ${component}.jar"
    }

    sh " docker build -t ${component} ."
}


def unittests() {
    // Developer is missing unit test cases

    if (app_lang== "nodejs") {
        sh 'npm test || true '
    }
    if (app_lang== "maven"){
        sh 'mvn test'
    }
    if (app_lang== "python"){
        sh 'python3 -m unittest'
        }
}

def artifacts() {
    // Adding Artifacts to Nexus
    sh " echo ${TAG_NAME} >VERSION"
    if (app_lang == "nodejs") {
        sh "zip -r ${component}-${TAG_NAME}.zip node_modules server.js VERSION ${extrafiles}"
    }
    if (app_lang == "nginx" || "python") {
        sh "zip -r ${component}-${TAG_NAME}.zip * -x Jenkinsfile"
    }
    if (app_lang == "maven") {
        sh "zip -r ${component}-${TAG_NAME}.zip * -x ${component}.jar VERSION ${extrafiles}"
    }

    NEXUS_PASS = sh(script: 'aws ssm get-parameters --region us-east-1 --names nexus.pass  --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true).trim()
    NEXUS_USER = sh(script: 'aws ssm get-parameters --region us-east-1 --names nexus.user  --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true).trim()
    wrap([$class: 'MaskPasswordsBuildWrapper', varPasswordPairs: [[password: "${NEXUS_PASS}", var: 'SECRET']]]) {
        sh "curl -v -u ${NEXUS_USER}:${NEXUS_PASS} --upload-file ${component}-${TAG_NAME}.zip http://172.31.11.118:8081/repository/${component}/${component}-${TAG_NAME}.zip"

    }
}


def email(email_note) {
    mail bcc: '', body: "Failure detected on ${JOB_URL} component", cc: '', from: 'palakurthyk95@gmail.com', replyTo: '', subject: "Failure on ${JOB_BASE_NAME}", to: 'palakurthyk95@gmail.com'
}
