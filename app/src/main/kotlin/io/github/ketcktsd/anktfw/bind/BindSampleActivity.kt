package io.github.ketcktsd.anktfw.bind

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import io.github.ketcktsd.anktfw.bind.container.BindableDelegates
import io.github.ketcktsd.anktfw.bind.container.bindable
import org.jetbrains.anko.setContentView

class BindSampleActivity : AppCompatActivity(),
        IBindSampleUI by BindSampleUI() {

    private val mTextBindable =
            bindable({ textView.text = it }, { textView.text })

    private val mLengthTextBindable =
            bindable({ lengthTextView.text = "${it.length}" }, { lengthTextView.text })

    private var mText: CharSequence by BindableDelegates
            .readWrite("", mTextBindable, mLengthTextBindable)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(this)
        setSupportActionBar(toolbar)

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                mText = s
            }
        })
    }
}
