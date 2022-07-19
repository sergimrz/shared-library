def call(Map params) {
  application = params.application
  runScan     = params.runScan

  def GITHUB_ORG="https://github.com/sergimrz/"
  def IMAGE="${application}:${BUILD_NUMBER}"
  pipeline {
    agent any
    stages {
      stage('Clone') {
        steps {
          script {
            git '${GITHUB_ORG}${application}.git'
          }
        }
      }
      stage('Build dev image') {
        steps {
          script {
            sh "docker build -t ${IMAGE}-dev"
            if(runScan){ 
              CI.scan("${IMAGE}-dev")
              // docker push ${registry}{image}
            } 
          }
        }
      }
      stage('Deploy dev image') {
        steps {
          script {
            sh "echo deploy minikube dev"
          }
        }
      }        
      stage('Testing') {
        steps {
          script {
            sh "echo 'Perform end to end test in dev'"
          }
        }
      }        
      stage('Promote image') {
        steps {
          script {
            sh "docker tag ${IMAGE}-dev ${IMAGE}"
            // docker push ${registry}{image}
          }
        }
      }
      stage('Deploy live') {
        steps {
          script {
            sh "echo deploy minikube live"
          }
        }
      }        
    }
  }
}
