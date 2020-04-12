package a.suman.bppcmarketplace

class LoginResponse(
    token: String,
    username: String,
    email: String
) {

    var token: String
    var username: String
    var email: String


    init {
        this.email = email
        this.username = username
        this.token = token
    }
}