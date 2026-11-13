
pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo 'Recuperation du projet...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Compilation du projet...'
                sh 'chmod +x mvnw'
                sh './mvnw clean compile'
            }
        }

        stage('Tests') {
            steps {
                echo 'Execution des tests...'
                sh './mvnw test'
            }
        }

        stage('JaCoCo') {
            steps {
                echo 'Generation du rapport de couverture...'
                sh './mvnw verify'
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
    }
}
