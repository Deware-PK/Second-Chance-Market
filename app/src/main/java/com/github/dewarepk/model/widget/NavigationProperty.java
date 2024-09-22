package com.github.dewarepk.model.widget;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NavigationProperty {

    private LinearLayout homeButton;
    private LinearLayout cartButton;
    private LinearLayout profileButton;

    private ImageView homeImage;
    private ImageView cartImage;
    private ImageView profileImage;

    private TextView homeText;
    private TextView cartText;
    private TextView profileText;

    public LinearLayout getHomeButton() {
        return homeButton;
    }

    public NavigationProperty setHomeButton(LinearLayout homeButton) {
        this.homeButton = homeButton;
        return this;
    }

    public LinearLayout getCartButton() {
        return cartButton;
    }

    public NavigationProperty setCartButton(LinearLayout cartButton) {
        this.cartButton = cartButton;
        return this;
    }

    public LinearLayout getProfileButton() {
        return profileButton;
    }

    public NavigationProperty setProfileButton(LinearLayout profileButton) {
        this.profileButton = profileButton;
        return this;
    }

    public ImageView getHomeImage() {
        return homeImage;
    }

    public NavigationProperty setHomeImage(ImageView homeImage) {
        this.homeImage = homeImage;
        return this;
    }

    public ImageView getCartImage() {
        return cartImage;
    }

    public NavigationProperty setCartImage(ImageView cartImage) {
        this.cartImage = cartImage;
        return this;
    }

    public ImageView getProfileImage() {
        return profileImage;
    }

    public NavigationProperty setProfileImage(ImageView profileImage) {
        this.profileImage = profileImage;
        return this;
    }

    public TextView getHomeText() {
        return homeText;
    }

    public NavigationProperty setHomeText(TextView homeText) {
        this.homeText = homeText;
        return this;
    }

    public TextView getCartText() {
        return cartText;
    }

    public NavigationProperty setCartText(TextView cartText) {
        this.cartText = cartText;
        return this;
    }

    public TextView getProfileText() {
        return profileText;
    }

    public NavigationProperty setProfileText(TextView profileText) {
        this.profileText = profileText;
        return this;
    }
}
