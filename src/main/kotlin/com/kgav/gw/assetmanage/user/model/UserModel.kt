package com.kgav.gw.assetmanage.user.model

// 원래는 테이블에 맞는 Model로 하려고 했지만 현재는 JWT토큰에 대한 인증용을 변형
data class UserModel(
    // 해당 자료형 뒤에 ? 표시는 nullable 없으면 이 데이터 클래스를 이용할때 nullpoint exception 난다
    val userid: String,
    val userpw: String, // 테이블에서 사용하는게 id,pw라 그것만 표현
    val username: String, // 사용자 이름
    val commonName: String     // 사용자 권한
//    val roles: Int

)