# bookmymovie
A spring boot application that provides different APIs for booking seat(s) for a movie. For simplicity, I have assumed all theatres are single screen theatres. Through this project, you will know the basics of how to use Swagger to document API.

**Note:** As the 3rd and 4th API have same resource path but different query parameters, the 4th API won't show in the swagger UI but you can hit it using Postman/SoapUI.

**Demo**: https://book-movie.herokuapp.com/swagger-ui.html

## Getting Started

Just clone and import the project in the IDE of your choice. Run BookingApplication.java as Java application, it will fire up tomcat instance and embedded MongoDB underhood. Hit http://localhost:8080/swagger-ui.html

### API Details

**1. API to accept details of a movie screen**

  - **Request method**: POST

  - **Request URI**: /screens

  - **Request body (example)**:

{ "name":"inox", "seatInfo": { "A": { "numberOfSeats": 10, "aisleSeats": [0, 5, 6, 9] }, "B": { "numberOfSeats": 15, "aisleSeats": [0, 5 ,6, 9] }, "D": { "numberOfSeats": 20, "aisleSeats": [0, 5 ,6, 9] } } }

The payload for this API describes the layout of a theatre screen ‑ it contains the rows (A, B, D) and the number of seats each row contains as well as the aisle seats marked out.



**2. API to reserve tickets for given seats in a given screen**

- **Request method** POST

- **Request URI**: /screens/{screen‑name}/reserve

- **Request body (example)**:

{ "seats": { "B": [1, 2], "C": [ 6, 7] } }

The payload describes the number of seats in the rows to be booked - Seat number 1 & 2 of row B and seat number 6 & 7 of row C needs to be booked.



**3. API to get the available seats for a given screen**

- **Request method**: GET

- **Request URI**: /screens/{screen‑name}/seats?status=unreserved

- **Response body (example)**:

  { "seats": { "A": [0, 1 ,2 ,6, 7, 8 , 9], "B": [0, 8 , 9], "D": [] } }



**4. API to get information of available tickets at a given position**


- **Request method**: GET

- **Request URI**: /screens/{screen‑name}/seats?numSeats={x}&choice={seat‑row‑and‑number}

- **Response body (example)**:

{ "availableSeats": { "A": [3, 4] } }

Example:

**GET /screens/{screen‑name}/seats?numSeats=2&choice=A4**

Can return

{ "availableSeats": { "A": [3, 4] } } OR { "availableSeats": { "A": [4, 5] } }

**GET /screens/{name}/seats?numSeats=4&choice=A4**

Returns an error since we don't have 4 contigous seats for the user (A5 is an isle seat)

## Built With

[Spring] (https://spring.io/) - the web framework used

[Swagger] (https://swagger.io/) - to document APIs and an UI to visualize and interact with the API’s resources 

[Maven] (https://maven.apache.org/) - Dependency Management

[MongoDB] (https://spring.io/projects/spring-data-mongodb) - Embedded DB used for storage

