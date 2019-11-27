pipeline {
  agent {
    node {
      label " "
    }
  }
  stages {
    stage("Start monitoring script") {
      steps {
        script {
          def workspace = "/var/lib/jenkins/workspace/Ansible_Test_WithGit"
          echo "Проверка состояния сервера ELK"
          sh "pip install termcolor"
          sh "chmod ugo+rwx /var/lib/jenkins/workspace/Ansible_Test_WithGit/monitoring_script.py"
          sh "/var/lib/jenkins/workspace/Ansible_Test_WithGit/monitoring_script.py"
        }
      }
    }
  }
}
