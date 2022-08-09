# Topic 4 (#40): Infrastructure As Code
## Application Info
Read all the information in the [ORIGINAL_README.md](ORIGINAL_README.md)

## How To Start
###DOCKERFILE PART
There is a [Dockerfile](Dockerfile) in the root of the module:\
`docker build -t petclinic .`

Thr Dockerfile is built of 3 steps:
**Step 1. Build:**

**Step 2. Package:**\
_Packager_ uses the most lightweight version of Linux image (alpine:3.10.3) to install the required JDK\
and link only necessary java modules via _jlink_ into the custom lightweight JRE.

**Step 3. Config and Deploy:**\
The final configurations of the image: creating a system user to use instead of the root one, 
custom Xmx and Xms parameters

Here you need to create a Docker volume and map it to a directory on PC where the config file is located.\ 
Then you can reference it in the command:\
`docker container run`\
`-p 8081:8080`\
`-v %cd%/target/classes:/target/classes` <--- this %cd% is for Window, $(pwd) is for Linux\
`petclinic:latest`

###KUBERNETES PART
Create a namespace:\
`kubectl create namespace petclinic/namespace`

Set it as current:\
`kubectl config set-context --current --namespace=msvc-ns`

At this point all objects will be created in this namespace. Otherwise, the default namespace is 'default'.\
There is a kubernetes folder containing the following files:\
1. **Deployment** [petclinic-deployment-kubernetes.yaml](/kubernetes/petclinic-deployment-kubernetes.yaml) **file:**\

2. **Service** [petclinic-service-kubernetes.yaml](/kubernetes/petclinic-service-kubernetes.yaml) **file:**\
The ClusterIP is only available inside a cluster/private cloud network,\ 
that's why I've selected NodePort. NodePort service type has dynamic nodePort value,\
so I've defined the fixed nodePort value

3. **Configmap** [petclinic-configmap-kubernetes.yaml](/kubernetes/petclinic-configmap-kubernetes.yaml) **file:**\
Don't really know what I'd like to customize, but I've tried to switch between different DBs\ 
and it's worked out.

The applying order:\
`kubectl apply -f petclinic-configmap-kubernetes.yaml`\
`kubectl apply -f petclinic-service-kubernetes.yaml`\
`kubectl apply -f petclinic-deployment-kubernetes.yaml`

Look at my pod, my pod is amazing C:\
![mysmallpod](/kubernetes/mysmallpod.png)

Pods amount may be scaled with this command:\
`kubectl scale deployment <deployment-name> --replicas=n`

Or by updated `spec.replicas` parameter in deployment and reapplied .yaml config

###HELM PART
After Helm is installed:\
`helm create petclinic-chart`

Check the created chart:\
`helm show chart petclinic-chart`

There is a folder [petclinic-chart](petclinic-chart) in the root folder. It could contain a "charts" folder \
but it's for embedded charts, the application doesn't have ones.\
To install the "petclinic-chart":\
`helm install petclinic-chart <system-path-to-this-folder>`\

###INGRESS PART
Add the NGINX repo to the Helm:\
`helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx`

Update the dataset to create the app in the Kubernetes cluster:\
`helm repo update`

Install the controller:\
`helm install ingress-nginx ingress-nginx/ingress-nginx`

Prevent "service "ingress-nginx-controller-admission" not found" exception:\
`kubectl delete -A ValidatingWebhookConfiguration ingress-nginx-admission`

Configure ingress-kubernetes.yaml in [kubernetes](kubernetes) folder and [ingress](petclinic-chart/templates/ingress.yaml) file\
Reinstall with Helm

_ADDRESS IT VIA_ **HTTP** _INSTEAD OF HTTPS_