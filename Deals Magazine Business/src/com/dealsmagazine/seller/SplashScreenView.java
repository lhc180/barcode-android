/*
 * 
 * © 2011 WhereYouShop.com  All Rights Reserved | http://www.whereyoushop.com 
 * Emergency 24    | The WhereYouShop Team  
 *
 */

package com.dealsmagazine.seller;

/*
 * Splash Screen Activity
 * 
 */
import com.dealsmagazine.globals.Globals;
import com.dealsmagazine.seller.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreenView extends Activity {

	protected boolean _active = true;
	// Setup the splash screen time
	protected int _splashTime = Globals.SPLASH_TIME;

	private ImageView iv_splash;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle saveInstanceState) {
		super.onCreate(saveInstanceState);
		setContentView(R.layout.splash);

		// fade in logo
		iv_splash = (ImageView) this.findViewById(R.id.iv_splash);
		Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein);
		iv_splash.startAnimation(myFadeInAnimation);

		// Add other effect to Welcome Screen
		Thread splashTread = new Thread() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.Thread#run()
			 */
			@Override
			public void run() {
				try {
					int waited = 0;
					while (_active && (waited < _splashTime)) {
						sleep(100);
						if (_active) {
							waited += 100;
						}
					}
				} catch (InterruptedException e) {
					// TODO: handle exception
				} finally {
					finish();
					// Start Main Activity
					startActivity(new Intent("com.dealsmagazine.seller.DealsMagazineBusinessActivity"));
				}
			}
		};
		splashTread.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			_active = false;
		}
		return true;
	}
}
