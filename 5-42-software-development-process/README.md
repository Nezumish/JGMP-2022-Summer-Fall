# Topic 5 (#42): Software Development Process
## Application Info
Trying to create a working Jenkins pipeline _lmao_.

Required Jenkins Plugins: 
- Docker plugin
- Git
- Kubernetes
- Kubernetes CLI Plugin

## How To Start
###ONLY JENKINS PART
The most simple and nice part.The [Jenkinsfile](../4-40-infrastructure-as-code/Jenkinsfile) was 
containing a one-stage pipeline of echoing "Hello, World!". 
As the first attempt, I decided to try the Jenkins in Docker installation method.

###JENKINS + DOCKER PART
I decided to use the [Dockerfile](../4-40-infrastructure-as-code/Dockerfile) from the previous topic. 
It was transformed a little with Jenkins error message.\
Both scenarios below required installation of maven tool in Jenkins (Jenkins -> Global Tool Configuration -> Maven)

#### 1. Docker-in-Docker Jenkins
Follow the official guide https://www.jenkins.io/doc/book/installing/docker/ to install DinD Jenkins

Then it's possible to use docker commands within the script pipeline.

#### 2. Docker in Kubernetes as Jenkins slave
I'd switched to this option when the integration with Kubernetes in Minikube had failed for 21312328419 time. 
I fed it with certs and cloud configuration but Jenkins still could not see the Kubernetes.

I Deployed Jenkins in Kubernetes (apply [jenkins-volume.yaml](./src/main/resources/jenkins-volume.yaml) and [jenkins-sa.yaml](./src/main/resources/jenkins-sa.yaml) via kubectl) using Helm:\
`helm install jenkins -n jenkins -f jenkins-values.yaml jenkinsci/jenkins`\
Follow the appeared instructions to start using Jenkins in Kubernetes.

Then it could not see the Docker :,^) 

So I deployed [DinD](./src/main/resources/setup/dind.yaml) to the same namespace. Check its IP via kubectl.\
Now Jenkins use this DinD as a slave, creating a Pod with given yaml template in Jenkinsfile.\
The only issue: I have to restart minikube after computer reboot, so it recomputes pods' IPs, so I have to update DOCKER_HOST_VALUE in Jenkinsfile when it happens.

**DON'T FORGET TO ADD DOCKERHUB CREDENTIALS TO JENKINS -> CREDENTIALS MANAGEMENT -> GLOBAL**

###JENKINS + DOCKER + KUBERNETES PART
### Jenkins in Minikube Kubernetes using Docker in Kubernetes and Minikube Kubernetes as slaves
The docker part is the same. Due to deploying Jenkins in the Kubernetes, it's been configured as Jenkins Cloud Node automatically.

I will use the same resources from the previous topic, but I've removed Xmx and Xms properties from image, because it's can be configured as JAVA_OPTS in Kubernetes.
Also I've merged the separated deployment, service etc files into the solid [kubernetes-manifest.yaml](../4-40-infrastructure-as-code/kubernetes/kubernetes-manifest.yaml).

Obtain and decode the token of the generated Kubernetes service account in previous steps, use it as credentials. Set 'jenkins' as the namespace of the Kubernetes cloud.

Here comes the Kubectl CLI Plugin, giving an opportunity to call kubectl from a script pipeline. But it requires kubectl installation on the fly