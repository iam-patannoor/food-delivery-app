pipeline {
    agent any 
    tools {
        maven "mymaven"
    }
    stages {
        stage ("Code") {
            steps {
                git branch: 'main', url: 'https://github.com/iam-patannoor/food-delivery-app.git'
            }
        }
        stage ("build") {
            steps {
                sh "mvn clean "
            }
        }
        stage ("unit test") {
            steps {
                withSonarQubeEnv("my-sonar") {
                    sh "mvn clean verify sonar:sonar -Dsonar.projectKey=food-delivery-app"
                    }
            }
        }
        stage("Quality gates") {
            steps {
                waitForQualityGate abortPipeline: true, credentialsId: 'sonar'
            }
        }
        stage ("Docker tag") {
            steps {
                echo "dcoker build"
            }
        }
        stage ("Docker push") {
            steps {
                echo "docker tag"
                echo "docker push"
            }
        }
    }
}


