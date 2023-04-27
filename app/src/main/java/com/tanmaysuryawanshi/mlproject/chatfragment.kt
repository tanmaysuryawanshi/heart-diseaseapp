package com.tanmaysuryawanshi.mlproject

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okio.IOException
import org.json.JSONException
import org.json.JSONObject


class chatfragment : Fragment() {
    private lateinit var textView: TextView
    private lateinit var editText: EditText
    lateinit var question:String
    val api_key=""

    lateinit var generateButton: Button
    val JSON: MediaType = "application/json; charset=utf-8".toMediaType()
    lateinit var loader:LottieAnimationView

    var client = OkHttpClient()

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v: View = inflater.inflate(R.layout.fragment_chat, container, false)

      //  val url = "https://api.openai.com/v1/engines/davinci-codex/completions"
       editText=v.findViewById<EditText>(R.id.editTextTextPersonName)

textView=v.findViewById<TextView>(R.id.textView2)
generateButton=v.findViewById(R.id.button)
        loader=v.findViewById(R.id.chatLoading)
        generateButton.setOnClickListener{
            it->
            loader.visibility=View.VISIBLE
it.hideKeyboard()
            it.clearFocus()
                question="I want you to act as a expert cardiologist and come up with creative treatments for illnesses or diseases. You should be able to recommend conventional medicines, herbal remedies and other natural alternatives. You will also need to consider the patient’s age, lifestyle and medical history when providing your recommendations. So my question is: "+editText.text.toString()

            callApi(question)
        }

        return v
    }

    @SuppressLint("SuspiciousIndentation")
    private fun callApi(question: String) {
     val jsonBody:JSONObject=JSONObject()
        try {
            jsonBody.put("model", "text-davinci-003")
            jsonBody.put("prompt",question)
            jsonBody.put("max_tokens",2000)

            jsonBody.put("temperature",0)
        }
        catch (e:JSONException){
            e.printStackTrace()
        }
       val body:RequestBody=RequestBody.Companion.create(JSON,jsonBody.toString())
        val request: Request =Request.Builder()
            .url("https://api.openai.com/v1/completions").header("Authorization","Bearer $api_key").post(body).build()
        client.newCall(request).enqueue(
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    loader.visibility=View.INVISIBLE
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                   // val responseBody = response.body?.string()
                    if (response.isSuccessful) {
                        var jsonObject: JSONObject? = null
                        try {
                            jsonObject = JSONObject(response.body!!.string())
                            val jsonArray = jsonObject.getJSONArray("choices")
                            val result = jsonArray.getJSONObject(0).getString("text")


                            if(!TextUtils.isEmpty(result.trim { it <= ' ' })){
                               requireActivity().runOnUiThread {
                                   textView.text=result.trim { it <= ' ' }
                               }
                            }
                            Handler(Looper.getMainLooper()).post(Runnable {
                                loader.visibility=View.GONE

                            })

                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    } else {
                        loader.visibility=View.INVISIBLE
                        Toast.makeText(context,"Failed to load response due to " + response.body.toString(),Toast.LENGTH_LONG).show()


                    }
                }
            }
        )


    }
    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

}


