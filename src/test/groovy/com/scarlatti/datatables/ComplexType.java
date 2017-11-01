package com.scarlatti.datatables;

import java.awt.*;

/**
 * ~     _____                                    __
 * ~    (, /  |  /)                /)         (__/  )      /)        ,
 * ~      /---| // _ _  _  _ __  _(/ __ ___     / _ _  __ // _ _/_/_
 * ~   ) /    |(/_(//_)/_)(_(/ ((_(_/ ((_)   ) / (_(_(/ ((/_(_((_(__(_
 * ~  (_/                                   (_/
 * ~  Wednesday, 10/25/2017
 */
class ComplexType {
    String name;
    String nickname;
    int age;
    boolean alive;
    Color hairColor;
    int heightInFeet;

    private ComplexType(Builder builder) {
        this.name = builder.name;
        this.nickname = builder.nickname;
        this.age = builder.age;
        this.alive = builder.alive;
        this.hairColor = builder.hairColor;
        this.heightInFeet = builder.heightInFeet;
    }

    // silly method for testing
    public int getInteger(int i) {
        return i;
    }

    public static Builder newComplexType2() {
        return new Builder();
    }


    public static final class Builder {
        private String name;
        private String nickname;
        private int age;
        private boolean alive;
        private Color hairColor;
        private int heightInFeet;

        private Builder() {
        }

        public ComplexType build() {
            return new ComplexType(this);
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder alive(boolean alive) {
            this.alive = alive;
            return this;
        }

        public Builder hairColor(Color hairColor) {
            this.hairColor = hairColor;
            return this;
        }

        public Builder heightInFeet(int heightInFeet) {
            this.heightInFeet = heightInFeet;
            return this;
        }
    }
}
