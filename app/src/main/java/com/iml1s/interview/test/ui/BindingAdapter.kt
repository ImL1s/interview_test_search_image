package com.iml1s.interview.test.ui

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iml1s.interview.test.R
import com.iml1s.interview.test.model.Hits
import com.mancj.materialsearchbar.MaterialSearchBar
import timber.log.Timber


// region [MaterialSearchBar]
@BindingAdapter("text")
fun setMaterialSearchBarText(materialSearchBar: MaterialSearchBar, text: String) {
    if (materialSearchBar.text == text) return
    materialSearchBar.text = text
}

@InverseBindingAdapter(attribute = "text")
fun getMaterialSearchBarText(materialSearchBar: MaterialSearchBar): String = materialSearchBar.text

@BindingAdapter("textAttrChanged")
fun setMaterialSearchBarTextChangeListener(
    materialSearchBar: MaterialSearchBar,
    listener: InverseBindingListener
) {
    materialSearchBar.addTextChangeListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // do nothing
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            listener.onChange()
        }

        override fun afterTextChanged(s: Editable?) {
            // do nothing
        }

    })
}
// endregion

// region [ImageView]
@BindingAdapter("sourceFromUrl")
fun setImageViewSourceFromUrl(view: ImageView, imageUrl: String?) {
    Timber.v("setImageViewSourceFromUrl: imageUrl = %s", imageUrl)
    if (imageUrl.isNullOrEmpty()) {
        view.background = null
        return
    }
    Glide.with(view.context)
        .load(imageUrl)
        .placeholder(R.mipmap.ic_loading)
        .into(view)
}
// endregion

// region []
@BindingAdapter("focuse")
fun setMaterialSearchBarFocuse(materialSearchBar: MaterialSearchBar, focuse: Boolean) {
//    materialSearchBar.searchEditText.focu
    // do nothing
}

@InverseBindingAdapter(attribute = "focuse")
fun getMaterialSearchBarFocuse(materialSearchBar: MaterialSearchBar): Boolean =
    materialSearchBar.searchEditText.hasFocus()

@BindingAdapter("focuseAttrChanged")
fun setMaterialSearchBarFocuseChangeListener(
    materialSearchBar: MaterialSearchBar,
    listener: InverseBindingListener
) {
    materialSearchBar.searchEditText.onFocusChangeListener =
        View.OnFocusChangeListener { _, _ ->
            listener.onChange()
        }
}
// endregion

// region [RecyclerView]
@BindingAdapter("source")
fun setRecyclerViewData(recyclerView: RecyclerView, items: List<*>?) {
    Timber.v("setRecyclerViewData: items = %s", items)
    items ?: return
    with(recyclerView.adapter) {
        when (this) {
            is SearchResultAdapter -> submitList(items.filterIsInstance<Hits>())
        }
    }
}
// endregion

// region [common view]
@BindingAdapter("visibility")
fun setVisible(view: View, show: Boolean?) {
    Timber.v("showHide show = %s", show)
    view.visibility = if (show == true) View.VISIBLE else View.GONE
}
// endregion
