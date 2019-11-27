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
          echo -e "\033[32m Проверка состояния сервера ELK\n"
          sh "chmod ugo+rwx /var/lib/jenkins/workspace/Ansible_Test_WithGit/monitoring_script.py"
          sh "/var/lib/jenkins/workspace/Ansible_Test_WithGit/monitoring_script.py"
          sh "rm -rf /var/lib/jenkins/workspace/Ansible_Test_WithGit/*"
        }
      }
    }
  }
}
