package com.mobitill.mobitill_2.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.apps.AppsActivity;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements LoginContract.View {

    private LoginContract.Presenter mPresenter;

    @BindView(R.id.password) EditText mPasswordEditText;
    @BindView(R.id.username) EditText mUsernameEditText;
    @BindView(R.id.login_button) Button mLoginButton;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;

    // Resources
    @BindString(R.string.login_error) String mLoginError;
    @BindString(R.string.empty_username) String mEmptyUsername;
    @BindString(R.string.empty_password) String mEmptyPassword;

    private Unbinder mUnbinder;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }



    @OnClick(R.id.login_button)
    public void login(Button button){
        mPresenter.login(mUsernameEditText.getText().toString(), mPasswordEditText.getText().toString());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.automaticallyLogin();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void showProgress(boolean show) {
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showEmptyUsernameError() {
       Snackbar.make(mUsernameEditText, mEmptyUsername, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showEmptyPasswordError() {
        Snackbar.make(mPasswordEditText, mEmptyPassword, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showLoginFailError() {
        Toast.makeText(getActivity(), mLoginError, Toast.LENGTH_LONG).show();;
    }

    @Override
    public void showTaskActivity() {
        Intent intent = new Intent(getContext(), AppsActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void setTitle() {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(@NonNull LoginContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }


}
