package com.gettipsi.stripe.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class EnterAddressDialogFragment extends DialogFragment {
  public AddCardDialogFragment next = null;

  private PlainAddress data = null;

  private EditText name;
  private EditText address;
  private EditText apartment;
  private EditText zip;
  private EditText city;
  private EditText state;
  private EditText country;
  private EditText phone;
  private EditText email;

  public EnterAddressDialogFragment() {
  }

  public static EnterAddressDialogFragment newInstance(PlainAddress params) {
    EnterAddressDialogFragment fragment = new EnterAddressDialogFragment();
    if (params != null) {
      fragment.data = params;
      Bundle args = new Bundle();
      args.putParcelable("data", params);
      fragment.setArguments(args);
    }
    return fragment;
  }

  public static EnterAddressDialogFragment newInstance() {
    return EnterAddressDialogFragment.newInstance(null);
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final View view = View.inflate(getActivity(), R.layout.address_layout, null);
    bindViews(view);
    if (data != null) {
      initValues(data);
    }
    Activity activity = getActivity();
    AlertDialog dialog = null;
    if (activity != null) {
      dialog = new AlertDialog.Builder(getActivity())
        .setView(view)
        .setTitle("Enter your address")
        .setPositiveButton("Done", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            collectData();
            next.setAddress(JSBridgeUtil.convertPlainAddressToWritableMap(data));
            next.show(getCurrentActivity().getFragmentManager(), "AddNewCard");
          }
        })
        .setNegativeButton(android.R.string.cancel, null).create();
      dialog.show();
    }
    return dialog;
  }

  private void bindViews(final View view) {
    name = (EditText) view.findViewById(R.id.name);
    address = (EditText) view.findViewById(R.id.address);
    apartment = (EditText) view.findViewById(R.id.apartment);
    zip = (EditText) view.findViewById(R.id.zip);
    city = (EditText) view.findViewById(R.id.city);
    state = (EditText) view.findViewById(R.id.state);
    country = (EditText) view.findViewById(R.id.country);
    phone = (EditText) view.findViewById(R.id.phone);
    email = (EditText) view.findViewById(R.id.email);
  }

  private void initValues(PlainAddress data) {
    pushData(name, data.name);
    pushData(address, data.address);
    pushData(apartment, data.apartment);
    pushData(zip, data.zip);
    pushData(city, data.city);
    pushData(state, data.state);
    pushData(country, data.country);
    pushData(phone, data.phone);
    pushData(email, data.email);
  }

  private void collectData() {
    data.name = name.getText().toString();
    data.address = address.getText().toString();
    data.apartment = apartment.getText().toString();
    data.zip = zip.getText().toString();
    data.city = city.getText().toString();
    data.state = state.getText().toString();
    data.country = country.getText().toString();
    data.phone = phone.getText().toString();
    data.email = email.getText().toString();
  }

  private void pushData(EditText field, String text) {
    if (!TextUtils.isEmpty(text)) {
      field.setText(text);
    }
  }
}
