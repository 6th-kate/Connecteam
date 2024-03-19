@file:Suppress("PropertyName")

package ru.hse.connecteam.shared.services.api

data class UserSignUp(
    val email: String,
    val phone_number: String,
    val first_name: String,
    val second_name: String,
    val password: String,
)

data class ID(
    val id: String,
)

data class UserSignIn(
    val email: String,
    val password: String,
)

data class UserAuth(
    val token: String,
    val access: String,
)

data class Email(
    val email: String,
)

data class CodeVerification(
    val id: String,
    val code: String,
)

data class Status(
    val status: String,
)

data class UserData(
    val access: String,
    val company_info: String,
    val company_logo: String,
    val company_url: String,
    val company_name: String,
    val description: String,
    val email: String,
    val first_name: String,
    val id: String,
    val profile_image: String,
    val second_name: String,
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

data class UserPersonal(
    val first_name: String,
    val second_name: String,
    val description: String,
)

data class AccessChange(
    val id: String,
    val access: String,
)

data class CompanyData(
    val company_name: String,
    val company_url: String,
    val company_info: String,
)

data class TariffData(
    val id: String,
    val expiry_date: String,
    val holder_id: String,
    val user_id: String,
    val plan_access: String,
    val plan_type: String,
    val status: String,
    val invitation_code: String,
    val is_trial: String,
)

data class TariffRequest(
    val duration: Int,
    val plan_type: String,
)

data class TariffConfirm(
    val status: String,
    val duration: String,
    val expiry_date: String,
    val holder_id: String,
    val plan_access: String,
    val plan_type: String,
    val user_id: String,
)

data class TrialData(
    val status: String,
    val duration: String,
    val expiry_date: String,
    val holder_id: String,
    val plan_access: String,
    val plan_type: String,
    val user_id: String,
    val is_trial: String,
)

data class TariffMember(
    val id: String,
    val first_name: String,
    val second_name: String,
    val email: String,
)

data class JoinTariffData(
    val id: String,
    val status: String,
    val duration: String,
    val expiry_date: String,
    val holder_id: String,
    val plan_access: String,
    val plan_type: String,
    val user_id: String,
)

data class UserByIdData(
    val first_name: String,
    val second_name: String,
)

data class CreateGame(
    val name: String,
    val start_date: String,
)

data class GameCreated(
    val id: String,
    val name: String,
    val start_date: String,
    val status: String,
    val invitation_code: String,
)

data class GameBasic(
    val id: String,
    val creator_id: String,
    val name: String,
    val start_date: String,
    val status: String,
    val invitation_code: String,
)

data class Data<T>(
    val data: List<T>
)