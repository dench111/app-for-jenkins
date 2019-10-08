pipeline {
  agent {
    node {
      label " "
    }
  }
  stages {
    stage('Update system") {
      steps {
        script {
          def workspace = "/var/lib/jenkins/workspace/Ansible_Test_WithGit"
          echo "Обновление системы"
          sh "ansible-playbook" + workspace + "SystemUpdate.yml"
        }
      }
    }
  }
}
