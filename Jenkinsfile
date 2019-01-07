pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                checkout scm
                chmod +x gradlew
                sh './gradlew build'
            }
        }
    }
}
