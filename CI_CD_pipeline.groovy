#! groovy

def workspace = "/var/jenkins_home/workspace/Pipeline_Job"

pipeline {
  agent {
    node {
      label " "
    }
  }
  stages {
  //выкачиваем исходники приложения на слейв jenkins
    stage("Clone sources") {
      steps {
        script {
          git branch: 'feature/mvn_deploy', credentialsId: 'fd7c264a-d14f-4d9f-94b6-ce610eb4ccbc', url: 'https://github.com/dench111/rest-service.git'
          git branch: 'feature/docker', credentialsId: 'fd7c264a-d14f-4d9f-94b6-ce610eb4ccbc', url: 'https://github.com/dench111/app-for-jenkins.git'
        }
      }
    }
  //Собираем из исходников jar
    stage("Run build app with maven") {
      steps {
        script {
          sh "mvn -f /var/jenkins_home/workspace/sources/ clean deploy -Dmaven.test.skip=true -X"
          sh "ls -la /var/jenkins_home/workspace/sources/target/"
        }
      }
    }
  //Нумеруем артефакт и выгружаем его в нексус
    stage("Rename and Upload disrtibutiv to nexus") {
      steps {
       withCredentials([usernamePassword(credentialsId: '6deb43b4-4f40-425b-813a-6a21dc4e7c05',
                        usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
        script {
          sh "chmod ugo+rwx $workspace/scripts/*"
          //sh "/var/jenkins_home/workspace/Pipeline_Job/scripts/PomParser.py"
        }
      }
    }
   }
  //Очищаем директорию на сборщике
    stage("Clean source dir") {
      steps {
        script {
          sh "rm -rf /var/jenkins_home/workspace/sources/* && ls /var/jenkins_home/workspace/"
        }
      }
    }
  }
 }


