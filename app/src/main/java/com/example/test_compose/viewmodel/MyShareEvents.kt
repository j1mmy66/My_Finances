package com.example.test_compose.viewmodel

import com.example.test_compose.data.models.MyShare

sealed interface MyShareEvents {
    data class  BuyShares(val myShare: MyShare) : MyShareEvents


    data class DeleteShare(val myShare: MyShare) : MyShareEvents


}