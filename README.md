AuthService Application
A Spring Boot-based authentication service to manage user sign-up, sign-in, JWT token validation, token revocation, and token refresh functionality. This guide explains how to set up and test the application.

## Getting Started

To run the application, simply execute the following command in the root directory of the project:

```bash
docker-compose up --build
This command will automatically build and start the necessary Docker containers, including the application and database. Once the containers are up and running, you can begin testing the application.

Testing the Application
You can test the application using Postman or curl commands. The endpoints are available at http://localhost:8087.

Make sure the MySQL container is healthy before sending requests. If you encounter any issues, feel free to reach out!


🧪 API Testing Instructions
You can test the APIs using Postman or Curl. Below is the list of API endpoints and their usage:

1. Sign-Up API
Request:

bash
Copy code
curl -X POST http://localhost:8087/api/auth/signup \
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
curl -X POST http://localhost:8087/api/auth/signin \
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
curl -X GET http://localhost:8087/api/auth/validate \
-H "Authorization: Bearer <TOKEN>"
Responses:

Valid Token (200 OK): "Valid Token"
Invalid Token (401 Unauthorized): "Invalid or Expired Token"


4. Revoke Token API
Request:

bash
Copy code
curl -X POST http://localhost:8087/api/auth/revoke \
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
curl -X POST http://localhost:8087/api/auth/refresh \
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
curl -X POST http://localhost:8087/api/auth/signup \
-H "Content-Type: application/json" \
-d '{"email":"user@example.com", "password":"password123"}'


Sign-In to Obtain Token:
bash
Copy code
curl -X POST http://localhost:8087/api/auth/signin \
-H "Content-Type: application/json" \
-d '{"email":"user@example.com", "password":"password123"}'


Validate the Token:
bash
Copy code
curl -X GET http://localhost:8087/api/auth/validate \
-H "Authorization: Bearer <TOKEN>"


Revoke the Token:
bash
Copy code
curl -X POST http://localhost:8087/api/auth/revoke \
-H "Content-Type: application/json" \
-d '{"userId":1}'


Refresh the Token:
bash
Copy code
curl -X POST http://localhost:8087/api/auth/refresh \
-H "Content-Type: application/json" \
-d '{"token":"<TOKEN>"}'


📋 Notes for Recruiters
Replace <BASE_URL> with the API's URL (e.g., http://localhost:8087).
Replace <TOKEN> with the JWT token received from the Sign-In API.
Ensure the database is set up and the application is running before testing the APIs.
The sample token expiration duration is set to 1 hour (modifiable for testing).


I have also shared postman collection where I have provided all the apis along with the data I used for testing. Please do check.
Please let me know in case of any doubt. 
I will be happy to help.
