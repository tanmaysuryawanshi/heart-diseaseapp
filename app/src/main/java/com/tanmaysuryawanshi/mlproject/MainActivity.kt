package com.tanmaysuryawanshi.mlproject

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.tanmaysuryawanshi.mlproject.ml.HeartPredictionModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

class MainActivity : AppCompatActivity() {
    lateinit var submitbutton:Button
    lateinit var backbutton:ImageView
    lateinit var setDatabutton:ImageView
   // lateinit var textView: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      //  textView=findViewById(R.id.textView)
submitbutton=findViewById(R.id.buttonSubmit)
       backbutton=findViewById(R.id.backOption)
setDatabutton=findViewById(R.id.inputOption)

        val ageEditText = findViewById<EditText>(R.id.age)
        val sexEditText = findViewById<EditText>(R.id.sex)
        val cpEditText = findViewById<EditText>(R.id.chest_pain_type)
        val trestbpsEditText = findViewById<EditText>(R.id.resting_bp)
        val cholEditText = findViewById<EditText>(R.id.serum_cholesterol)
        val fbsEditText = findViewById<EditText>(R.id.fasting_blood_sugar)
        val restecgEditText = findViewById<EditText>(R.id.resting_ecg)
        val maxheartrateEditText = findViewById<EditText>(R.id.max_heart_rate)
        val exangEditText = findViewById<EditText>(R.id.exercise_induced_angina)
        val oldpeakEditText = findViewById<EditText>(R.id.oldpeak)
        val slopeEditText = findViewById<EditText>(R.id.slope)
        val numMajorVesselsEditText = findViewById<EditText>(R.id.num_major_vessels)
        val thalEditText = findViewById<EditText>(R.id.thal)
        val targetEditText = findViewById<EditText>(R.id.target)

        backbutton.setOnClickListener{

            it->
            onBackPressedDispatcher.onBackPressed()
        }



    submitbutton.setOnClickListener{
        it->




        val inputFeatures = FloatArray(14)
        inputFeatures[0] = ageEditText.text.toString().toFloat()
        inputFeatures[1] = sexEditText.text.toString().toFloat()
        inputFeatures[2] = cpEditText.text.toString().toFloat()
        inputFeatures[3] = trestbpsEditText.text.toString().toFloat()
        inputFeatures[4] = cholEditText.text.toString().toFloat()
        inputFeatures[5] = fbsEditText.text.toString().toFloat()
        inputFeatures[6] = restecgEditText.text.toString().toFloat()
        inputFeatures[7] = maxheartrateEditText.text.toString().toFloat()
        inputFeatures[8] = exangEditText.text.toString().toFloat()
        inputFeatures[9] = oldpeakEditText.text.toString().toFloat()
        inputFeatures[10] = slopeEditText.text.toString().toFloat()
        inputFeatures[11] = numMajorVesselsEditText.text.toString().toFloat()
        inputFeatures[12] = thalEditText.text.toString().toFloat()
        inputFeatures[13] = targetEditText.text.toString().toFloat()



//        val inputFeatures = floatArrayOf(
//            63f, 1f, 3f, 145f,
//            233f, 1f, 0f, 150f,
//
//            0f, 2.3f, 0f, 0f,
//            1f, 1f
//        )

        val byteBuffer = ByteBuffer.allocate(inputFeatures.size * 4) // 4 bytes per float
            .order(ByteOrder.nativeOrder())


        val inputBytes = byteBuffer.array()

        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 14), DataType.FLOAT32)
        inputFeature0.loadBuffer(ByteBuffer.wrap(inputBytes))

        val model = HeartPredictionModel.newInstance(this@MainActivity)
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        if(outputFeature0.intArray.get(0)==0){
            Toast.makeText(this,"not heart disease",Toast.LENGTH_SHORT).show()

            val dialog = Dialog(this)
            //  dialog.getWindow().setWindowAnimations(R.style.Dialog);

            //  dialog.getWindow().setWindowAnimations(R.style.Dialog);
            dialog.setContentView(R.layout.heart_disease_success)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val okBtnNotifiedDialog = dialog.findViewById<MaterialButton>(R.id.okButtonNotifed)
            dialog.show()

            //  tvNotifiedMessName.setText(m.getMessName());
            okBtnNotifiedDialog.setOnClickListener {
                dialog.dismiss()

            }

        }
        else{
            Toast.makeText(this," heart disease",Toast.LENGTH_SHORT).show()


        }

        model.close()

    }

setDatabutton.setOnClickListener{

    Toast.makeText(this@MainActivity,"loading ...",Toast.LENGTH_SHORT).show()

    ageEditText.setText("63")
 sexEditText.setText("1")
 cpEditText.setText("3")
 trestbpsEditText.setText("145")
 cholEditText.setText("233")
 fbsEditText.setText("1")
 restecgEditText.setText("0")
    maxheartrateEditText.setText("150")
 exangEditText.setText("0")
 oldpeakEditText.setText("2.3")
 slopeEditText.setText("0")
 numMajorVesselsEditText.setText("0")
 thalEditText.setText("1")
 targetEditText.setText("1")

}


    }
}


