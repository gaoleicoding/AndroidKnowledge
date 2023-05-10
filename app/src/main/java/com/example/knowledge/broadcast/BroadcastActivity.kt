package com.example.knowledge.broadcast

import com.example.knowledge.R
import com.example.knowledge.activity.BaseActivity

class BroadcastActivity : BaseActivity() {


    override fun getLayoutId(): Int {
        return R.layout.activity_broadcast
    }

    override fun initView() {
        mTitleTv.text = "Broadcast"
    }

    override fun initData() {
    }


}