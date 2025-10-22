

# Command to Run Gradle Project
```
dotenv -e '<LOCATION OF ENV FILE>' -- ./gradlew clean bootRun
```

# Steps to Update docker hub

## Make the executable Build the Spring Boot Project
```
dotenv -e '<LOCATION OF ENV FILE>' -- ./gradlew clean bootJar
```

## Make the Docker Build
```
docker build -t shubhamsapkal1/vehiforge-configuration_service .
```

## (OPTIONAL) Run the project to check all is working
```
docker run -p 8082:8082 --env-file "<ENV_FILE_LOCATION>" shubhamsapkal1/vehiforge-configuration_service:latest
```

## Login into docker hub and push the image
```
docker login
```

```
docker push shubhamsapkal1/vehiforge-userservice
```


# Kubernetes Deployment

## Install Kubernetes with kubectl and kind

## Create secrets.yaml file to store env secrets

```
apiVersion: v1
kind: Secret
metadata:
  name: secrets
type: Opaque
data:
    ENV_KEY_NAME: ENV_KEY_VALUE     # Base43 encoded
stringData:
    ENV_KEY_NAME: ENV_KEY_VALUE 
```

### To convert to base64
```
-> echo -n 'env_value' | base64
```

### Apply the changes
```
-> kubctl apply -f secrets.yaml
```


## Create Deployment File
```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: vehiforge-user-service
  labels:
    app: vehiforge-user-service
spec:
  replicas: 2
  selector:
    matchLabels:
      app: vehiforge-user-service
  template:
    metadata:
      labels:
        app: vehiforge-user-service
    spec:
      containers:
        - name: vehiforge-user-service
          image: shubhamsapkal1/vehiforge-user_service
          ports:
            - containerPort: 8081
          env:
            - name: USER_SERVICE_URL
              value: http://localhost:8081
            - name: DB_HOST
              valueFrom:
                secretKeyRef:
                  name: secrets
                  key: DB_HOST
            - name: DB_USER
              valueFrom:
                secretKeyRef:
                  name: secrets
                  key: DB_USER
            - name: DB_PASSWORD
              valueFrom:
                secretKeyRef:
                  name: secrets
                  key: DB_PASSWORD
            - name: DB_NAME
              valueFrom:
                secretKeyRef:
                  name: secrets
                  key: DB_NAME
            - name: TOKEN_SECRET
              valueFrom:
                secretKeyRef:
                  name: secrets
                  key: TOKEN_SECRET

```

### Apply the changes
```
kubectl apply -f user_service-deployment.yaml
```

## Create Kubernetes Service File
Pods are ephemeral, so to reach them you need a Service.

```
apiVersion: v1
kind: Service
metadata:
  name: vehiforge-user-service
spec:
  selector:
    app: vehiforge-user-service
  ports:
    - protocol: TCP
      port: 8081
      targetPort: 8081
  type: ClusterIP
```

### Apply the changes
```
kubectl apply -f user_service_svc.yaml
```

## Check Pods and Services

```
kubectl get pods
```

```
kubectl get svc
```


## (OPTIONAL) Port Fortwarding
Just in case pod is not accessible ( This is the case when kubernetes are running with docker), forward the port to get accessible

```
kubectl port-forward svc/vehiforge-configuration-service 8083:8083
```


# Encrypting secrets.yaml file using ansible (  )

## Step 1: Open Ubuntu WSL

## Step 2: Update the system
```
sudo apt update
```

## Step 3: Install ansible
```
sudo apt install ansible
```

## Step 4: Encrypt the file
```
ansible-vault encrypt secrets.yaml
```