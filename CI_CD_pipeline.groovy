
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
    stage("Run build app with maven") {
      steps {
        script {
          sh "mvn -f /var/jenkins_home/workspace/sources/ clean package"
          sh "ls -la /var/jenkins_home/workspace/sources/target/
        }
      }
    }
    stage("Upload disrtibutiv to nexus") {
      steps {
        script {
          sh "curl -v -u admin:admin123 --upload-file /var/jenkins_home/workspace/sources/target/*.jar http://192.168.0.84:8081/nexus/content/repositories/Testrep/rest-service-0.0.1-SNAPSHOT.jar"
        }
      }
    }
    stage("Clean source dir") {
      steps {
        script {
          sh "rm -rf /var/jenkins_home/workspace/sources/*"
        }
      }
    }
  }
}
