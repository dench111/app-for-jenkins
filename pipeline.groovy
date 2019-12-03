  
pipeline {
  agent {
    node {
      label " "
    }
  }
  stages {
    stage("Start Ansible Playbook from Git") {
      steps {
        script {
          def workspace = "/var/lib/jenkins/workspace/Ansible_Git"
          sh "chmod ugo+rwx $workspace/*"
          sh "/var/lib/jenkins/workspace/Ansible_Git/AnsiblePlaybook.yml"
        }
      }
    }
  }
}
