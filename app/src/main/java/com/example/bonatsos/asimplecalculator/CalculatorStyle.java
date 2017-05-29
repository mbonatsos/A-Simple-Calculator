package com.example.bonatsos.asimplecalculator;

import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import com.vstechlab.easyfonts.EasyFonts;

/**
 * Provides static functions that aim to style views
 */
public class CalculatorStyle {

    //Animates the given view
    public static void animateView(View view) {

        //Create Translate animation
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 1,
                Animation.RELATIVE_TO_SELF, 0);
        translateAnimation.setDuration(1250);
        translateAnimation.setInterpolator(new AnticipateOvershootInterpolator(1f));

        //Create FadeAway Animation
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(1250);

        //Create Animation Set
        AnimationSet animationSet = new AnimationSet(false);
        //Add the animations to animation Set
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(alphaAnimation);

        //Animate the View
        view.setAnimation(animationSet);
    }

    //Changes the fonts of all buttons within a view
    public static void overrideFonts(View view, Typeface typeface) {
        try {
            if (view instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) view;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(child, typeface);
                }
            } else if (view instanceof Button) {
                ((Button) view).setTypeface(typeface);
            }
        } catch (Exception e) {
        }
    }

}
