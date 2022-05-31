package ro.matheszabi.sleepcountdown

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Calling the composable function
            // to display element and its contents
            MainContent()
        }
    }
}


// Creating a composable
// function to display Top Bar
@Composable
fun MainContent() {

    val mDateTitle = remember {
        mutableStateOf("Please select date")
    }
    // Declaring a string value to
    // store date in string format

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(mDateTitle.value, color = Color.White) },
                backgroundColor = Color(0xff0e5d58)
            )
        },
        content = {

            // Fetching the Local Context
            val mContext = LocalContext.current

            // Declaring a string value to
            // store date in string format
            val mDate = remember { mutableStateOf("") }
            val mDateDiff = remember { mutableStateOf("") }

            // Declaring integer values
            // for year, month and day
            val mYear: Int
            val mMonth: Int
            val mDay: Int

            // Initializing a Calendar
            val mCalendar = Calendar.getInstance()

            // Fetching current year, month and day
            mYear = mCalendar.get(Calendar.YEAR)
            mMonth = mCalendar.get(Calendar.MONTH)
            mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

            mCalendar.time = Date()


            // Declaring DatePickerDialog and setting
            // initial values as current values (present year, month and day)
            val mDatePickerDialog = DatePickerDialog(
                mContext,
                { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                    mDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"

                    val sdf = SimpleDateFormat("dd/MM/yyyy")

                    val dateSelected: Date = sdf.parse(mDate.value)!!

                    mDateTitle.value = "Selected  date: "+sdf.format( dateSelected )

                    val cal = Calendar.getInstance();
                    cal.time = dateSelected
                    // set the clock to the end of the day:
                    cal.set(Calendar.HOUR_OF_DAY, 23)
                    cal.set(Calendar.MINUTE, 60)
                    cal.set(Calendar.SECOND, 60)


                    val diffTimeMillisecond = (cal.time.time - mCalendar.timeInMillis)
                    val diffDays = java.util.concurrent.TimeUnit.DAYS.convert(
                        diffTimeMillisecond,
                        java.util.concurrent.TimeUnit.MILLISECONDS
                    )
                    println("Days: $diffDays");
                    mDateDiff.value = diffDays.toString()

                }, mYear, mMonth, mDay
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Creating a button that on
                // click displays/shows the DatePickerDialog
                Button(onClick = {
                    mDatePickerDialog.show()
                }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF0F9D58))) {
                    Text(text = "Select a Day \"to\"", color = Color.White)
                }


                // Displaying the mDate value in the Text
                Text(text = "Days \"to\" : ${mDateDiff.value}", fontSize = 30.sp, textAlign = TextAlign.Center)

                // Adding a space of 100dp height
                Spacer(modifier = Modifier.size(100.dp))

            }
        }
    )
}


// For displaying preview in
// the Android Studio IDE emulator
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainContent()
}