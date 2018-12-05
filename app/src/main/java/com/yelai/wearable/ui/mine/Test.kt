package com.yelai.wearable.ui.mine

/**
 * Created by hr on 18/11/7.
 */

enum class Test private constructor(private val content: Int) {

    A(0),
    B(1);


    fun value(): Int {
        return this.content
    }


}
