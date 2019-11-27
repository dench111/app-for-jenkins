pipeline {
  agent {
    node {
      label " "
    }
  }
    stages {
        stage('build') {
            steps {
                sh 'python --version'
            }
        }
    }
}
