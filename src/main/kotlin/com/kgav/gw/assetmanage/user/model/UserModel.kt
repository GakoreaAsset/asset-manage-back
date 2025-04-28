package com.kgav.gw.assetmanage.user.model

// userinfo 테이블
data class UserModel(
    val userid: String,
    val userpw: String, // 테이블에서 사용하는게 id,pw라 그것만 표현
    val cocd: String?, // 해당 자료형 뒤에 ? 표시는 nullable 없으면 이 데이터 클래스를 이용할때 nullpoint exception 난다
    val divcd : Int?
)