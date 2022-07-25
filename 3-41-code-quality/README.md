# Topic 3 (#41): Code Quality

## Application Info
A poor quality app to trigger SonarQube

## How To Start
1. Install Sonar via Docker Image:\
`docker pull image`


2. Prepare Docker Volumes:\
`docker volume create --name sonarqube_date`\
`docker volume create --name sonarqube_logs`\
`docker volume create --name sonarqube_extensions`\


3. Launch SonarQube container:
`docker run -d --name sonarqube`\
`-p 9000:9000`\
`-v sonarqube_data:/opt/sonarqube/data`\
`-v sonarqube_extensions:/opt/sonarqube/extensions`\
`-v sonarqube_logs:/opt/sonarqube/logs`\
`sonarqube`


4. Generate SonarQube token for the local app


5. Run the application along with _maven_:\
`mvn clean verify sonar:sonar `\
`-Dsonar.projectKey=private-jgmp-2022` \
`-Dsonar.host.url=http://localhost:9000` \
`-Dsonar.login=sqp_164a53c031363e056d43ed27e2155015345aeff6`