package com.io.app.shakomako.ui.home

import android.content.Context
import androidx.databinding.BaseObservable
import com.io.app.shakomako.api.pojo.business.OtherBusinessProduct
import com.io.app.shakomako.api.pojo.business.OtherBusinessProfile
import com.io.app.shakomako.api.pojo.business.OtherBusinessProfileResponse
import com.io.app.shakomako.api.pojo.home.HomeItem
import com.io.app.shakomako.api.pojo.home.item.HomeFashionData
import com.io.app.shakomako.api.pojo.product.ProductRequest
import com.io.app.shakomako.api.pojo.profile.ProfileResponse
import com.io.app.shakomako.api.pojo.shop.BusinessDetail
import com.io.app.shakomako.api.pojo.shop.BusinessProducts
import com.io.app.shakomako.api.pojo.shop.BusinessProfile
import com.io.app.shakomako.api.repo.ApiRepository
import com.io.app.shakomako.ui.base.BaseViewModel
import com.io.app.shakomako.utils.ProfileFieldType
import com.io.app.shakomako.utils.session.UserSession
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    context: Context,
    apiRepository: ApiRepository,
    var userSession: UserSession
) :
    BaseViewModel() {

    init {
        this.context = context
        this.apiRepository = apiRepository
    }

    var dealObserver: DealObserver = DealObserver()
    var chatObserver: ChatObserver = ChatObserver()
    var shopObserver: ShopObserver = ShopObserver()
    var homeObserver: HomeObserver = HomeObserver()
    var otherBusinessObserver: OtherBusinessObserver = OtherBusinessObserver()
    var visibleObserver: VisibleObserver = VisibleObserver()
    var profileObserver: ProfileObserver = ProfileObserver()
    var shopItemObserver: ShopItemObserver = ShopItemObserver()
    var navigationShopObserver: NavigationShopObserver = NavigationShopObserver()
    var shopItemDetailObserver: ShopItemDetailsObserver = ShopItemDetailsObserver()
    var languageObserver: LanguageObserver = LanguageObserver()
    var editProfileFieldObserver: EditProfileFieldObserver = EditProfileFieldObserver()


    inner class DealObserver : BaseObservable() {
        var screenObserver: Int = 0
            set(value) {
                field = value
                notifyChange()
            }
    }

    inner class ChatObserver : BaseObservable() {
        var screenObserver: Int = 0
            set(value) {
                field = value
                notifyChange()
            }
    }

    inner class ShopObserver : BaseObservable() {
        /**
         * 0 - if business profile is not available  than we will show add details screen for business
         * 1 -  business profile is available so we will show my shop profile
         * */
        var screenObserver: Int = 0
            set(value) {
                field = value
                notifyChange()
            }

        var businessDetail: BusinessDetail = BusinessDetail()
            set(value) {
                field = value
                notifyChange()
            }

        var businessProfile: BusinessProfile = BusinessProfile()
            set(value) {
                field = value
                notifyChange()
            }

    }

    inner class HomeObserver : BaseObservable() {
        var homeFashionData: HomeFashionData = HomeFashionData()
            set(value) {
                field = value
                notifyChange()
            }
    }


    inner class OtherBusinessObserver : BaseObservable() {
        var otherBusinessProfileResponse: OtherBusinessProfileResponse =
            OtherBusinessProfileResponse()
            set(value) {
                field = value
                notifyChange()
            }

        var otherBusinessProduct: OtherBusinessProduct = OtherBusinessProduct()
            set(value) {
                field = value
                notifyChange()
            }

        var otherBusinessProfile: OtherBusinessProfile = OtherBusinessProfile()
            set(value) {
                field = value
                notifyChange()
            }
    }

    inner class VisibleObserver : BaseObservable() {
        var visible: Boolean = true
            set(value) {
                field = value
                notifyChange()
            }
    }


    inner class ProfileObserver : BaseObservable() {
        var profileObserverData: ProfileResponse = ProfileResponse()
            set(value) {
                field = value
                notifyChange()
            }
    }

    inner class ShopItemObserver : BaseObservable() {
        /** 0 = we are viewing other business product
         *  1 = we are viewing own business product
         * */
        var type: Int = -1
            set(value) {
                field = value
                notifyChange()
            }

        var businessProducts: BusinessProducts = BusinessProducts()
            set(value) {
                field = value
                notifyChange()
            }
    }

    inner class ShopItemDetailsObserver : BaseObservable() {
        var screenObserver: Int = 0
            set(value) {
                field = value
                notifyChange()
            }
    }

    inner class NavigationShopObserver : BaseObservable() {
        var homeItem: HomeItem = HomeItem()
            set(value) {
                field = value
                notifyChange()
            }
    }

    inner class LanguageObserver : BaseObservable() {
        var langObserver: String = "en"
            set(value) {
                field = value
                notifyChange()
            }
    }

    inner class EditProfileFieldObserver : BaseObservable() {
        var type: Int = 0
            set(value) {
                field = value
                notifyChange()
            }

        var editedText: String = ""
            set(value) {
                field = value
                notifyChange()
            }
    }

}