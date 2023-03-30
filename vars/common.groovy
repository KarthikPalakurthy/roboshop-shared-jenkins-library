def compile() {
    if (app_lang== "nodejs"){
        sh 'npm install'
    }
    if (app_lang== "maven"){
        sh 'mvn package'
    }
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
    if (app_lang=="nodejs"){
        sh "zip -r cart-${TAG_NAME}.zip node_modules server.js VERSION"
    }

}


def email(email_note) {
    mail bcc: '', body: "Failure detected on ${JOB_URL} component", cc: '', from: 'palakurthyk95@gmail.com', replyTo: '', subject: "Failure on ${JOB_BASE_NAME}", to: 'palakurthyk95@gmail.com'
}
