AuthService Application
A Spring Boot-based authentication service to manage user sign-up, sign-in, JWT token validation, token revocation, and token refresh functionality. This guide explains how to set up and test the application.

🚀 Prerequisites
Java: Ensure you have JDK 11 or higher installed.
MySQL: Install and set up MySQL server or workbench.
Java IDE: Use any Java-specific IDE like IntelliJ IDEA, Eclipse, or Spring Tool Suite.
Postman: Use Postman to test the API or run the provided curl commands.


🛠️ Setup Instructions
Step 1: Clone the Repository
bash
Copy code
git clone <repository-url>
cd authservice


Step 2: Configure the Database
Open application.properties in the src/main/resources directory.
Update the following values:
properties
Copy code
spring.datasource.url=jdbc:mysql://localhost:3306/auth_service?useSSL=false
spring.datasource.username=your-username
spring.datasource.password=your-password
Open MySQL Workbench and create the required database:
sql
Copy code
CREATE DATABASE auth_service;
<!---you can also use any other database name-->


Step 3: Run the Application
Open the project in your preferred Java IDE (e.g., IntelliJ IDEA).
Locate AuthserviceApplication.java in the src/main/java directory.
Click the Run button to start the application.
Once the application is running, it will be available at http://localhost:8080.


🧪 API Testing Instructions
You can test the APIs using Postman or Curl. Below is the list of API endpoints and their usage:

1. Sign-Up API
Request:

bash
Copy code
curl -X POST http://localhost:8080/api/auth/signup \
-H "Content-Type: application/json" \
-d '{"email":"user@example.com", "password":"password123"}'
Response:

json
Copy code
{
  "id": 1,
  "email": "user@example.com",
  "password": "$2a$10$hashedPasswordHere",
  "tokenRevoked": false
}


2. Sign-In API
Request:

bash
Copy code
curl -X POST http://localhost:8080/api/auth/signin \
-H "Content-Type: application/json" \
-d '{"email":"user@example.com", "password":"password123"}'
Response:

json
Copy code
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIiwiaWF0IjoxNjkzODUwMjE2LCJleHAiOjE2OTM4NTM4MTZ9.zF0gFxE2kVP3kTWWLD5b8q5nJ6qN7QsZbPaHHEa1wq4"
}
Save the token for testing other endpoints.


3. Token Validation API
Request:

bash
Copy code
curl -X GET http://localhost:8080/api/auth/validate \
-H "Authorization: Bearer <TOKEN>"
Responses:

Valid Token (200 OK): "Valid Token"
Invalid Token (401 Unauthorized): "Invalid or Expired Token"


4. Revoke Token API
Request:

bash
Copy code
curl -X POST http://localhost:8080/api/auth/revoke \
-H "Content-Type: application/json" \
-d '{"userId": 1}'
Response:

json
Copy code
"Token revoked"


5. Token Refresh API
Request:

bash
Copy code
curl -X POST http://localhost:8080/api/auth/refresh \
-H "Content-Type: application/json" \
-d '{"token": "<TOKEN>"}'
Responses:

Success:
json
Copy code
{
  "newToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIiwiaWF0IjoxNjkzODUwMjE2LCJleHAiOjE2OTM4NTM4MTZ9.zF0gFxE2kVP3kTWWLD5b8q5nJ6qN7QsZbPaHHEa1wq4"
}
Failure (401 Unauthorized): "Invalid or Expired Token"


Sample Testing Workflow
Sign-Up a New User:
bash
Copy code
curl -X POST http://localhost:8080/api/auth/signup \
-H "Content-Type: application/json" \
-d '{"email":"user@example.com", "password":"password123"}'


Sign-In to Obtain Token:
bash
Copy code
curl -X POST http://localhost:8080/api/auth/signin \
-H "Content-Type: application/json" \
-d '{"email":"user@example.com", "password":"password123"}'


Validate the Token:
bash
Copy code
curl -X GET http://localhost:8080/api/auth/validate \
-H "Authorization: Bearer <TOKEN>"


Revoke the Token:
bash
Copy code
curl -X POST http://localhost:8080/api/auth/revoke \
-H "Content-Type: application/json" \
-d '{"userId":1}'


Refresh the Token:
bash
Copy code
curl -X POST http://localhost:8080/api/auth/refresh \
-H "Content-Type: application/json" \
-d '{"token":"<TOKEN>"}'


📋 Notes for Recruiters
Replace <BASE_URL> with the API's URL (e.g., http://localhost:8080).
Replace <TOKEN> with the JWT token received from the Sign-In API.
Ensure the database is set up and the application is running before testing the APIs.
The sample token expiration duration is set to 1 hour (modifiable for testing).


I have also shared postman collection where I have provided all the apis along with the data I used for testing. Please do check.
Please let me know in case of any doubt. 
I will be happy to help.
