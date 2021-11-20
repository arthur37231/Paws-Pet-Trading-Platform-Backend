# Pet Trading Backend (Server) Program

## API Documentation

See: http://localhost:8080/swagger-ui/index.html

## Exceptions

See [Error Code Documentation](Error%20Code.md)

## WebSocket subscription

Establish websocket connection with the server through `ws://localhost:8080/websocket/{username}`

The _username_ is the current login user's username, so the initialisation step should be performed after the user sign
in successfully, and the connection should be terminated when the user sign out.

## Database Configuration

Database connection configuration needs to be initialised before you launching the application. The corresponding
configuration is put in `src/main/resources/application.yml`

Both MySQL and Redis connection parameters are needed to be set for the application running properly.