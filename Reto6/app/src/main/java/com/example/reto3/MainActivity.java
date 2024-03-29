package com.example.reto3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.media.MediaPlayer;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.MenuInflater;
import android.content.Intent;
import android.widget.Toast;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    static final int DIALOG_RESTART_SETTINGS = 1;
    static final int DIALOG_QUIT_ID = 2;

    // Represents the internal state of the game
    private TicTacToeGame mGame;
    private boolean mGameOver;

    // Various text displayed
    private TextView mInfoTextView;
    private TextView mCountHumanTextView;
    private TextView mCountTiesTextView;
    private TextView mCountAndroidTextView;

    // Declare BoardView class
    private BoardView mBoardView;

    // Declare Media Player
    private MediaPlayer mHumanMediaPlayer;
    private MediaPlayer mComputerMediaPlayer;

    private boolean turn;
    private boolean mSoundOn;

    private SharedPreferences mPrefs;

    // Declare OnTouchListener class
    private View.OnTouchListener mTouchListener = new View.OnTouchListener( ){

        public boolean onTouch( View v, MotionEvent event ){

            if( !turn )
                return false;

            // Determine which cell was touched
            int col = (int) event.getX( ) / mBoardView.getBoardCellWidth( );
            int row = (int) event.getY( ) / mBoardView.getBoardCellHeight( );
            int pos = row * 3 + col;

            if( !mGameOver && setMove( TicTacToeGame.HUMAN_PLAYER, pos ) ){


                // If no winner yet, let the computer make a move
                if( mSoundOn ) {
                    mHumanMediaPlayer.start( );
                }

                int winner = mGame.checkForWinner( );
                if( winner == 0 ){
                    turn = false;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            mComputerMove( );
                            mBoardView.invalidate( );
                            winner();
                        }
                    }, 1000);
                }else{
                    winner();
                }
            }

            // So we aren't notified of continued events when finger is moved
            return false;
        }
    };

    // Set up the game board.
    private void startNewGame( ){

        mGame.clearBoard();
        mGameOver = false;
        turn = true;

        mBoardView.invalidate( );

        // Human goes first
        mInfoTextView.setText("You go first.");
        mCountHumanTextView.setText( mGame.getWins( 0 ).toString( ) );
        mCountTiesTextView.setText( mGame.getWins( 1 ).toString( ) );
        mCountAndroidTextView.setText( mGame.getWins( 2 ).toString( ) );

    }    // End of startNewGame

    private boolean setMove( char player, int location ){
        if( mGame.setMove( player, location ) ){
            mBoardView.setGame( mGame );
            mBoardView.invalidate( );
            return true;
        }
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGame = new TicTacToeGame();
        mBoardView = findViewById(R.id.board);
        mBoardView.setGame(mGame);

        mBoardView.setOnTouchListener( mTouchListener );

        mInfoTextView = (TextView) findViewById(R.id.information);
        mCountHumanTextView = (TextView) findViewById(R.id.countHuman);
        mCountTiesTextView = (TextView) findViewById(R.id.countTies);
        mCountAndroidTextView = (TextView) findViewById(R.id.countAndroid);


        // Restore the scores from the persistent preference data source
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        mBoardView.setColor( mPrefs.getInt( "color_board", 0xFFCCCCCC ) );

        mSoundOn = mPrefs.getBoolean("sound", true);
        String difficultyLevel = mPrefs.getString("difficulty_level",
                getResources().getString(R.string.difficulty_harder));
        if (difficultyLevel.equals(getResources().getString(R.string.difficulty_easy)))
            mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Easy);
        else if (difficultyLevel.equals(getResources().getString(R.string.difficulty_harder)))
            mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Harder);
        else
            mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Expert);


        mPrefs = getSharedPreferences("ttt_prefs", MODE_PRIVATE);

        if ( savedInstanceState == null ){
            // Restore the scores
            mGame.setWins( 0, mPrefs.getInt("mHumanWins", 0) );
            mGame.setWins( 1, mPrefs.getInt("mTies", 0) );
            mGame.setWins( 2, mPrefs.getInt("mComputerWins", 0) );
//            mGame.setDifficultyLevel( TicTacToeGame.DifficultyLevel.values( )[mPrefs.getInt("difficultyLevel", 2)] );
            startNewGame( );
        }else{
            // Restore the game's state
            mGame.setBoardState( savedInstanceState.getCharArray( "board" ) );
            mGameOver = savedInstanceState.getBoolean( "mGameOver" );
            mInfoTextView.setText( savedInstanceState.getCharSequence( "info" ) );
            mGame.setWins( 0, Integer.valueOf( savedInstanceState.getInt( "mHumanWins" ) ) );
            mGame.setWins( 1, Integer.valueOf( savedInstanceState.getInt( "mTies" ) ) );
            mGame.setWins( 2, Integer.valueOf( savedInstanceState.getInt( "mComputerWins" ) ) );
            turn = savedInstanceState.getBoolean( "mGoFirst" );
            mBoardView.setGame( mGame );
            mBoardView.invalidate( );
        }
        displayScores( );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_game:
                startNewGame();
                return true;
            case R.id.settings:
                startActivityForResult(new Intent(this, Settings.class), 0);
                return true;
            case R.id.restart_settings:
                showDialog(DIALOG_RESTART_SETTINGS);
                return true;
            case R.id.quit:
                showDialog(DIALOG_QUIT_ID);
                return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RESULT_CANCELED) {
            // Apply potentially new settings

            mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

            mSoundOn = mPrefs.getBoolean( "sound", true );

            String difficultyLevel = mPrefs.getString("difficulty_level",
                    getResources().getString(R.string.difficulty_harder));

            if (difficultyLevel.equals(getResources().getString(R.string.difficulty_easy)))
                mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Easy);
            else if (difficultyLevel.equals(getResources().getString(R.string.difficulty_harder)))
                mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Harder);
            else
                mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Expert);

            mBoardView.setColor( mPrefs.getInt( "color_board", 0xFFCCCCCC ) );
        }
    }


    @Override
    protected Dialog onCreateDialog( int id ){
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder( this );

        switch( id ){
            case DIALOG_RESTART_SETTINGS:
                SharedPreferences.Editor ed = mPrefs.edit( );
                ed.putInt( "mHumanWins", 0 );
                ed.putInt( "mTies", 0 );
                ed.putInt( "mComputerWins", 0 );
                ed.putInt( "difficultyLevel", 2 );
                ed.commit( );
                mGame.setWins( 0, mPrefs.getInt("mHumanWins", 0) );
                mGame.setWins( 1, mPrefs.getInt("mTies", 0) );
                mGame.setWins( 2, mPrefs.getInt("mComputerWins", 0) );
                mGame.setDifficultyLevel( TicTacToeGame.DifficultyLevel.values( )[mPrefs.getInt("difficultyLevel", 2)] );
                startNewGame( );
                break;
            case DIALOG_QUIT_ID:
                // Create the quit confirmation dialog

                builder.setMessage(R.string.quit_question)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                MainActivity.this.finish();
                            }
                        })
                        .setNegativeButton(R.string.no, null);
                dialog = builder.create();

                break;
        }

        return dialog;
    }

    @Override
    protected void onResume( ){
        super.onResume( );
        mHumanMediaPlayer = MediaPlayer.create( getApplicationContext( ), R.raw.human );
        mComputerMediaPlayer = MediaPlayer.create( getApplicationContext( ), R.raw.android );
    }

    @Override
    protected void onPause( ){
        super.onPause( );

        mHumanMediaPlayer.release( );
        mComputerMediaPlayer.release( );
    }

    @Override
    protected void onSaveInstanceState( Bundle outState ){
        super.onSaveInstanceState( outState );

        outState.putCharArray( "board", mGame.getBoardState( ) );
        outState.putBoolean( "mGameOver", mGameOver );
        outState.putInt( "mHumanWins", mGame.getWins( 0 ) );
        outState.putInt( "mComputerWins", mGame.getWins( 2 ) );
        outState.putInt( "mTies", mGame.getWins( 1 ) );
        outState.putCharSequence( "info", mInfoTextView.getText( ) );
        outState.putBoolean( "mGoFirst", turn );
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ){
        super.onRestoreInstanceState( savedInstanceState );

        mGame.setBoardState( savedInstanceState.getCharArray( "board" ) );
        mGameOver = savedInstanceState.getBoolean( "mGameOver" );
        mInfoTextView.setText(savedInstanceState.getCharSequence( "info" ) );
        mCountHumanTextView.setText( mGame.getWins( 0 ).toString( ) );
        mCountTiesTextView.setText( mGame.getWins( 1 ).toString( ) );
        mCountAndroidTextView.setText( mGame.getWins( 2 ).toString( ) );
        mGame.setWins( 0, Integer.valueOf( savedInstanceState.getInt( "mHumanWins" ) ) );
        mGame.setWins( 1, Integer.valueOf( savedInstanceState.getInt( "mTies" ) ) );
        mGame.setWins( 2, Integer.valueOf( savedInstanceState.getInt( "mComputerWins" ) ) );
        turn = savedInstanceState.getBoolean( "mGoFirst" );
    }

    @Override
    protected void onStop( ){
        super.onStop( );

        // Save the current scores
        SharedPreferences.Editor ed = mPrefs.edit( );
        ed.putInt( "mHumanWins", mGame.getWins( 0 ) );
        ed.putInt( "mTies", mGame.getWins( 1 ) );
        ed.putInt( "mComputerWins", mGame.getWins( 2 ) );
        ed.putInt( "difficultyLevel", mGame.getDifficultyLevel( ).ordinal( ) );
        ed.commit( );
    }


    public int mComputerMove( ){
        mInfoTextView.setText( R.string.turn_computer );
        int move = mGame.getComputerMove( );
        setMove( TicTacToeGame.COMPUTER_PLAYER, move );
        if( mSoundOn ) {
            mComputerMediaPlayer.start( );
        }
        turn = true;
        return mGame.checkForWinner( );
    }

    public void winner( ){
        int winner = mGame.checkForWinner( );
        if( winner == 0 ){
            mInfoTextView.setText( R.string.turn_human );
        }else if( winner == 1 ){
            mInfoTextView.setText( R.string.result_tie );
            mGame.setWins( 1 );
            mCountTiesTextView.setText( mGame.getWins( 1 ).toString( ) );
            mGameOver = true;
        }else if (winner == 2) {
            String defaultMessage = getResources().getString(R.string.result_human_wins);
            mInfoTextView.setText(mPrefs.getString("victory_message", defaultMessage));
            mGame.setWins( 0 );
            mCountHumanTextView.setText( mGame.getWins( 0 ).toString( ) );
            mGameOver = true;
        }else {
            mInfoTextView.setText(R.string.result_computer_wins);
            mGame.setWins( 2 );
            mCountAndroidTextView.setText( mGame.getWins( 2 ).toString( ) );
            mGameOver = true;
        }
    }

    private void displayScores() {
        mCountHumanTextView.setText( mGame.getWins( 0 ).toString( ) );
        mCountTiesTextView.setText( mGame.getWins( 1 ).toString( ) );
        mCountAndroidTextView.setText( mGame.getWins( 2 ).toString( ) );
    }

}
