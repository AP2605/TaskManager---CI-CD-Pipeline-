pipeline {

agent any

tools {
    jdk 'JDK21'
    maven 'MAVEN3.9'
}

environment {
    DOCKERHUB_USERNAME = '26ayush007'
    IMAGE_NAME = 'taskmanager'
    IMAGE_TAG = 'v1'
}

stages {

    stage('Checkout Source Code') {
        steps {
            echo "Checking out source code from GitHub..."

            git branch: 'main',
                url: 'https://github.com/AP2605/TaskManager---CI-CD-Pipeline-.git'
        }
    }

    stage('Verify Tools') {
        steps {
            echo "Verifying Java..."
            bat 'java -version'

            echo "Verifying Maven..."
            bat 'mvn -version'

            echo "Verifying Docker..."
            bat 'docker --version'

            echo "Verifying Kubernetes..."
            bat 'kubectl version --client'
        }
    }

    stage('Clean Project') {
        steps {
            echo "Cleaning project..."
            bat 'mvn clean'
        }
    }

    stage('Compile Project') {
        steps {
            echo "Compiling project..."
            bat 'mvn compile'
        }
    }

    stage('Run Unit Tests') {
        steps {
            echo "Running unit tests..."
            bat 'mvn test'
        }
    }

    stage('Package Application') {
        steps {
            echo "Packaging Spring Boot application..."
            bat 'mvn package'
        }
    }

    stage('Build Docker Image') {
        steps {
            echo "Building Docker image..."
            bat 'docker build -t %IMAGE_NAME%:%IMAGE_TAG% .'
        }
    }

    stage('Tag Docker Image') {
        steps {
            echo "Tagging Docker image..."
            bat 'docker tag %IMAGE_NAME%:%IMAGE_TAG% %DOCKERHUB_USERNAME%/%IMAGE_NAME%:%IMAGE_TAG%'
        }
    }

    stage('Push Docker Image to Docker Hub') {
        steps {
            echo "Pushing Docker image to Docker Hub..."

            withCredentials([
                usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )
            ]) {

                bat 'docker login -u %DOCKER_USER% -p %DOCKER_PASS%'

                bat 'docker push %DOCKERHUB_USERNAME%/%IMAGE_NAME%:%IMAGE_TAG%'

                bat 'docker logout'
            }
        }
    }

    stage('List Docker Images') {
        steps {
            echo "Available Docker Images"
            bat 'docker images'
        }
    }

    stage('Deploy to Kubernetes') {
        steps {
            echo "Deploying application to Kubernetes..."

            bat 'kubectl apply -f k8s/deployment.yaml'

            bat 'kubectl apply -f k8s/service.yaml'

            bat 'kubectl rollout restart deployment/taskmanager'
        }
    }

    stage('Verify Deployment') {
        steps {
            echo "Checking Deployment..."

            bat 'kubectl rollout status deployment/taskmanager'

            bat 'kubectl get deployments'

            bat 'kubectl get pods'

            bat 'kubectl get svc'
        }
    }
}

post {

    always {
        echo "Pipeline Finished."
    }

    success {
        echo "======================================="
        echo "BUILD SUCCESSFUL"
        echo "Docker Image Pushed Successfully"
        echo "Application Deployed Successfully"
        echo "======================================="
    }

    failure {
        echo "======================================="
        echo "BUILD FAILED"
        echo "Check Jenkins Console Output"
        echo "======================================="
    }
}

}
