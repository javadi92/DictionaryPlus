package com.javadi.dictionary.Presenter;

import com.javadi.dictionary.App;
import com.javadi.dictionary.View.ITranslateResult;

public class TranslatePresenter implements ITranslatePresenter {

    ITranslateResult iTranslateResult;

    public TranslatePresenter(ITranslateResult iTranslateResult){

        this.iTranslateResult=iTranslateResult;
    }

    @Override
    public void onTranslate(String word) {

        iTranslateResult.onTranslateResult(App.dbHelper.translate(word));
    }
}
