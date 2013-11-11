package com.example.ds;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;

public class SurveyActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_survey);
		
		
		EditText surveyCoffee=(EditText)findViewById(R.id.survey_coffee);
		surveyCoffee.setInputType(InputType.TYPE_CLASS_NUMBER);
	}

	
	public void onCheckboxClicked(View view) {
	    // Is the view now checked?
	    boolean checked = ((CheckBox)view).isChecked();
	    
	    // Check which checkbox was clicked
	    switch(view.getId()) {
	        case R.id.checkbox_male:
	            if (checked){
	            	
	            }
	                
	            else
	               
	            break;
	        case R.id.checkbox_female:
	            if (checked){
	            	
	            }
	                
	            else
	                
	            break;
	        case R.id.checkbox_alarmclocktrue:
	        	if(checked){
	        		
	        	}
	        	else
	            
	            break;
	        case R.id.checkbox_alarmclockfalse:
	        	if(checked){
	        		
	        	}
	        	else
	        		
	            break;
	    }
	}
	
	public void onSurveyComplete(View view){		
		Intent intent = new Intent(this,MainActivity.class);
		startActivity(intent);
		
	}

}
