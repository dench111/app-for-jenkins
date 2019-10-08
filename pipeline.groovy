pipeline {
  agent {
    node {
      label " "
    }
  }
  stages {
    stage("Update system") {
      steps {
        script {
          def workspace = "/var/lib/jenkins/workspace/Ansible_Test_WithGit"
          echo pwd
          echo ls
          echo "Обновление системы"
          sh "SystemUpdate.yml"
        }
      }
    }
  }
}
