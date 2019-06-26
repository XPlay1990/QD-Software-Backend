//package com.nicando.ediportal.rest.login
//
//import com.nicando.ediportal.database.model.user.User
//import com.nicando.ediportal.database.repositories.RoleRepository
//import com.nicando.ediportal.database.repositories.UserRepository
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RestController
//
//
///**
// * Created by Jan Adamczyk on 24.06.2019.
// */
//@RestController
//@RequestMapping("/register")
//class RegisterController(private val bCryptPasswordEncoder: BCryptPasswordEncoder,
//                         private val userRepository: UserRepository,
//                         private val roleRepository: RoleRepository) {
//
////    fun register(user: User) {
////        user.password = bCryptPasswordEncoder.encode(user.password)
////        user.roles = roleRepository.findAll()
////        userRepository.save(user)
////    }
//}