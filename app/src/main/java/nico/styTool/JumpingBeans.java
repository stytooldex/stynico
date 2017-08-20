package nico.styTool;

import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public final class JumpingBeans {

    private static final String ELLIPSIS_GLYPH = "妮媌动态壁纸";
    private static final String THREE_DOTS_ELLIPSIS = "...";
    private static final int THREE_DOTS_ELLIPSIS_LENGTH = 7;

    private final JumpingBeansSpan[] jumpingBeans;
    private final WeakReference<TextView> textView;

    private JumpingBeans(JumpingBeansSpan[] beans, TextView textView) {
        this.jumpingBeans = beans;
        this.textView = new WeakReference<>(textView);
    }

    /**
     */
    public static Builder with(@NonNull TextView textView) {
        return new Builder(textView);
    }

    /**
     * Stops the jumping animation and frees up the animations.
     */
    public void stopJumping() {
        for (JumpingBeansSpan bean : jumpingBeans) {
            if (bean != null) {
                bean.teardown();
            }
        }

        cleanupSpansFrom(textView.get());
    }

    private static void cleanupSpansFrom(TextView textView) {
        if (textView == null) {
            return;
        }

        CharSequence text = textView.getText();
        if (text instanceof Spanned) {
            CharSequence cleanText = removeJumpingBeansSpansFrom((Spanned) text);
            textView.setText(cleanText);
        }
    }

    private static CharSequence removeJumpingBeansSpansFrom(Spanned text) {
        SpannableStringBuilder sbb = new SpannableStringBuilder(text.toString());
        Object[] spans = text.getSpans(0, text.length(), Object.class);
        for (Object span : spans) {
            if (!(span instanceof JumpingBeansSpan)) {
                sbb.setSpan(span, text.getSpanStart(span), text.getSpanEnd(span), text.getSpanFlags(span));
            }
        }
        return sbb;
    }

    /**
     * @see JumpingBeans#with(TextView)
     */
    public static class Builder {

        private static final float DEFAULT_ANIMATION_DUTY_CYCLE = 0.65f;
        private static final int DEFAULT_LOOP_DURATION = 1300;   // ms
        private static final int DEFAULT_WAVE_CHAR_DELAY = -1;

        private final TextView textView;

        private int startPos;
        private int endPos;

        private float animRange = DEFAULT_ANIMATION_DUTY_CYCLE;
        private int loopDuration = DEFAULT_LOOP_DURATION;
        private int waveCharDelay = DEFAULT_WAVE_CHAR_DELAY;
        private CharSequence text;
        private boolean wave;

        Builder(TextView textView) {
            this.textView = textView;
        }

        /**
         *
         * @see #setIsWave(boolean)
         */
        @NonNull
        public Builder appendJumpingDots() {
            CharSequence text = appendThreeDotsEllipsisTo(textView);

            this.text = text;
            this.wave = true;
            this.startPos = text.length() - THREE_DOTS_ELLIPSIS_LENGTH;
            this.endPos = text.length();

            return this;
        }

        private static CharSequence appendThreeDotsEllipsisTo(TextView textView) {
            CharSequence text = getTextSafe(textView);
            if (text.length() > 0 && endsWithEllipsisGlyph(text)) {
                text = text.subSequence(0, text.length() - 1);
            }

            if (!endsWithThreeEllipsisDots(text)) {
                text = new SpannableStringBuilder(text).append(THREE_DOTS_ELLIPSIS);  // Preserve spans in original text
            }
            return text;
        }

        private static CharSequence getTextSafe(TextView textView) {
            return !TextUtils.isEmpty(textView.getText()) ? textView.getText() : "";
        }

        private static boolean endsWithEllipsisGlyph(CharSequence text) {
            CharSequence lastChar = text.subSequence(text.length() - 1, text.length());
            return ELLIPSIS_GLYPH.equals(lastChar);
        }

        @SuppressWarnings("SimplifiableIfStatement")                 // For readability
        private static boolean endsWithThreeEllipsisDots(CharSequence text) {
            if (text.length() < THREE_DOTS_ELLIPSIS_LENGTH) {
                // TODO we should try to normalize "invalid" ellipsis (e.g., ".." or "....")
                return false;
            }
            CharSequence lastThreeChars = text.subSequence(text.length() - THREE_DOTS_ELLIPSIS_LENGTH, text.length());
            return THREE_DOTS_ELLIPSIS.equals(lastThreeChars);
        }

        /**
         */
        @NonNull
        public Builder makeTextJump(@IntRange(from = 0) int startPos, @IntRange(from = 0) int endPos) {
            CharSequence text = textView.getText();
            ensureTextCanJump(startPos, endPos, text);

            this.text = text;
            this.wave = true;
            this.startPos = startPos;
            this.endPos = endPos;

            return this;
        }

        private static CharSequence ensureTextCanJump(int startPos, int endPos, CharSequence text) {
            if (text == null) {
                throw new NullPointerException("The textView text must not be null");
            }

            if (endPos < startPos) {
                throw new IllegalArgumentException("The start position must be smaller than the end position");
            }

            if (startPos < 0) {
                throw new IndexOutOfBoundsException("The start position must be non-negative");
            }

            if (endPos > text.length()) {
                throw new IndexOutOfBoundsException("The end position must be smaller than the text length");
            }
            return text;
        }

        /**
         * Sets the fraction of the animation loop time spent actually animating.
         * The rest of the time will be spent "resting".
         *
         * @param animatedRange The fraction of the animation loop time spent
         *                      actually animating the characters
         */
        @NonNull
        public Builder setAnimatedDutyCycle(@FloatRange(from = 0f, to = 1f, fromInclusive = false) float animatedRange) {
            if (animatedRange <= 0f || animatedRange > 1f) {
                throw new IllegalArgumentException("The animated range must be in the (0, 1] range");
            }
            this.animRange = animatedRange;
            return this;
        }

        /**
         * Sets the jumping loop duration.
         *
         * @param loopDuration The jumping animation loop duration, in milliseconds
         */
        @NonNull
        public Builder setLoopDuration(@IntRange(from = 1) int loopDuration) {
            if (loopDuration < 1) {
                throw new IllegalArgumentException("The loop duration must be bigger than zero");
            }
            this.loopDuration = loopDuration;
            return this;
        }

        /**
         * Sets the delay for starting the animation of every single dot over the
         * start of the previous one, in milliseconds. The default value is
         * the loop length divided by three times the number of character animated
         * by this instance of JumpingBeans.
         * <p/>
         * Only has a meaning when the animation is a wave.
         *
         * @param waveCharOffset The start delay for the animation of every single
         *                       character over the previous one, in milliseconds
         * @see #setIsWave(boolean)
         */
        @NonNull
        public Builder setWavePerCharDelay(@IntRange(from = 0) int waveCharOffset) {
            if (waveCharOffset < 0) {
                throw new IllegalArgumentException("The wave char offset must be non-negative");
            }
            this.waveCharDelay = waveCharOffset;
            return this;
        }

        /**
         * Sets a flag that determines if the characters will jump in a wave
         * (i.e., with a delay between each other) or all at the same
         * time.
         *
         * @param wave If true, the animation is going to be a wave; if
         *             false, all characters will jump ay the same time
         * @see #setWavePerCharDelay(int)
         */
        @NonNull
        public Builder setIsWave(boolean wave) {
            this.wave = wave;
            return this;
        }

        /**

         */
        @NonNull
        public JumpingBeans build() {
            SpannableStringBuilder sbb = new SpannableStringBuilder(text);
            JumpingBeansSpan[] spans;
            if (wave) {
                spans = buildWavingSpans(sbb);
            } else {
                spans = buildSingleSpan(sbb);
            }

            textView.setText(sbb);
            return new JumpingBeans(spans, textView);
        }

        @SuppressWarnings("Range")          // Lint bug: the if makes sure waveCharDelay >= 0
        private JumpingBeansSpan[] buildWavingSpans(SpannableStringBuilder sbb) {
            JumpingBeansSpan[] spans;
            if (waveCharDelay == DEFAULT_WAVE_CHAR_DELAY) {
                waveCharDelay = loopDuration / (3 * (endPos - startPos));
            }

            spans = new JumpingBeansSpan[endPos - startPos];
            for (int pos = startPos; pos < endPos; pos++) {
                JumpingBeansSpan jumpingBean =
                        new JumpingBeansSpan(textView, loopDuration, pos - startPos, waveCharDelay, animRange);
                sbb.setSpan(jumpingBean, pos, pos + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spans[pos - startPos] = jumpingBean;
            }
            return spans;
        }

        private JumpingBeansSpan[] buildSingleSpan(SpannableStringBuilder sbb) {
            JumpingBeansSpan[] spans;
            spans = new JumpingBeansSpan[]{new JumpingBeansSpan(textView, loopDuration, 0, 0, animRange)};
            sbb.setSpan(spans[0], startPos, endPos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spans;
        }

    }

}
