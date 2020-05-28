pipeline {
  agent {
    node {
      label " "
    }
  }
  stages {
    stage("Download application sources from git") {
      steps {
        script {
          def workspace = "/var/jenkins_home/workspace/Pipeline_Job"
          sh "chmod ugo+rwx $workspace/*"
          sh "ansible-playbook -i " + "inventory" + " " + "$workspace/Playbooks/git_clone_repo.yml -vvv"
        }
      }
    }
  }
}
