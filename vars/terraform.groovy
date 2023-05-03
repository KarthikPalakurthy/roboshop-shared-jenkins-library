def call() {
    pipeline{

        options{
            ansiColor('xterm')
        }

        agent{
            node{
                label 'test-work'
            }
        }

        parameters {
            string(name: 'INFRA_ENV', defaultValue: '', description: 'Enter the environment (dev/prod)')
            string(name: 'ACTION', choice: ['Apply','Destroy'], description: 'Action')


        }

        stages{

            stage ('Terraform init') {
                steps{
                    sh "terraform init -backend-config=env-${INFRA_ENV}/state.tfvars"
                }
            }

            stage ('Terraform Apply') {
                steps{
                    sh "terraform ${ACTION} -auto-approve -var-file=env-${INFRA_ENV}/main.tfvars"
                }
            }
        }

        post {
            always {
                cleanWs()
            }
        }
    }
}