package com.luseen.autolinklibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Chatikyan on 25.09.2016-18:53.
 */

public final class AutoLinkTextView extends TextView {

    static final String TAG = AutoLinkTextView.class.getSimpleName();

    private static final int MIN_PHONE_NUMBER_LENGTH = 8;

    private static final int DEFAULT_COLOR = Color.RED;

    private AutoLinkOnClickListener autoLinkOnClickListener;

    private AutoLinkMode[] autoLinkModes;

    private String customRegex;

    private int mentionModeColor = DEFAULT_COLOR;
    private int hashtagModeColor = DEFAULT_COLOR;
    private int urlModeColor = DEFAULT_COLOR;
    private int phoneModeColor = DEFAULT_COLOR;
    private int emailModeColor = DEFAULT_COLOR;
    private int customModeColor = DEFAULT_COLOR;
    private int defaultSelectedColor = Color.LTGRAY;

    public AutoLinkTextView(Context context) {
        this(context, null);
    }

    public AutoLinkTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoLinkTextView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AutoLinkTextView,
                defStyleAttr, 0);

        final int autoLinkModeFlags =
                ta.getInt(R.styleable.AutoLinkTextView_autolinklibrary__auto_link_modes,
                        AutoLinkMode.MODE_HASHTAG.getBitFlag());
        final List<AutoLinkMode> selectedAutoLinkModes =
                processSelectedXmlAutoLinkModes(autoLinkModeFlags);
        if(!selectedAutoLinkModes.isEmpty()){
            addAutoLinkMode(selectedAutoLinkModes
                    .toArray(new AutoLinkMode[selectedAutoLinkModes.size()]));
        }
        final String resCustomRegex =
                ta.getString(R.styleable.AutoLinkTextView_autolinklibrary__custom_regex);
        if(resCustomRegex != null && resCustomRegex.length() > 0){
            setCustomRegex(resCustomRegex);
        }

        //Extract colors for each modes
        final int resCustomModeColor =
                ta.getColor(R.styleable.AutoLinkTextView_autolinklibrary__custom_mode_color,
                        DEFAULT_COLOR);
        setCustomModeColor(resCustomModeColor);
        final int resHashtagModeColor =
                ta.getColor(R.styleable.AutoLinkTextView_autolinklibrary__hashtag_mode_color,
                        DEFAULT_COLOR);
        setHashtagModeColor(resHashtagModeColor);
        final int resMentionModeColor =
                ta.getColor(R.styleable.AutoLinkTextView_autolinklibrary__mention_mode_color,
                        DEFAULT_COLOR);
        setMentionModeColor(resMentionModeColor);
        final int resUrlModeColor =
                ta.getColor(R.styleable.AutoLinkTextView_autolinklibrary__url_mode_color,
                        DEFAULT_COLOR);
        setUrlModeColor(resUrlModeColor);
        final int resPhoneModeColor =
                ta.getColor(R.styleable.AutoLinkTextView_autolinklibrary__phone_mode_color,
                        DEFAULT_COLOR);
        setPhoneModeColor(resPhoneModeColor);
        final int resEmailModeColor =
                ta.getColor(R.styleable.AutoLinkTextView_autolinklibrary__email_mode_color,
                        DEFAULT_COLOR);
        setEmailModeColor(resEmailModeColor);
        final int resSelectedStateColor =
                ta.getColor(R.styleable.AutoLinkTextView_autolinklibrary__selected_state_color,
                        DEFAULT_COLOR);
        setSelectedStateColor(resSelectedStateColor);
        final String resAutoLinkText = ta.getString(R.styleable.AutoLinkTextView_android_text);
        if(resAutoLinkText != null && resAutoLinkText.length() > 0){
            setAutoLinkText(resAutoLinkText);
        }
        ta.recycle();
    }

    private List<AutoLinkMode> processSelectedXmlAutoLinkModes(final int autoLinkModeValues){
        EnumSet<AutoLinkMode> autoLinkModes = EnumSet.noneOf(AutoLinkMode.class);
        final List<AutoLinkMode> result = new ArrayList<>();
        for(AutoLinkMode autoLinkMode : autoLinkModes){
            final int bitFlag = autoLinkMode.getBitFlag();
            if((autoLinkModeValues & bitFlag) == bitFlag){
                result.add(autoLinkMode);
            }
        }
        return result;
    }

    @Override
    public void setHighlightColor(int color) {
        super.setHighlightColor(Color.TRANSPARENT);
    }

    public void setAutoLinkText(String text) {
        SpannableString spannableString = makeSpannableString(text);
        setText(spannableString);
        setMovementMethod(new LinkTouchMovementMethod());
    }

    private SpannableString makeSpannableString(String text) {

        final SpannableString spannableString = new SpannableString(text);

        List<AutoLinkItem> autoLinkItems = matchedRanges(text);

        for (final AutoLinkItem autoLinkItem : autoLinkItems) {
            int currentColor = getColorByMode(autoLinkItem.getAutoLinkMode());

            TouchableSpan clickableSpan = new TouchableSpan(currentColor, defaultSelectedColor) {
                @Override
                public void onClick(View widget) {
                    if (autoLinkOnClickListener != null)
                        autoLinkOnClickListener.onAutoLinkTextClick(
                                autoLinkItem.getAutoLinkMode(),
                                autoLinkItem.getMatchedText());
                }
            };

            spannableString.setSpan(
                    clickableSpan,
                    autoLinkItem.getStartPoint(),
                    autoLinkItem.getEndPoint(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return spannableString;
    }

    private List<AutoLinkItem> matchedRanges(String text) {

        List<AutoLinkItem> autoLinkItems = new LinkedList<>();

        if (autoLinkModes == null) {
            throw new NullPointerException("Please add at least one mode");
        }

        for (AutoLinkMode anAutoLinkMode : autoLinkModes) {
            String regex = Utils.getRegexByAutoLinkMode(anAutoLinkMode, customRegex);
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);

            if (anAutoLinkMode == AutoLinkMode.MODE_PHONE) {
                while (matcher.find()) {
                    if (matcher.group().length() > MIN_PHONE_NUMBER_LENGTH)
                        autoLinkItems.add(new AutoLinkItem(
                                matcher.start(),
                                matcher.end(),
                                matcher.group(),
                                anAutoLinkMode));
                }
            } else {
                while (matcher.find()) {
                    autoLinkItems.add(new AutoLinkItem(
                            matcher.start(),
                            matcher.end(),
                            matcher.group(),
                            anAutoLinkMode));
                }
            }
        }

        return autoLinkItems;
    }


    private int getColorByMode(AutoLinkMode autoLinkMode) {
        switch (autoLinkMode) {
            case MODE_HASHTAG:
                return hashtagModeColor;
            case MODE_MENTION:
                return mentionModeColor;
            case MODE_URL:
                return urlModeColor;
            case MODE_PHONE:
                return phoneModeColor;
            case MODE_EMAIL:
                return emailModeColor;
            case MODE_CUSTOM:
                return customModeColor;
            default:
                return DEFAULT_COLOR;
        }
    }

    public void setMentionModeColor(@ColorInt int mentionModeColor) {
        this.mentionModeColor = mentionModeColor;
    }

    public void setHashtagModeColor(@ColorInt int hashtagModeColor) {
        this.hashtagModeColor = hashtagModeColor;
    }

    public void setUrlModeColor(@ColorInt int urlModeColor) {
        this.urlModeColor = urlModeColor;
    }

    public void setPhoneModeColor(@ColorInt int phoneModeColor) {
        this.phoneModeColor = phoneModeColor;
    }

    public void setEmailModeColor(@ColorInt int emailModeColor) {
        this.emailModeColor = emailModeColor;
    }

    public void setCustomModeColor(@ColorInt int customModeColor) {
        this.customModeColor = customModeColor;
    }

    public void setSelectedStateColor(@ColorInt int defaultSelectedColor) {
        this.defaultSelectedColor = defaultSelectedColor;
    }

    public void addAutoLinkMode(AutoLinkMode... autoLinkModes) {
        this.autoLinkModes = autoLinkModes;
    }

    public void setCustomRegex(String regex) {
        this.customRegex = regex;
    }

    public void setAutoLinkOnClickListener(AutoLinkOnClickListener autoLinkOnClickListener) {
        this.autoLinkOnClickListener = autoLinkOnClickListener;
    }
}
