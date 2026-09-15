pipeline {
    agent any

    options {
        timestamps()
        disableConcurrentBuilds()
    }

    environment {
        HEADLESS = 'true'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Install Playwright Browsers') {
            steps {
                runMaven('exec:java@install-playwright-browsers')
            }
        }

        stage('Login Suite (required)') {
            steps {
                runMaven('test -Dcucumber.filter.tags="@Login" -Dheadless=true')
            }
            post {
                always {
                    junit testResults: 'target/surefire-reports/testng-results.xml', allowEmptyResults: true
                    archiveReports('login')
                }
            }
        }

        stage('Amazon Suite (non-blocking)') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'UNSTABLE') {
                    runMaven('test -Dcucumber.filter.tags="@Amazon" -Dheadless=true')
                }
            }
            post {
                always {
                    archiveReports('amazon')
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'reports/**', allowEmptyArchive: true
        }
    }
}

def runMaven(String args) {
    if (isUnix()) {
        sh "mvn -B ${args}"
    } else {
        bat "mvn -B ${args}"
    }
}

def archiveReports(String suiteName) {
    if (isUnix()) {
        sh "mkdir -p reports/${suiteName} && cp -r target/extent-reports target/cucumber-reports target/surefire-reports reports/${suiteName}/ || true"
    } else {
        bat "if not exist reports\\${suiteName} mkdir reports\\${suiteName} & xcopy /E /I /Y target\\extent-reports reports\\${suiteName}\\extent-reports & xcopy /E /I /Y target\\cucumber-reports reports\\${suiteName}\\cucumber-reports & xcopy /E /I /Y target\\surefire-reports reports\\${suiteName}\\surefire-reports"
    }
}
