# DevSecOps Pipeline Project

## Overview
This project implements a **DevSecOps pipeline** to integrate security into the software development lifecycle. The pipeline automates code quality checks, security scans, unit testing, and deployment, ensuring secure and production-ready applications.  

## Key Features
- **Automated Security Checks**: Integrates tools like OWASP Dependency Check, Bandit, and SonarQube for identifying vulnerabilities and improving code quality.  
- **Continuous Integration/Continuous Deployment (CI/CD)**: Uses Jenkins to automate the pipeline stages.  
- **Containerization**: Leverages Docker for secure and consistent application deployment.  
- **Unit Testing**: Ensures application correctness with Pytest and coverage reporting.  

---

## Objectives
- Integrate security checks at every stage of the CI/CD pipeline.  
- Automate code quality analysis and vulnerability scanning.  
- Reduce manual effort while improving security and efficiency.  
- Streamline deployment with containerization using Docker.  

---

## Tools and Technologies
- **Jenkins**: CI/CD pipeline automation.  
- **SonarQube**: Static code analysis for code quality.  
- **OWASP Dependency Check**: Detects vulnerabilities in dependencies.  
- **Bandit**: Python security analysis.  
- **Docker**: Containerization and image management.  
- **Pytest**: Unit testing framework with coverage reporting.  
- **GitHub**: Version control for source code.  

---

## Pipeline Stages
1. **Git Checkout**: Pulls source code from GitHub repository.  
2. **OWASP Dependency Check**: Scans for vulnerabilities in dependencies.  
3. **SonarQube Analysis**: Performs static code analysis to identify issues.  
4. **Install Dependencies**: Installs Python packages required for the application.  
5. **Docker Build & Push**: Builds and pushes Docker images to Docker Hub.  
6. **Bandit Security Scan**: Scans Python code for security issues.  
7. **Unit Testing**: Runs tests using Pytest with coverage reporting.  

---

## Installation and Setup
1. Clone the repository:  
   ```bash
   git clone https://github.com/termux2o/python_ml_application.git
