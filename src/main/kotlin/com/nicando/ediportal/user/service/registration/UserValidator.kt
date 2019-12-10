package com.nicando.ediportal.user.service.registration

import com.nicando.ediportal.user.database.model.User
import com.nicando.ediportal.user.database.repository.UserRepository
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Errors
import org.springframework.stereotype.Component
import org.springframework.validation.Validator


/**
 * Created by Jan Adamczyk on 24.06.2019.
 */
@Component
class UserValidator(private val userRepository: UserRepository) : Validator {
//    @Autowired
//    private val userService: UserService? = null

    override fun supports(aClass: Class<*>): Boolean {
        return User::class == aClass
    }

    override fun validate(o: Any, errors: Errors) {
        val user = o as User

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty")
//        if (user.username.length < 3 || user.username.length > 32) {
//            errors.rejectValue("username", "Size.userForm.username")
//        }
        if (userRepository.existsByUsername(user.username)) {
            errors.rejectValue("username", "Duplicate.userForm.username")
        }

//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty")
//        if (user.password.length < 8 || user.password.length > 32) {
//            errors.rejectValue("password", "Size.userForm.password")
//        }

//        if (!user.getPasswordConfirm().equals(user.getPassword())) {
//            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm")
//        }
    }
}