#! groovy

def workspace = "/var/jenkins_home/workspace/SendEnv"

pipeline {
  agent {
    node {
      label " "
    }
  }
  stages{
    stage("Get Python env") {
      steps {
        script {
          sh "chmod ugo+rwx $workspace/scripts/*"
          sh "/var/jenkins_home/workspace/SendEnv/scripts/envtest.py"
          sh "FPname = $(envtest.py) && echo $FPname"
        }
      }
    }
  }
}
