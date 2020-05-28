Skip to content
Search or jump to…

Pull requests
Issues
Marketplace
Explore
 
@dench111 
dench111
/
app-for-jenkins
1
00
 Code
 Issues 0
 Pull requests 0 Actions
 Projects 0
 Wiki
 Security 0
 Insights
 Settings
app-for-jenkins/CI_CD_pipeline.groovy
@dench111 dench111 Update CI_CD_pipeline.groovy
0933989 2 minutes ago
46 lines (45 sloc)  1.3 KB
  

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
          sh "ls -la /var/jenkins_home/workspace/sources/target/"
        }
      }
    }
    stage("Upload disrtibutiv to nexus") {
      withCredentials([[$class: 'UsernamePassword', credentialsId: '6deb43b4-4f40-425b-813a-6a21dc4e7c05',
                        usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
      println(env.USERNAME)
      println(env.PASSWORD)
      steps {
        script {
          sh "curl -v -u $USERNAME:$PASSWORD --upload-file /var/jenkins_home/workspace/sources/target/*.jar http://192.168.0.84:8081/nexus/content/repositories/Testrep/rest-service-0.0.1-SNAPSHOT.jar"
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
}
