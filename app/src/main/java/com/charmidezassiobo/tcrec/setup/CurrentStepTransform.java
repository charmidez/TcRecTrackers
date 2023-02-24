package com.charmidezassiobo.tcrec.setup;

import android.content.Context;
import android.util.AttributeSet;

import com.shuhart.stepview.StepView;

public class CurrentStepTransform extends StepView {

    //protected int currentStep;


    private static final int NOUVELLE_VALEUR = 1;
    //public int START_STEP;
    //public int currentStep;
    //public int IDLE = 1;



    public CurrentStepTransform(Context context) {
        super(context);
    }

    public CurrentStepTransform(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CurrentStepTransform(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int transCurrentStep(int stptr) {
        int stepCount = getStepCount();
        return stepCount;

    }

}
