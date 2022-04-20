package com.example.diceroller;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ImageView image1_btn, image2_btn;
    Button next_btn;
    TextView d1_txt;
    TextView d2_txt;
    TextView total_txt;
    TextToSpeech textToSpeech;
    int z, s;
    int sound1;

    int sound_shake, sound_dice, number_dice;

    int[] image1 = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};
    int i1 = 0;
    int[] image2 = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};
    int i2 = 0;
    public static final Random RANDOM = new Random();
    SoundPool dice_sounds = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding();
        setImage1(i1);
        setImage2(i2);


        // Dice Sounds
        sound1 = dice_sounds.load(this, R.raw.sound, 1);


        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Random rand = new Random();
                int a = rand.nextInt(image1.length);
                Random rand1 = new Random();
                int b = rand1.nextInt(image2.length);
                image1_btn.setImageResource(image1[a]);
                image2_btn.setImageResource(image2[b]);

                /*Toast.makeText(MainActivity.this, "" + (a + 1) + "," + (b + 1), Toast.LENGTH_LONG).show();*/
                d1_txt.setText("" + (a + 1));
                d2_txt.setText("" + (b + 1));
                z = Integer.parseInt(String.valueOf(a));
                s = Integer.parseInt(String.valueOf(b));
                Vibrator vibe = (Vibrator) getSystemService(MainActivity.VIBRATOR_SERVICE);
                vibe.vibrate(100);
                int x = (z + 1) + (s + 1);
                total_txt.setText("" + x);
                switch (z) {
                    case 1:
                        image1_btn.setImageResource(R.drawable.dice1);
                        number_dice = R.drawable.dice1;

                        sound_dice = sound1;
                        break;
                    case 2:
                        image1_btn.setImageResource(R.drawable.dice2);
                        number_dice = R.drawable.dice2;
                        sound_dice = sound1;
                        break;
                    case 3:
                        image1_btn.setImageResource(R.drawable.dice3);
                        number_dice = R.drawable.dice3;

                        sound_dice = sound1;
                        break;
                    case 4:
                        image1_btn.setImageResource(R.drawable.dice4);
                        number_dice = R.drawable.dice4;

                        sound_dice = sound1;
                        break;
                    case 5:
                        image1_btn.setImageResource(R.drawable.dice5);
                        number_dice = R.drawable.dice5;

                        sound_dice = sound1;
                        break;
                    case 6:
                        image1_btn.setImageResource(R.drawable.dice6);
                        number_dice = R.drawable.dice6;

                        sound_dice = sound1;
                        break;
                    default:
                }

                // Animation
                final Animation anim1 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
                final Animation anim2 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
                final Animation.AnimationListener animationListener = new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        dice_sounds.play(sound_dice, 1.0f, 1.0f, 0, 0, 1.0f);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        int value = randomDiceValue();
                        int res = getResources().getIdentifier("dice_" + value, "drawable", "com.ssaurel.dicer");


                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                };

                anim1.setAnimationListener(animationListener);
                anim2.setAnimationListener(animationListener);

                image1_btn.startAnimation(anim1);
                image2_btn.startAnimation(anim2);
                textToSpeech.speak(total_txt.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);


            }
        });

        // Speech

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {


                if (i != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });


    }

    void binding() {
        image1_btn = findViewById(R.id.image1_btn);
        image2_btn = findViewById(R.id.image2_btn);
        next_btn = findViewById(R.id.next_btn);
        d1_txt = findViewById(R.id.d1_txt);
        d2_txt = findViewById(R.id.d2_txt);
        total_txt = findViewById(R.id.total_txt);
    }

    void setImage1(int z) {
        Glide.with(MainActivity.this)
                .load(image1[z])
                .placeholder(R.drawable.ic_launcher_background)
                .into(image1_btn);


    }

    void setImage2(int s) {
        Glide.with(MainActivity.this)
                .load(image2[s])
                .placeholder(R.drawable.ic_launcher_background)
                .into(image2_btn);


    }

    public static int randomDiceValue() {
        return RANDOM.nextInt(6) + 1;
    }


}