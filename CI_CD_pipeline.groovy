#! groovy

String FPname
def workspace = "/var/jenkins_home/workspace/Pipeline_Job"

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
          sh "ansible-playbook -i " + "inventory" + " " + "$workspace/Playbooks/git_clone_repo.yml"
        }
      }
    }
    stage("Run build app with maven") {
      steps {
        script {
          sh "mvn -f /var/jenkins_home/workspace/sources/ clean install"
          sh "ls -la /var/jenkins_home/workspace/sources/target/"
        }
      }
    }
    stage("Naming distr") {
      steps {
        script {
          sh "chmod ugo+rwx $workspace/scripts/*"
          sh "/var/jenkins_home/workspace/Pipeline_Job/scripts/PomParser.py"
          FPname='/var/jenkins_home/workspace/Pipeline_Job/scripts/PomParser.py'
          sh "echo $FPname"
        }
      }
    }
    stage("Upload disrtibutiv to nexus") {
      steps {
       withCredentials([usernamePassword(credentialsId: '6deb43b4-4f40-425b-813a-6a21dc4e7c05',
                        usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
        script {
          
          sh "echo $FPname"
          sh "curl -v -u $USERNAME:$PASSWORD --upload-file /var/jenkins_home/workspace/sources/target/$FPname.jar http://192.168.0.84:8081/nexus/content/repositories/Testrep/$FPname.jar"
        }
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


