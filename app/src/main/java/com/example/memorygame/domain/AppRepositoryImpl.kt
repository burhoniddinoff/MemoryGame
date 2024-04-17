package com.example.memorygame.domain

import com.example.memorygame.R
import com.example.memorygame.data.CardData
import com.example.memorygame.data.LevelEnum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor() : AppRepository {

    companion object {
        private lateinit var instance: AppRepositoryImpl

        fun getInstance(): AppRepositoryImpl {
            if (!(Companion::instance.isInitialized))
                instance = AppRepositoryImpl()
            return instance
        }
    }

    private val images: ArrayList<CardData> = ArrayList()
    private var result = ArrayList<CardData>()

    init {
        initImages()
    }

    override fun getImagesByLevel(level: LevelEnum): Flow<List<CardData>> = flow {
        val count = level.rowCount * level.columnCount / 2

        val ls = images.subList(0, count)
        val result = ArrayList<CardData>(ls)
        result.addAll(ls)

        result.shuffle()
        emit(result)

//        val shuffledImages = images
//
//        val count = level.rowCount * level.columnCount / 2
//
//        result.addAll(shuffledImages.subList(0, count))
//        result.addAll(shuffledImages.subList(0, count))
//        result = result.shuffled() as ArrayList<CardData>
//
//        emit(result)
    }.flowOn(Dispatchers.IO)



    override fun checkOpens(openedImages: ArrayList<Int>): Boolean {
        return result[openedImages[0]] == result[openedImages[1]]
    }

    private fun initImages() {
        images.add(CardData(1, R.drawable.image_1))
        images.add(CardData(2, R.drawable.image_2))
        images.add(CardData(3, R.drawable.image_3))
        images.add(CardData(4, R.drawable.image_4))
        images.add(CardData(5, R.drawable.image_5))
        images.add(CardData(6, R.drawable.image_6))
        images.add(CardData(7, R.drawable.image_7))
        images.add(CardData(8, R.drawable.image_8))
        images.add(CardData(9, R.drawable.image_9))
        images.add(CardData(10, R.drawable.image_10))
        images.add(CardData(11, R.drawable.image_11))
        images.add(CardData(12, R.drawable.image_12))
        images.add(CardData(13, R.drawable.image_13))
        images.add(CardData(14, R.drawable.image_14))
        images.add(CardData(15, R.drawable.image_15))
        images.add(CardData(16, R.drawable.image_16))
        images.add(CardData(17, R.drawable.image_17))
        images.add(CardData(18, R.drawable.image_18))
        images.add(CardData(19, R.drawable.image_19))
        images.add(CardData(20, R.drawable.image_20))
        images.add(CardData(21, R.drawable.image_21))
        images.add(CardData(22, R.drawable.image_22))
        images.add(CardData(23, R.drawable.image_23))
        images.add(CardData(24, R.drawable.image_24))
        images.add(CardData(25, R.drawable.image_25))
        images.add(CardData(26, R.drawable.image_26))
        images.add(CardData(27, R.drawable.image_27))
        images.add(CardData(28, R.drawable.image_28))
        images.add(CardData(29, R.drawable.image_29))
        images.add(CardData(30, R.drawable.image_30))
        images.add(CardData(31, R.drawable.image_31))
        images.add(CardData(32, R.drawable.image_32))
        images.add(CardData(33, R.drawable.image_33))
        images.add(CardData(34, R.drawable.image_34))
        images.add(CardData(35, R.drawable.image_35))
        images.add(CardData(36, R.drawable.image_36))
        images.add(CardData(37, R.drawable.image_37))
        images.add(CardData(38, R.drawable.image_38))
        images.add(CardData(39, R.drawable.image_39))
        images.add(CardData(40, R.drawable.image_40))
        images.add(CardData(41, R.drawable.image_41))
        images.add(CardData(42, R.drawable.image_42))
        images.add(CardData(43, R.drawable.image_43))
        images.add(CardData(44, R.drawable.image_44))
        images.add(CardData(45, R.drawable.image_45))
        images.add(CardData(46, R.drawable.image_46))
        images.add(CardData(47, R.drawable.image_47))
        images.add(CardData(48, R.drawable.image_48))
        images.add(CardData(49, R.drawable.image_49))
        images.add(CardData(50, R.drawable.image_50))
        images.add(CardData(51, R.drawable.image_51))
        images.add(CardData(52, R.drawable.image_52))
        images.add(CardData(53, R.drawable.image_53))
        images.add(CardData(54, R.drawable.image_54))



    }

}