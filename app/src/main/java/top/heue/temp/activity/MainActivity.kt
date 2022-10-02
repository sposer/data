package top.heue.temp.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import io.objectbox.EntityInfo
import io.objectbox.Property
import top.heue.temp.R
import top.heue.temp.data.SampleData
import top.heue.temp.data.SampleData_
import top.heue.utils.data.other.DH

@SuppressLint("SetTextI18n")
class MainActivity : Activity() {
    private lateinit var input: EditText
    private lateinit var output: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        input = findViewById(R.id.main_edt_input)
        output = findViewById(R.id.main_txt_msg)
    }

    fun add(view: View?) {
        val s = input.text.toString()
        if (s.isEmpty()) {
            output.text = "输入为空"
            return
        }
        val data = SampleData()
        data.text = s
        DH.addOrSet(data)
        output.text = "添加 $s 成功"
    }

    fun remove(view: View?) {
        val s = input.text.toString()
        if (s.isEmpty()) {
            output.text = "输入为空"
            return
        }
        val data = DH.findByAny(SampleData_.text, s)
        if (data.isEmpty()) {
            output.text = "查询结果为空"
            return
        }
        output.text = "查询到 ${data.size} 个结果"
        data.forEach {
            DH.removeByObject<SampleData>(it)
        }
        output.text = "查询到 ${data.size} 个结果\n" +
                "删除完成"
    }

    private var words: SampleData? = null
    fun edit(view: View?) {
        if (words == null) {
            output.text = "请先进行 查 ，且保证结果数量为1"
            return
        }
        val s = input.text.toString()
        if (s.isEmpty()) {
            output.text = "更改内容为空，请输入内容"
            return
        }
        words!!.text = s
        DH.addOrSet(words)
        output.text = "更改成功，当前为 $s"
    }

    fun search(view: View?) {
        val s = input.text.toString()
        if (s.isEmpty()) {
            output.text = "输入为空"
            return
        }
        val data = DH.findByAny(SampleData_.text, s)
        if (data.isEmpty()) {
            output.text = "查询结果为空"
            return
        }
        if (data.size == 1) {
            words = data[0]
            output.text = "现在可以重新输入内容后点击 改 以更改"
        } else {
            output.text = "结果超过一个，为 ${data.size} 个，该示例仅适用一个结果的时候"
        }
    }
}