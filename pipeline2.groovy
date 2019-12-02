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
          sh "chmod ugo+rwx $workspace"
          sh "/var/lib/jenkins/workspace/Ansible_Test_WithGit/monitoring_script.py"
          sh "unset ELKStatus"
          sh "rm -rf /var/lib/jenkins/workspace/Ansible_Test_WithGit/*"
        }
      }
    }
  }
}
