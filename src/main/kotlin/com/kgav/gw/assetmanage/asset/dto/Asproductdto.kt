package src.main.kotlin.com.kgav.gw.assetmanage.asset.dto

data class AssetDto(
    val as_type: String,
    val as_pd_name: String,
    val as_buy_cp: String,
    val as_place: String,
    val as_part: String,
    val as_user: String,
    val as_install_day: String,
    val as_state: String
)