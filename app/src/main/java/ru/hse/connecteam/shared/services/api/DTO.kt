package ru.hse.connecteam.shared.services.api

data class UserSignUp(
    val email: String,
    val phone_number: String,
    val first_name: String,
    val second_name: String,
    val password: String,
)

data class UserSignIn(
    val email: String,
    val password: String,
)

data class Email(
    val email: String,
)

data class ID(
    val id: String,
    val code: String,
)

data class PasswordChange(
    val new_password: String,
    val old_password: String,
)

data class NewEmailVerification(
    val email: String,
    val password: String,
)

data class EmailChange(
    val new_email: String,
    val code: String,
)

data class UserData(
    val first_name: String,
    val second_name: String,
    val description: String,
)

data class CompanyData(
    val company_name: String,
    val company_url: String,
    val company_info: String,
)