package com.example.brightminds.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.brightminds.R;
import com.example.brightminds.database.NotesDatabase;
import com.example.brightminds.entities.Note;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateNoteActivity extends AppCompatActivity
{

    private EditText inputNoteTitle, inputNoteSubtitle, inputNoteText;
    private TextView textDateTime;
    private View viewSubtitleIndicator;

    private String selectedNoteColor;

    private Note alreadyAvailableNote;

    private AlertDialog dialogDeleteNote;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        ImageView imageback = findViewById(R.id.ImageBack);
        imageback.setOnClickListener(v -> onBackPressed());

        inputNoteTitle = findViewById(R.id.inputNoteTitle);
        inputNoteSubtitle = findViewById(R.id.inputNoteSubtitle);
        inputNoteText = findViewById(R.id.inputNote);
        textDateTime = findViewById(R.id.textDateTime);
        viewSubtitleIndicator = findViewById(R.id.viewSubtitleIndicator);

        textDateTime.setText(new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault()).format(new Date()));

        ImageView imageSave = findViewById(R.id.ImageSave);
        imageSave.setOnClickListener(v -> saveNote());

        selectedNoteColor = "#333333";

        if(getIntent().getBooleanExtra("isViewOrUpdate", false))
        {
            alreadyAvailableNote = (Note) getIntent().getSerializableExtra("note");
            setViewOrUpdateNote();
        }

        initMiscellaneous();
        setSubtitleIndicatorColor();

    }

    private void setViewOrUpdateNote()
    {
        inputNoteTitle.setText(alreadyAvailableNote.getTitle());
        inputNoteSubtitle.setText(alreadyAvailableNote.getSubtitle());
        inputNoteText.setText(alreadyAvailableNote.getNoteText());
        textDateTime.setText(alreadyAvailableNote.getDateTime());
    }

    private void saveNote()
    {
        if(inputNoteTitle.getText().toString().trim().isEmpty())
        {
            Toast.makeText(this, "Title is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(inputNoteSubtitle.getText().toString().trim().isEmpty() && inputNoteText.getText().toString().trim().isEmpty())
        {
            Toast.makeText(this, "Notes cant be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        final Note note = new Note();

        note.setTitle(inputNoteTitle.getText().toString());
        note.setSubtitle(inputNoteSubtitle.getText().toString());
        note.setNoteText(inputNoteText.getText().toString());
        note.setDateTime(textDateTime.getText().toString());
        note.setColor(selectedNoteColor);

        if(alreadyAvailableNote != null)
        {
            note.setId(alreadyAvailableNote.getId());
        }

        @SuppressLint("StaticFieldLeak")
        class saveNoteTask extends AsyncTask<Void, Void, Void>
        {
            @Override
            protected Void doInBackground(Void... voids)
            {
                NotesDatabase.getDatabase(getApplicationContext()).noteDao().InsertNote(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void avoid)
            {
                super.onPostExecute(avoid);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }

        new saveNoteTask().execute();
    }

    private void initMiscellaneous()
    {
        final LinearLayout layoutMiscellaneous = findViewById(R.id.layoutMiscellaneous);
        final BottomSheetBehavior<LinearLayout> bottomSheetBehavior = BottomSheetBehavior.from(layoutMiscellaneous);
        layoutMiscellaneous.findViewById(R.id.textMiscellaneous).setOnClickListener(v -> {
            if(bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED)
            {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
            else
            {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        final ImageView imagecolor1 = layoutMiscellaneous.findViewById(R.id.imagecolor1);
        final ImageView imagecolor2 = layoutMiscellaneous.findViewById(R.id.imagecolor2);
        final ImageView imagecolor3 = layoutMiscellaneous.findViewById(R.id.imagecolor3);
        final ImageView imagecolor4 = layoutMiscellaneous.findViewById(R.id.imagecolor4);
        final ImageView imagecolor5 = layoutMiscellaneous.findViewById(R.id.imagecolor5);

        layoutMiscellaneous.findViewById(R.id.viewcolor1).setOnClickListener(v ->
        {
            selectedNoteColor = "#333333";
            imagecolor1.setImageResource(R.drawable.ic_done);
            imagecolor2.setImageResource(0);
            imagecolor3.setImageResource(0);
            imagecolor4.setImageResource(0);
            imagecolor5.setImageResource(0);
            setSubtitleIndicatorColor();
        });

        layoutMiscellaneous.findViewById(R.id.viewcolor2).setOnClickListener(v ->
        {
            selectedNoteColor = "#FDBE3B";
            imagecolor1.setImageResource(0);
            imagecolor2.setImageResource(R.drawable.ic_done);
            imagecolor3.setImageResource(0);
            imagecolor4.setImageResource(0);
            imagecolor5.setImageResource(0);
            setSubtitleIndicatorColor();
        });

        layoutMiscellaneous.findViewById(R.id.viewcolor3).setOnClickListener(v ->
        {
            selectedNoteColor = "#FF4842";
            imagecolor1.setImageResource(0);
            imagecolor2.setImageResource(0);
            imagecolor3.setImageResource(R.drawable.ic_done);
            imagecolor4.setImageResource(0);
            imagecolor5.setImageResource(0);
            setSubtitleIndicatorColor();
        });

        layoutMiscellaneous.findViewById(R.id.viewcolor4).setOnClickListener(v ->
        {
            selectedNoteColor = "#3A52FC";
            imagecolor1.setImageResource(0);
            imagecolor2.setImageResource(0);
            imagecolor3.setImageResource(0);
            imagecolor4.setImageResource(R.drawable.ic_done);
            imagecolor5.setImageResource(0);
            setSubtitleIndicatorColor();
        });

        layoutMiscellaneous.findViewById(R.id.viewcolor5).setOnClickListener(v ->
        {
            selectedNoteColor = "#000000";
            imagecolor1.setImageResource(0);
            imagecolor2.setImageResource(0);
            imagecolor3.setImageResource(0);
            imagecolor4.setImageResource(0);
            imagecolor5.setImageResource(R.drawable.ic_done);
            setSubtitleIndicatorColor();
        });

        if(alreadyAvailableNote != null && alreadyAvailableNote.getColor() != null && !alreadyAvailableNote.getColor().trim().isEmpty())
        {
            switch (alreadyAvailableNote.getColor())
            {
                case "#FDBE3B":
                    layoutMiscellaneous.findViewById(R.id.viewcolor2).performClick();
                    break;
                case "#FF4842":
                    layoutMiscellaneous.findViewById(R.id.viewcolor3).performClick();
                    break;
                case "#3A52FC":
                    layoutMiscellaneous.findViewById(R.id.viewcolor4).performClick();
                    break;
                case "#000000":
                    layoutMiscellaneous.findViewById(R.id.viewcolor5).performClick();
                    break;
            }
        }

        if(alreadyAvailableNote != null)
        {
            layoutMiscellaneous.findViewById(R.id.layoutDeleteNote).setVisibility(View.VISIBLE);
            layoutMiscellaneous.findViewById(R.id.layoutDeleteNote).setOnClickListener(v -> {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                showDeleteNoteDialog();
            });
        }

    }

    private void showDeleteNoteDialog()
    {
        if(dialogDeleteNote == null)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(CreateNoteActivity.this);
            View view = LayoutInflater.from(this).inflate(R.layout.layout_delete_note, (ViewGroup) findViewById(R.id.layoutDeleteNoteContainer));
            builder.setView(view);
            dialogDeleteNote = builder.create();
            if(dialogDeleteNote.getWindow() != null)
            {
                dialogDeleteNote.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            view.findViewById(R.id.textDeleteNote).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    @SuppressLint("StaticFieldLeak")
                    class DeleteNoteTask extends AsyncTask<Void, Void, Void>
                    {

                        @Override
                        protected Void doInBackground(Void... voids)
                        {
                            NotesDatabase.getDatabase(getApplicationContext()).noteDao().deleteNote(alreadyAvailableNote);
                            return null;
                        }

                        protected void onPostExecute(Void aVoid)
                        {
                            super.onPostExecute(aVoid);
                            Intent intent = new Intent();
                            intent.putExtra("isNoteDeleted", true);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }

                    new DeleteNoteTask().execute();
                }
            });

            view.findViewById(R.id.textCancel).setOnClickListener(v -> dialogDeleteNote.dismiss());
        }

        dialogDeleteNote.show();
    }
    private void setSubtitleIndicatorColor()
    {
        GradientDrawable gradientDrawable = (GradientDrawable) viewSubtitleIndicator.getBackground();
        gradientDrawable.setColor(Color.parseColor(selectedNoteColor));
    }
}