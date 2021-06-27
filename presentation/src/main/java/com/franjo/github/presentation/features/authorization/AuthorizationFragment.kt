package com.franjo.github.presentation.features.authorization

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.franjo.github.domain.shared.REDIRECT_URI_CALLBACK
import com.franjo.github.presentation.BaseFragment
import com.franjo.github.presentation.R
import com.franjo.github.presentation.databinding.FragmentAuthorizationBinding
import javax.inject.Inject

class AuthorizationFragment : BaseFragment<FragmentAuthorizationBinding>() {

  override fun getFragmentView(): Int = R.layout.fragment_authorization

  @Inject
  lateinit var viewModel: AuthorizationViewModel

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    viewModel.startAuthorizationProcess()
    viewModel.state.observe(viewLifecycleOwner, {
      renderView(it)
    })
  }

  private fun renderView(state: AuthorizationState?) {
    when (state) {
      is AuthorizationState.GetAccessCode -> renderAccessCodeView(state)
      is AuthorizationState.Loading -> renderProgressView()
      is AuthorizationState.Error -> renderErrorView()
      is AuthorizationState.Success -> Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()

    }
  }

  private fun renderErrorView() {
    binding.progressBar.hide()
    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
  }

  private fun renderProgressView() {
    binding.webView.visibility = View.GONE
    binding.progressBar.show()
  }

  @SuppressLint("SetJavaScriptEnabled")
  private fun renderAccessCodeView(state: AuthorizationState.GetAccessCode) {
    binding.webView.visibility = View.VISIBLE
    binding.progressBar.hide()
    binding.webView.apply {
      with(settings) {
        javaScriptEnabled = true
        webViewClient = object : WebViewClient() {
          override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
          ): Boolean {
            request?.url?.authPage()
            return false
          }
        }
        loadUrl(state.uri.toString())
      }
    }
  }

  private fun Uri.authPage() {
    if (accessCodeExists()) {
      viewModel.authorize(this)
    }
  }

}

private fun Uri.accessCodeExists() =
  toString().startsWith(REDIRECT_URI_CALLBACK)
    && getQueryParameter(ACCESS_CODE) != null



