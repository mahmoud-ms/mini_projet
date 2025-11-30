pipeline {
    agent any

    environment {
        MAVEN_HOME = '/usr/share/maven'        // chemin de Maven
        SONARQUBE = 'SonarQube'               // nom de l’installation SonarQube dans Jenkins
        TOMCAT_URL = 'http://localhost:8080/manager/text'
        TOMCAT_USER = credentials('tomcat-user')  // Jenkins credentials ID
        TOMCAT_PASS = credentials('tomcat-pass')
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/username/mon-projet.git'
            }
        }

        stage('Build') {
            steps {
                sh "${MAVEN_HOME}/bin/mvn clean install package"
            }
        }

        stage('Unit Tests') {
            steps {
                sh "${MAVEN_HOME}/bin/mvn test"
                junit '**/target/surefire-reports/*.xml'
            }
        }

        stage('SonarQube Scan') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh "${MAVEN_HOME}/bin/mvn sonar:sonar"
                }
            }
        }

        stage('Deploy to Tomcat') {
            steps {
                sh """
                    curl -u ${TOMCAT_USER}:${TOMCAT_PASS} \
                    -T target/mon-projet.war \
                    ${TOMCAT_URL}/deploy?path=/mon-projet&update=true
                """
            }
        }
    }

    post {
        success {
            echo "Pipeline terminée avec succès !"
        }
        failure {
            echo "La pipeline a échoué."
        }
    }
}
