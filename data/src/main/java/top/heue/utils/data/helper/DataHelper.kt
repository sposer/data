package top.heue.utils.data.helper

import android.content.Context
import android.util.Log
import io.objectbox.BoxStore
import io.objectbox.BoxStoreBuilder
import io.objectbox.Property
import io.objectbox.query.QueryBuilder
import java.lang.Thread.sleep
import java.util.*
import kotlin.concurrent.thread

object DataHelper {
    private var box: BoxStore? = null
    val boxStore: BoxStore get() = box!!

    init {
        //检查
        thread {
            sleep(3000)
            if (box == null) Log.w(
                "DataHelper",
                "has not bean call init() method, this DataHelper would not be use normally"
            )
        }
    }

    @JvmStatic
    fun init(context: Context, boxStoreBuilder: BoxStoreBuilder) {
        box?.close()
        box = boxStoreBuilder.androidContext(context).build()
    }
    /*fun init(context: Context) {
        //box = MyObjectBox.builder().androidContext(context).build()

        //防止忘记，初始化检查
        if (box == null) L.w("BoxStore 尚未初始化，请调用")
    }*/

    inline fun <reified T> addOrSet(bean: T): Long {
        return boxStore.boxFor(T::class.java).put(bean)
    }

    inline fun <reified T> addOrSet(beans: Collection<T>) {
        boxStore.boxFor(T::class.java).put(beans)
    }

    inline fun <reified T> removeAll() {
        boxStore.boxFor(T::class.java).removeAll()
    }

    inline fun <reified T> removeById(vararg ids: Long) {
        val list = ArrayList<Long>()
        ids.forEach {
            list.add(it)
        }
        removeById<T>(list)
    }

    inline fun <reified T> removeById(ids: Collection<Long>) {
        boxStore.boxFor(T::class.java).removeByIds(ids)
    }

    inline fun <reified T> removeByObject(bean: T) {
        boxStore.boxFor(T::class.java).remove(bean)
    }

    inline fun <reified T> findAll(): List<T> {
        return boxStore.boxFor(T::class.java).query().build().find()
    }

    inline fun <reified T> findByReverse(property: Property<T>): List<T> {
        return boxStore.boxFor(T::class.java).query().orderDesc(property).build().find()
    }

    inline fun <reified T> findByPage(page: Long, size: Long): List<T> {
        return boxStore.boxFor(T::class.java).query().build().find((page - 1) * size, size)
    }

    /**
     * @sample property Bean_.id
     * @sample value 1L
     */
    inline fun <reified T> findByAny(property: Property<T>, value: Long): List<T> {
        //equal等于,greater大于,less小于,and和,or或
        return boxStore.boxFor(T::class.java).query()
            .equal(property, value)
            .orderDesc(property).build().find()
    }

    /**
     * @sample property Bean_.id
     * @sample value 1L
     */
    inline fun <reified T> findOneByAny(property: Property<T>, value: Long): T {
        //equal等于,greater大于,less小于,and和,or或
        return findByAny(property, value)[0]
    }

    /**
     * @sample property Bean_.id
     * @sample value 1L
     */
    inline fun <reified T> findOneByAny(property: Property<T>, value: String): T {
        //equal等于,greater大于,less小于,and和,or或
        return findByAny(property, value)[0]
    }

    inline fun <reified T> findByAny(property: Property<T>, value: String): List<T> {
        return boxStore.boxFor(T::class.java).query()
            .contains(property, value, QueryBuilder.StringOrder.CASE_INSENSITIVE)
            .orderDesc(property).build().find()
    }

    inline fun <reified T> findByAny(property: Property<T>, value: ByteArray): List<T> {
        return boxStore.boxFor(T::class.java).query()
            .equal(property, value)
            .orderDesc(property).build().find()
    }

    inline fun <reified T> findByAny(property: Property<T>, value: Boolean): List<T> {
        return boxStore.boxFor(T::class.java).query()
            .equal(property, value)
            .orderDesc(property).build().find()
    }

    inline fun <reified T> findByAny(property: Property<T>, value: Date): List<T> {
        return boxStore.boxFor(T::class.java).query()
            .equal(property, value)
            .orderDesc(property).build().find()
    }
}