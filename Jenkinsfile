
pipeline {
    agent any
    environment {
        DB_URL = 'jdbc:postgresql://host.docker.internal:5432/eventpro_db'
        DB_USERNAME = 'postgres'
        DB_PASSWORD = '0000'
    }

    stages {

        // 1. Recuperation du projet depuis GitHub
        stage('Checkout') {
            steps {
                echo 'Recuperation du projet EventPro2...'
                checkout scm
            }
        }

        // 2. Compilation du projet
        stage('Build') {
            steps {
                echo 'Compilation du projet...'
                sh 'chmod +x mvnw'
                sh './mvnw -B clean compile'
            }
        }

        // 3. Execution des tests
        stage('Tests') {
            steps {
                echo 'Execution des tests...'
                sh './mvnw -B test'
            }
        }

        // 4. Generation du rapport JaCoCo
        stage('JaCoCo') {
            steps {
                echo 'Generation du rapport JaCoCo...'
                sh './mvnw -B jacoco:report'
            }
        }
    }

    post {
        always {
            echo 'Publication des resultats...'

            junit allowEmptyResults: true,
                  testResults: '**/target/surefire-reports/*.xml'

            archiveArtifacts artifacts: '**/target/*.war',
                             allowEmptyArchive: true

            archiveArtifacts artifacts: '**/target/site/jacoco/**',
                             allowEmptyArchive: true
        }

        success {
            echo 'SUCCESS: EventPro2 CI Pipeline'
        }

        failure {
            echo 'FAILED: EventPro2 CI Pipeline'
        }
    }
}
