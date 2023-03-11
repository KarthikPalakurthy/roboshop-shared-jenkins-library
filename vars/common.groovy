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

    if (app_lang== "nodejs"){
        try {
            sh 'npm test '
        } catch(Exception e) {
            common.email (" Unit Tests Failed ")
        }

    }
    if (app_lang== "maven"){
        try {
            sh 'mvn test'
        } catch(Exception e)
            common.email (" Unit Tests Failed ")
        sh 'mvn test'
    }
    if (app_lang== "python"){
        try {
            sh 'python3 -m unittest'
        } catch(Exception e) {
            common.email (" Unit Test failed ")
        }
    }

}

def email(email_note) {
    sh "echo {$email_note}"
}
