pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK17'
    }

    options {
        timestamps()
        disableConcurrentBuilds()
    }

    parameters {
        choice(name: 'BROWSER', choices: ['chromium', 'firefox', 'webkit'], description: 'Browser to run tests against')
        booleanParam(name: 'HEADLESS', defaultValue: true, description: 'Run browsers in headless mode')
        booleanParam(name: 'RUN_LOGIN_SUITE', defaultValue: true, description: 'Run the @Login suite (required check)')
        booleanParam(name: 'RUN_AMAZON_SUITE', defaultValue: true, description: 'Run the @Amazon suite (non-blocking)')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Install Playwright Browsers') {
            steps {
                runMaven("exec:java@install-playwright-browsers")
            }
        }

        stage('Login Suite (required)') {
            when {
                expression { params.RUN_LOGIN_SUITE }
            }
            steps {
                runMaven("test -Dcucumber.filter.tags=@Login -Dbrowser=${params.BROWSER} -Dheadless=${params.HEADLESS}")
            }
            post {
                always {
                    junit testResults: 'target/surefire-reports/testng-results.xml', allowEmptyResults: true
                    archiveReports('login')
                }
            }
        }

        stage('Amazon Suite (non-blocking)') {
            when {
                expression { params.RUN_AMAZON_SUITE }
            }
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'UNSTABLE') {
                    runMaven("test -Dcucumber.filter.tags=@Amazon -Dbrowser=${params.BROWSER} -Dheadless=${params.HEADLESS}")
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
