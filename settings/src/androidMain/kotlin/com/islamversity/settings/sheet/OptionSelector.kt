package com.islamversity.settings.sheet

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.islamversity.settings.R
import com.islamversity.settings.databinding.SheetOptionBinding

class OptionSelector(context: Context) : BottomSheetDialog(context, R.style.OptionsSelectorTheme) {

    @RecyclerView.Orientation
    private var orientation = RecyclerView.VERTICAL
    private var dismissListener: DismissListener? = null
    private val options = mutableListOf<String>()
    private var defaultPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = SheetOptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window!!.apply {
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }

        binding.epoxyView.apply {
            layoutManager = LinearLayoutManager(context, orientation, false)
            scrollToPosition(defaultPosition)
            withModelsAsync {
                if (orientation == RecyclerView.VERTICAL) {
                    options.forEach {
                        fullWidthOptionView {
                            id(it)
                            item(it)
                            listener {
                                dismissListener?.dismissSheet(options.indexOf(it))
                                dismiss()
                            }
                        }
                    }
                } else {
                    options.forEach {
                        optionView {
                            id(it)
                            item(it)
                            listener {
                                dismissListener?.dismissSheet(options.indexOf(it))
                                dismiss()
                            }
                        }
                    }
                }
            }
        }

    }

    fun options(options: List<String>): OptionSelector {
        this.options.clear()
        this.options.addAll(options)
        return this
    }

    fun dismissListener(dismissListener: DismissListener): OptionSelector {
        this.dismissListener = dismissListener
        return this
    }

    override fun onDetachedFromWindow() {
        dismissListener = null
        super.onDetachedFromWindow()
    }
}