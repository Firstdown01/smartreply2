package com.example.smartreply2

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.IOException
import org.json.JSONObject


class Recyclerview : AppCompatActivity() {

    val JSON: MediaType = "application/json; charset=utf-8".toMediaType()
    var client = OkHttpClient()

    @RequiresApi(34)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)


        findViewById<ImageButton>(R.id.button).setOnClickListener {

            val editview = findViewById<EditText>(R.id.edittext)
            var input = editview.text
//            Toast.makeText(this, input.toString(), Toast.LENGTH_LONG).show()

            addToChat(input.toString(), false)

            call(input.toString())
            editview.setText("")
        }
    }

    fun addToChat(response: String, boolean: Boolean) {
        runOnUiThread {
            var objectarray = ArrayList<Message>()
            var message=Message(response,boolean)
            objectarray.add(message)
            val adapter = Adapter(objectarray)
            val list = findViewById<RecyclerView>(R.id.recyclerview)
            list.adapter = adapter
        }
    }

    @RequiresApi(34)
    fun call(question: String) {
            var key = "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"
            var url = "https://api.openai.com/v1/completions"
            lateinit var jsobj: JSONObject
            jsobj.put("model", "text-davinci-003")
            jsobj.put("prompt", question)
            jsobj.put("max_tokens", 7)
            jsobj.put("temperature", 0)

            var body: RequestBody = jsobj.toString().toRequestBody(JSON)
            val request:Request =

                   Request.Builder()
                        .url("https://api.openai.com/v1/completions")
                        .header("Authorization", "Bearer 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")
                        .post(body)
                        .build()

        Toast.makeText(this@Recyclerview,"url",Toast.LENGTH_LONG).show()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    addToChat("fault",true)
                }
                override fun onResponse(call: Call, response: Response) {
                    if(response.isSuccessful){
                        var jsonobj=JSONObject(response.body?.string())
                        var jsonarray=jsonobj.getJSONArray("choices")
                        var result=jsonarray.getJSONObject(0).get("text")
                        addToChat(result.toString(),true)
                    }
                    else{
                        addToChat("failed",true)
                    }

                }
            })


    }

}