package main.groovy
  
class CI implements Serializable {
  
  // Import the jenkins steps from the current context
  def steps
  CI(steps) {this.steps = steps}

  def scan(image){
    steps.sh "curl -s https://ci-tools.anchore.io/inline_scan-latest | bash -s -- -r ${image}"
  }
}


