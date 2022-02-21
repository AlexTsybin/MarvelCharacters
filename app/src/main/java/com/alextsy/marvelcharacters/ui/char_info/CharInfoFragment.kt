package com.alextsy.marvelcharacters.ui.char_info

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.alextsy.marvelcharacters.R
import com.alextsy.marvelcharacters.databinding.FragmentCharInfoBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharInfoFragment : Fragment(R.layout.fragment_char_info) {

    private val args by navArgs<CharInfoFragmentArgs>()
    private val viewModel by viewModels<CharInfoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentCharInfoBinding.bind(view)

        binding.apply {
            val charInfo = args.result

            Glide.with(this@CharInfoFragment)
                .load("${charInfo.thumbnail.path}/standard_xlarge.${charInfo.thumbnail.extension}")
                .listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        tvDetailsUrl.isVisible = true
                        tvInfoName.isVisible = true
                        tvInfoName.text = charInfo.name
                        tvInfoDescr.isVisible = true
                        tvInfoDescr.text = charInfo.description
                        return false
                    }
                })
                .into(ivInfoImage)

            val uri = Uri.parse(charInfo.urls[0].url)
            val intent = Intent(Intent.ACTION_VIEW, uri)

            tvDetailsUrl.apply {
                charInfo.urls.isNotEmpty()
                text = if (charInfo.urls.isNotEmpty()) charInfo.urls[0].url else ""
                setOnClickListener {
                    context.startActivity(intent)
                }
                paint.isUnderlineText = true
            }
        }
    }
}