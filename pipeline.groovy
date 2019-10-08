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
          echo "Обновление системы"
          chmod ugo+rwx /var/lib/jenkins/workspace/Ansible_Test_WithGit/SystemUpdate.yml
          sh "/var/lib/jenkins/workspace/Ansible_Test_WithGit/SystemUpdate.yml"
        }
      }
    }
  }
}
