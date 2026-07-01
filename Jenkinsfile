pipeline {
    agent any 
    tools {
        maven "my-maven"
    }
    stages {
        stage ("code") {
            steps {
                git 'https://github.com/iam-patannoor/food-delivery-app.git'
            }
        }
        stage ("build") {
            steps {
                sh "mvn clean install"
            }
        }
        stage ("CQA") {
            steps {
                withSonarQubeEnv("my-sonar") {
                 sh "mvn clean verify sonar:sonar -Dsonar.projectKey=food-delivery-app"
               }
            }
        }
        stage ("Quality gates") {
            steps {
                waitForQualityGate abortPipeline: true, credentialsId: 'my-sonar'
            }
        }
        stage ("artifact") {
            steps {
                echo "this stage is artifact"
            }
        }
        stage ("Docker build") {
            steps {
                sh "docker build -t food-image:v1 ." 
            }
        }
        stage ("Docker registry") {
            steps {
                withDockerRegistry(credentialsId: 'Docker', url: 'https://index.docker.io/v1/') 
                 {
                     sh "docker tag food-image:v1 patannor/food-app:appv1"
                     sh "docker push patannor/food-app:appv1"
                  }
            }   
        }
        stage ("Container creation") {
            steps {
                sh "docker run -itd --name cont1 -p 1111:8080 patannoor/food-app:appv1"
                echo "application successfully deployed"
            }
        }
    }
}

//pluginsused:-Docker pipeline
//sonarqube-2
//give permission sudo chown root:docker /var/run/docker.sock
