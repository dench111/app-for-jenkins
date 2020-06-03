#! groovy

String FPname
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
          sh "echo $FPname"
          sh "name = $FPname"
          sh "echo $name"
          sh "echo ${env.FPname}"
        }
      }
    }
  }
}