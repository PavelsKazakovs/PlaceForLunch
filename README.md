## Notes
Users cannot change their roles through profile view because `ProfileRestController` works through `UserTO`, which is configured to prevent that. 
Admin controller, on the other hand, works with entities (`User`), so admins can change roles. Usernames cannot be changed through REST.

## Example commands
### Profile creating and editing
###### Get profile
```bash
$ curl -s -X GET --user estrella:estrellaPass http://placeforlunch.herokuapp.com/rest/v1/profile |json_pp
```
```json
{
   "id" : 3,
   "name" : "Estrella Gosnell",
   "username" : "estrella"
}
```

###### Change password - too short
```bash
$ curl -s -X PUT -d '{"name": "Esfdsf", "username": "estrella", "password": "es"}' --user estrella:estrellaPass -H "Content-Type: application/json" placeforlunch.herokuapp.com/rest/v1/profile |json_pp
```
```json
{
   "url" : "http://placeforlunch.herokuapp.com/rest/v1/profile",
   "cause" : "ValidationException",
   "details" : [
      "password must be between 5 and 64 characters"
   ]
}
```

###### Register new user
```bash
$ curl -sv -X POST -d '{"name": "John Smith", "username": "mrSmith", "password": "smithPass1"}' -H "Content-Type: application/json" placeforlunch.herokuapp.com/rest/v1/new-user |json_pp
```
link to profile is given in response header:
```
< Location: http://placeforlunch.herokuapp.com/rest/v1/profile
```
Profile data in body:
```json
{
   "username" : "mrSmith",
   "name" : "John Smith"
}
```

user names are case-insensitive:
```bash
$ curl -s -X GET --user mrsmith:smithPass1 placeforlunch.herokuapp.com/rest/v1/profile |json_pp
```
```json
{
   "username" : "mrSmith",
   "name" : "John Smith",
   "id" : 11
}
```

Delete profile:
```bash
$ curl -s -X DELETE --user mrSmith:smithPass1 placeforlunch.herokuapp.com/rest/v1/profile
```
###### Validation errors are shown as list
```bash
$ curl -s -X POST -d '{"name": "John Smith", "username": "mrSmith?@@", "password": "abc"}' -H "Content-Type: application/json" placeforlunch.herokuapp.com/rest/v1/new-user |json_pp
```
```json
{
   "cause" : "ValidationException",
   "details" : [
      "username can have only letters, numbers and ._-",
      "password must be between 5 and 64 characters"
   ],
   "url" : "http://placeforlunch.herokuapp.com/rest/v1/new-user"
}
```

### User administration
```
rest/v1/admin/users
```

### Poll
```
rest/v1/poll/restaurants
```

```
rest/v1/poll/restaurants?showDishes=true
```

```
rest/v1/poll/restaurants/1/dishes
```
### Voting
```bash
$ curl -s -X GET --user estrella:estrellaPass http://placeforlunch.herokuapp.com/rest/v1/profile/vote
```
```bash
curl -sv -X PUT --user estrella:estrellaPass -d '{"restaurantId": 1}' -H "Content-Type: application/json" http://placeforlunch.herokuapp.com/rest/v1/profile/vote |json_pp
```
### Poll summary
```bash
$ curl -s -X GET --user estrella:estrellaPass http://placeforlunch.herokuapp.com/rest/v1/poll/vote-summary |json_pp
```
```json
[
   {
      "votes" : 3,
      "id" : 1
   },
   {
      "votes" : 2,
      "id" : 2
   },
   {
      "votes" : 2,
      "id" : 4
   },
   {
      "votes" : 1,
      "id" : 3
   },
   {
      "votes" : 2
   }
]
```
