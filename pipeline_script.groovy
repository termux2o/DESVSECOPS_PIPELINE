pipeline {
    agent any
    environment {
        SCANNER_HOME = tool "Sonar-scanner"
        IMAGE_NAME = "aakashsharma114/devsecops_mlapp_pipeline:latest"
        REPO_URL = "https://github.com/termux2o/python_ml_application.git"
        BRANCH = "master"
        DJANGO_SETTINGS_MODULE = "myapp.settings"
    }
    stages {
        stage("Git Checkout") 
        {
            steps {
                git branch: "${BRANCH}", changelog: false, poll: false, url: "${REPO_URL}"
            }
        }

        stage('OWASP SCAN') {
            steps {
                dependencyCheck additionalArguments: '', odcInstallation: 'DP-check'
                dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
            }
        }

        stage('SonarQube Analysis') {
    steps {
        withSonarQubeEnv('Sonarqube') {
            bat """
                "%SCANNER_HOME%\\bin\\sonar-scanner" ^
                -Dsonar.projectName=mlapp ^
                -Dsonar.java.binaries=. ^
                -Dsonar.projectKey=mlapp ^
            """
        }
    }
}


        stage("Install Dependencies") {
            steps {
                script {
                    // Install dependencies and pytest-cov for coverage reporting
                    bat 'pip install -r requirements.txt'
                    bat 'pip install pytest-cov'
                }
            }
        }

        stage("Docker Build & Push") {
            steps {
                script {
                    withDockerRegistry(credentialsId: 'dockerhub_id') {
                        bat "docker build -t devsecops_mlapp_pipeline ."
                        bat "docker tag devsecops_mlapp_pipeline ${IMAGE_NAME}"
                        bat "docker push ${IMAGE_NAME}"
                    }
                }
            }
        }

        stage("Run Bandit Security Scan") {
            steps {
                script {
                    def banditStatus = bat(
                        script: 'bandit -r myapp -f html -o bandit-report.html',
                        returnStatus: true
                    )
                    if (banditStatus != 0) {
                        echo "Bandit found issues in the code. Check the Bandit Security Report for details."
                    }
                }
            }
            post {
                always {
                    publishHTML([
                        allowMissing: true, 
                        reportDir: '.', 
                        reportFiles: 'bandit-report.html', 
                        reportName: 'Bandit Security Report', 
                        keepAll: true, 
                        alwaysLinkToLastBuild: true
                    ])
                }
            }
        }

        stage("Run Unit Tests with Pytest") {
            steps {
                script {
                    bat 'set DJANGO_SETTINGS_MODULE=myapp.settings'
                    bat 'pytest test_example.py --junitxml=test-results.xml --cov=myapp --cov-report=xml'
                }
            }
            post {
                always {
                    junit 'test-results.xml'
                }
            }
        }
    }
    post {
        success {
            echo "Pipeline completed successfully!"
        }
        failure {
            echo "Pipeline failed!"
        }
    }
}
