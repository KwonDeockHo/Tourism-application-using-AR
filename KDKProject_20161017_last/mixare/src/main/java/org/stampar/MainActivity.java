package org.stampar;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.ads.doubleclick.AppEventListener;


/**
 * This is the main activity of mixare, that will be opened if mixare is
 * launched through the android.intent.action.MAIN the main tasks of this
 * activity is showing a prompt dialog where the user can decide to launch the
 * plugins, or not to launch the plugins. This class is also able to remember
 * those decisions, so that it can forward directly to the next activity.
 * 
 * @author A.Egal
 */
public class MainActivity extends Activity  {
	Context ctx;
	Button login_button;
	Button signUp_button;
	EditText idInput, passwordInput;
	Boolean loginChecked = false;
	Boolean touchChecked = false;
	SharedPreferences pref;
	SharedPreferences.Editor editor;

	ImageView login_title = null;
	ImageView login_start = null;
	LoginButton login_facebook = null;

	RelativeLayout back;
	InputMethodManager imm;

	ObjectAnimator obj = null;
	ObjectAnimator obj2 = null;

	public Animation animationFadeIn = null;

	CallbackManager callbackManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		FacebookSdk.sdkInitialize(this.getApplicationContext());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_init);
		callbackManager = CallbackManager.Factory.create();
		ctx = this;
		pref = getSharedPreferences("KDK_DATA", Activity.MODE_PRIVATE);
		editor = pref.edit();

		idInput = (EditText) findViewById(R.id.emailInput);
		passwordInput = (EditText) findViewById(R.id.passwordInput);
		login_button = (Button)findViewById(R.id.loginButton);
		signUp_button = (Button)findViewById(R.id.signupButton);

		login_title = (ImageView)findViewById(R.id.login_title);
		login_start = (ImageView)findViewById(R.id.login_start);
		login_facebook = (LoginButton)findViewById(R.id.login_facebook);


		back = (RelativeLayout)findViewById(R.id.layer);
		imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

		animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		obj = ObjectAnimator.ofFloat(login_title,"y",600);
		obj2 = ObjectAnimator.ofFloat(login_facebook,"y",1200);
		if(!pref.getBoolean("INIT", false))
		{
			Log.v("ANDONG", "데이터 없음");
			editor.putBoolean("INIT", true);
			// 다시 이 부분을 실행시키기 위해서는 프로그램을 지웠다가 다시 깔아야 함.
			// 이 안에 스탬프 4개에 대한 boolean 데이터를 넣는다. 그리고 그 외에 필요한 여러가지 정보를 넣자.
			// 1000 일 경우, 한국어임.
			editor.putInt("LANG", 1000);
			// 2000 일 경우, 외국어임.
			//
			editor.commit();
		}
		else
		{
			Log.v("ANDONG", "데이터 있음.");
		}
		login_button.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//startActivity(new Intent(ctx, SecondMain.class));
				//finish();

				String id = idInput.getText().toString();
				String password = passwordInput.getText().toString();
				Boolean validation = loginValidation(id, password);

				if(validation)
				{
					Log.v("LOGIN", "로그인 성공");
					//startActivity(new Intent(ctx, SecondMain.class));
					startActivity(new Intent(ctx, tutorial_main.class));
					finish();
				}
				else
				{
					Toast.makeText(MainActivity.this, "정보가 틀립니다.", Toast.LENGTH_LONG).show();
					//Log.v("LOGIN", "로그인 실패");
					idInput.setText("");
					passwordInput.setText("");
				}
			}
		});


		LoginManager.getInstance().registerCallback(callbackManager,new FacebookCallback<LoginResult>() {

			@Override
			public void onSuccess(LoginResult loginResult) {
				// App code
				login_start.setVisibility(View.VISIBLE);
				login_start.startAnimation(animationFadeIn);
			}

			@Override
			public void onCancel() {
				// App code
			}

			@Override
			public void onError(FacebookException exception) {
				// App code
			}
		});

		signUp_button.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				if(!pref.getBoolean("SIGNUP", false))
				{
					if(!idInput.getText().toString().equals("") && !idInput.getText().toString().equals("")) {

						// 초기 정보 없음.
						editor.putBoolean("SIGNUP", true);
						// 회원 정보를 넣는 공간임.
						editor.putString("ID", idInput.getText().toString());
						editor.putString("PW", passwordInput.getText().toString());
						//
						editor.commit();
						Toast.makeText(MainActivity.this, "회원가입 완료", Toast.LENGTH_LONG).show();
					}
					else
					{
						Toast.makeText(MainActivity.this, "아이디와 비밀번호를\n입력해주세요!", Toast.LENGTH_LONG).show();
					}
				}
				else {
					Toast.makeText(MainActivity.this, "이미 회원정보가 있습니다.", Toast.LENGTH_LONG).show();
					Log.v("SIGNUP", "id is " + pref.getString("ID", "").toString());
					Log.v("SIGNUP", "pw is " + pref.getString("PW", "").toString());
				}
			}
		});
	}
	public void startOnClick(View v)
	{
		//
		startActivity(new Intent(this, tutorial_main.class));
		finish();
	}


	public void linearOnClick(View v)
	{
		if(touchChecked)
		{

		}
		else
		{
			imm.hideSoftInputFromWindow(idInput.getWindowToken(), 0);
			imm.hideSoftInputFromWindow(passwordInput.getWindowToken(), 0);

			//login_button.setVisibility(View.VISIBLE);
			//signUp_button.setVisibility(View.VISIBLE);

			//login_button.startAnimation(animationFadeIn);
			//signUp_button.startAnimation(animationFadeIn);
			login_facebook.setVisibility(View.VISIBLE);
			login_facebook.startAnimation(animationFadeIn);

			obj.setDuration(600);
			obj.start();

			obj2.setDuration(600);
			obj2.start();

			touchChecked = true;
		}

		/* 리스너 추가하는 것. 애니메이션에 대한
		animation.setAnimationListener(new Animation.AnimationListener(){
			@Override
			public void onAnimationStart(Animation arg0) {
			}
			@Override
			public void onAnimationRepeat(Animation arg0) {
			}
			@Override
			public void onAnimationEnd(Animation arg0) {
				login_title.setY(300.0f);
			}
		});
		*/
	}

	// 로그인을 주관하는 함수임.
	private boolean loginValidation(String id, String password) {
		pref = getSharedPreferences("KDK_DATA", Activity.MODE_PRIVATE);
		editor = pref.edit();
		if(pref.getString("ID","").equals(id) && pref.getString("PW","").equals(password)) {
			// login success
			return true;
		} else if (pref.getString("ID","").equals(null)){
			// sign in first
			Toast.makeText(MainActivity.this, "Please Sign in first", Toast.LENGTH_LONG).show();
			return false;
		} else {
			// login failed
			return false;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		callbackManager.onActivityResult(requestCode, resultCode, data);
	}
}