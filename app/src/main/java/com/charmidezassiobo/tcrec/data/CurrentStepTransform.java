package com.charmidezassiobo.tcrec.data;

import android.content.Context;
import android.util.AttributeSet;

import com.shuhart.stepview.StepView;

public class CurrentStepTransform extends StepView {
    private static final int NOUVELLE_VALEUR = 1;
    public int START_STEP = 0;
    public int currentStep = 0;



    public CurrentStepTransform(Context context) {
        super(context);
    }

    public CurrentStepTransform(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CurrentStepTransform(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void transCurrentStep(int stptr) {
        START_STEP = NOUVELLE_VALEUR;
        //stepTruePosition = stptr;
    }

    @Override
    public int getCurrentStep() {
        return currentStep;
    }
}
