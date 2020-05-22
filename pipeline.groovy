  
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
          def workspace = "/var/jenkins_home/workspace/Pipeline_Job"
          sh "chmod ugo+rwx $workspace/*"
          sh "who"
          sh "ansible-playbook -i " + "inventory" + " " + "$workspace/AnsiblePlaybook.yml"
        }
      }
    }
  }
}
