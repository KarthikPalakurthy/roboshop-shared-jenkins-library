def call() {
    pipeline{
        agent{
            node{
                label 'test-work'
            }
        }

        parameters {
            string(name: 'INFRA_ENV', defaultValue: '', description: 'Enter the environment (dev/prod)')

        }

        stages{

            stage (Terraform Init){
                steps{
                    sh "terraform init -backend-config=env-${INFRA_ENV}/state.tfvars"
                }
            }
        }
    }
}