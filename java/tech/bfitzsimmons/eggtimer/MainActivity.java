package tech.bfitzsimmons.eggtimer;

import java.util.concurrent.TimeUnit;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //initialize boolean to see if running or stopped
    boolean isRunning = false;

    //Get time TextView
    TextView time;

    //Declare MediaPlayer
    MediaPlayer mediaPlayer;

    //Declare SeekBar
    SeekBar seekBar;

    //get button
    TextView button;

    //Declare CountdownTimer
    CountDownTimer mainTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize button
        button = (TextView) findViewById(R.id.button);

        //Initialize time textview
        time = (TextView) findViewById(R.id.time);

        //Set up audio file
        mediaPlayer = MediaPlayer.create(this, R.raw.airhorn);

        //Get seekbar
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(600000);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                generateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void formatTime(long i){
        String timeLeft = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(i),
                TimeUnit.MILLISECONDS.toSeconds(i) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(i))
        );
        time.setText(timeLeft);
    }

    public void generateTimer(int duration){
        formatTime(duration);

        mainTimer = new CountDownTimer(duration, 1000){
            public void onTick(long i){
                formatTime(i);
            }

            public void onFinish(){
                isRunning = false;
                generateTimer(30000);
                button.setText("Start");
                mediaPlayer.start();
            }
        };
    }
    public void click(View view){
        if(!isRunning){
            mainTimer.start();
            isRunning = true;
            button.setText("Stop");
            seekBar.setVisibility(View.INVISIBLE);
        } else {
            mainTimer.cancel();
            isRunning = false;
            button.setText("Start");
            generateTimer(30000);
            seekBar.setVisibility(View.VISIBLE);
        }
    }
}
