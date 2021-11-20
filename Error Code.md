# Backend Error Codes and Messages

### InvalidParameterException

* Code: 1001
* Message: Invalid parameters in the request
* Reason: Parameter provided in the request body is invalid. For example, user entered wrong username when login.

### MissingParameterException

* Code: 1002
* Message: Missing parameter
* Reason: A parameter is required by API, but not provided in the request body.
For example, username is not provided when sign up a user account.

### UnAuthorisedException

* Code: 1003
* Message: Invalid token or token has been expired
* Reason: User requested APIs that require Authorization, but in the request header, the token
is not provided, provided wrong, or using token that has been signed out.

### UnmatchedParameterTypeException

* Code: 1004
* Message: Parameter has wrong data type
* Reason: A parameter's data type is different from the API requires.