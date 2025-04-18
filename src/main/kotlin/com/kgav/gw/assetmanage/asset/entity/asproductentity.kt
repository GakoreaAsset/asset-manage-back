package src.main.kotlin.com.kgav.gw.assetmanage.asset.entity

import jakarta.persistence.*
import java.io.Serializable

// JPA 엔티티 정의
@Entity
@table(name = "asProduct")
data class asProduct(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val as_seq: Int = 0,  // 기본키

    val as_type: String,
    val as_pd_name: String,
    val as_buy_cp: String,
    val as_place: String,
    val as_part: String,
    val as_user: String,
    val as_install_day: String,
    val as_state: String
) : Serializable