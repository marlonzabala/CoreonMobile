package android.support.v4.view;

import android.content.Context;
import android.util.AttributeSet;

public class MyViewPager extends ViewPager
{

	public MyViewPager(Context context)
	{
		super(context);
	}

	public MyViewPager(Context context, AttributeSet attr)
	{
		super(context, attr);
	}

	void smoothScrollTo(int x, int y, int velocity)
	{
		super.smoothScrollTo(x, y, 1);
		//custom viewpager for better speed
	}
}

//
//
//
// package com.itcorea.coreonmobile;
//
// import java.lang.reflect.Field;
//
// import android.content.Context;
// import android.support.v4.view.ViewPager;
// import android.util.AttributeSet;
// import android.view.animation.DecelerateInterpolator;
// import android.widget.Scroller;

// public class MyViewPager extends ViewPager
// {
// public MyViewPager(Context context, AttributeSet attrs)
// {
// super(context, attrs);
// setMyScroller();
// }
//
// private void setMyScroller()
// {
// try
// {
// Class<?> viewpager = ViewPager.class;
// Field scroller = viewpager.getDeclaredField("mScroller");
// scroller.setAccessible(true);
// scroller.set(this, new MyScroller(getContext()));
// }
// catch (Exception e)
// {
// e.printStackTrace();
// }
// }
//
// public class MyScroller extends Scroller
// {
// public MyScroller(Context context)
// {
// super(context, new DecelerateInterpolator());
// }
//
// @Override
// public void startScroll(int startX, int startY, int dx, int dy, int duration)
// {
// super.startScroll(startX, startY, dx, dy, 1000 /* 1 secs */);
// }
// }
// }

// As we were pivoting to an email app, we had to take a few key decisions early on. As a
// typical startup with constrained resources, we had to focus hard to be able to quickly
// deliver an MVP.
//
// A key decision was to decide which platforms to support on launch. We had the following
// options -
//
// Option 1 - The traditional Valley way i.e. start with a feature rich iOS version and then
// follow up with the Android version later.
//
// Option 2 – Launch on both iOS and Android with a reduced feature set.
//
// We chose Option 2. While this meant that we’d have to build out 2 apps instead of just 1
// to start with, it was a good decision.
//
// Why?
//
// 1. Android users are underserved
//
// Let’s face it. Most cool and innovative startups focus on iOS, leaving Android users
// yearning for more. Android users are equally sophisticated and are equally (if not more)
// eager to try new things.
//
// 2. Android numbers are huge
//
// Our user base is evenly split between iOS and Android. If we were iOS only, we would have
// had at least 50% of our current user base not even introduced to the product.
//
// 3. Android lets you run rapid experiments
//
// In the age of A/B testing, rapid iterations and finding product/market fit quickly, the
// iOS approval cycle is a big deterrent. It is much easier to conduct experiments and learn
// fast on Android.
//
// 4. Android users are more critical
//
// While 5-star ratings and reviews singing praises about your freaking awesome app is good
// for your ego, team morale & app store rankings — they are usually not actionable. There
// is nothing to learn from such reviews. 3-star and less with constructive criticism are
// the best reviews for your product.
//
// I can’t say for all products, but for us, the quality of Android reviews have been higher
// compared to iOS reviews.
//
// 5. Play Store is more developer friendly
//
// There a few nice things in the Play Store that make the life of a developer less painful.
//
// Examples:
// - Replying to user reviews
// - Beta channel to let early adopters try your bleeding edge builds
// - Better app store analytics to see what’s going on
//
// 6. Healthy competition between both iOS and Android team internally
//
// At CloudMagic, both iOS and Android teams compete against each other to deliver the best
// experience possible for that platform. We don’t try hard to synchronize feature sets and
// experience between the two platforms. Each platform has its own nuances and development
// speed too. This healthy competition avoids having a lopsided fanboi mentality inside the
// company.

