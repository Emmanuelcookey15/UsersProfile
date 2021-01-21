package com.LineCard.userprofileprojectquestionone

import com.LineCard.userprofileprojectquestionone.model.Users
import org.junit.Test

import org.junit.Assert.*

class UsersTest {


    @Test
    fun users_fields_isworking(){
        val aUser = Users()
        aUser.id = 1
        aUser.name = "My Name"
        aUser.email ="myname@mail.com"
        aUser.username = "my_name"
        aUser.phone = "990-998-97"
        assertNotNull(aUser)
    }

    @Test
    fun users_field_id_iscorrect_datatype() {
        val aUser = Users()
        aUser.id = 1
        aUser.name = "My Name"
        assertNotSame(aUser.id, "1")
        assertNotSame(aUser.id, 1.0)
        assertSame(aUser.id, 1)
    }

    @Test
    fun users_field_ignored_isempty() {
        val aUser = Users()
        aUser.id = 1
        aUser.name = "My Name"
        assertEquals(aUser.username, "")
        assertEquals(aUser.email, "")
        assertEquals(aUser.phone, "")
    }



}