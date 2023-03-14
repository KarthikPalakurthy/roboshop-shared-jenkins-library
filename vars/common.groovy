def compile() {
    if (app_lang== "nodejs"){
        sh 'npm install'
        sh env
    }
    if (app_lang== "maven"){
        sh 'mvn package'
    }
}

def unittests() {
    // Developer is missing unit test cases

    if (app_lang== "nodejs") {
        sh 'npm test '
    }
    if (app_lang== "maven"){
        sh 'mvn test'
    }
    if (app_lang== "python"){
        sh 'python3 -m unittest'
        }
}



def email(email_note) {
    mail bcc: '', body: 'TESTING', cc: '', from: 'palakurthyk95@gmail.com', replyTo: '', subject: 'TEST', to: 'palakurthyk95@gmail.com'
}
