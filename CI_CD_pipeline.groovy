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
           // The below will clone your repo and will be checked out to master branch by default.
           git credentialsId: 'fd7c264a-d14f-4d9f-94b6-ce610eb4ccbc', url: 'https://github.com/dench111/rest-service.git'
           // Do a ls -lart to view all the files are cloned. It will be clonned. This is just for you to be sure about it.
           sh "ls -lart ./*" 
           // List all branches in your repo. 
           sh "git branch -a"
           // Checkout to a specific branch in your repo.
           sh "git checkout master"
          }
       }
    }
    stage("Run build app with maven") {
      steps {
        script {
          sh "mvn -f /var/jenkins_home/workspace/Pipeline_Job/ clean install -Dmaven.test.skip=true"
          sh "ls -la /var/jenkins_home/workspace/Pipeline_Job/target/"
        }
      }
    }
    stage("Rename and Upload disrtibutiv to nexus") {
      steps {
       withCredentials([usernamePassword(credentialsId: '6deb43b4-4f40-425b-813a-6a21dc4e7c05',
                        usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
        script {
          sh "chmod ugo+rwx $workspace/scripts/*"
          sh "/var/jenkins_home/workspace/Pipeline_Job/scripts/PomParser.py"
          //sh "curl -v -u $USERNAME:$PASSWORD --upload-file /var/jenkins_home/workspace/Pipeline_Job/target/$FPname$ext http://192.168.0.84:8081/nexus/content/repositories/Testrep/$FPname$ext"
        }
      }
    }
   }
   }
    post {
        always {
            echo 'Clean dir'
            //deleteDir() /* clean up our workspace */
        }
    }
}


