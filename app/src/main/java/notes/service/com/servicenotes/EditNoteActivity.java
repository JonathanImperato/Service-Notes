package notes.service.com.servicenotes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import java.util.Date;

import notes.service.com.servicenotes.data.Note;
import notes.service.com.servicenotes.util.Strings;
import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;

/**
 * Actividad para editar notas.
 *
 * @author Imperato Jonathan
 */
public class EditNoteActivity extends RoboActionBarActivity {

    private static final String EXTRA_NOTE = "EXTRA_NOTE";

    public static Activity activity;
    @InjectView(R.id.note_title)
    private EditText noteTitleText;
    @InjectView(R.id.note_content)
    private EditText noteContentText;
    @InjectView(R.id.note_content2)
    private EditText noteContentText2;
    @InjectView(R.id.note_emails)
    private EditText noteEmails;
    @InjectView(R.id.note_phone)
    private EditText notePhone;

    @InjectView(R.id.action_save)
    private FloatingActionButton fab;
    private Note note;

    /**
     * Construye el Intent para llamar a esta actividad con una nota ya existente.
     *
     * @param context el contexto que la llama.
     * @param note    la nota a editar.
     * @return un Intent.
     */
    public static Intent buildIntent(Context context, Note note) {
        Intent intent = new Intent(context, EditNoteActivity.class);
        intent.putExtra(EXTRA_NOTE, note);
        return intent;
    }

    /**
     * Construye el Intent para llamar a esta actividad para crear una nota.
     *
     * @param context el contexto que la llama.
     * @return un Intent.
     */
    public static Intent buildIntent(Context context) {
        return buildIntent(context, null);
    }

    /**
     * Recupera la nota editada.
     *
     * @param intent el Intent que vine en onActivityResult
     * @return la nota actualizada
     */
    public static Note getExtraNote(Intent intent) {
        return (Note) intent.getExtras().get(EXTRA_NOTE);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ServiceUtils.setSavedAnimations(this);
        Window window = getWindow();
        ServiceUtils.setSavedTheme(this, window);
        ServiceUtils.setSavedLanguage(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        //  ActionBar actionBar = getSupportActionBar();
        // Inicializa los componentes //////////////////////////////////////////////////////////////
        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Muestra la flecha hacia atrás
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getResources().getString(R.string.title_activity_edit_note));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ServiceUtils.setSavedAnimations(EditNoteActivity.this);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        setSupportActionBar(toolbar);
        note = (Note) getIntent().getSerializableExtra(EXTRA_NOTE); // Recuperar la nota del Intent

        if (note != null) { // Editar nota existente
            noteTitleText.setText(note.getTitle());
            noteContentText.setText(note.getContent());
            noteContentText2.setText(note.getContent2());
            noteEmails.setText(note.getEmails());
            notePhone.setText(note.getPhone());
        } else { // Nueva nota
            note = new Note();
            note.setCreatedAt(new Date());
        }
        fab = (FloatingActionButton) findViewById(R.id.action_save);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNoteFormOk()) {
                    setNoteResult();
                    ServiceUtils.setSavedAnimations(EditNoteActivity.this);
                    finish();
                } else validateNoteForm();
            }

        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.edit_note, menu);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ServiceUtils.setSavedAnimations(this);
                onBackPressed();
                return true;
            case R.id.action_save:
                if (isNoteFormOk()) {
                    setNoteResult();
                    ServiceUtils.setSavedAnimations(this);
                    finish();
                } else validateNoteForm();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * @return {@code true} si tiene titulo y contenido; {@code false} en cualquier otro caso.
     */
    private boolean isNoteFormOk() {
        return !Strings.isNullOrBlank(noteTitleText.getText().toString()) && !Strings.isNullOrBlank(noteContentText.getText().toString());
    }

    /**
     * Actualiza el contenido del objeto Note con los campos de texto del layout y pone el objeto
     * como resultado de esta actividad.
     */
    private void setNoteResult() {
        note.setTitle(noteTitleText.getText().toString().trim());
        note.setContent(noteContentText.getText().toString().trim());
        note.setContent2(noteContentText2.getText().toString().trim());
        note.setEmails(noteEmails.getText().toString().trim());
        note.setPhone(notePhone.getText().toString().trim());
        note.setUpdatedAt(new Date());
        Intent resultIntent = new Intent();
        ServiceUtils.setSavedAnimations(this);
        resultIntent.putExtra(EXTRA_NOTE, note);
        setResult(RESULT_OK, resultIntent);
    }

    /**
     * Muestra mensajes de validación de la forma de la nota.
     */
    private void validateNoteForm() {
        TextInputLayout til = (TextInputLayout) findViewById(R.id.text_input_layout);
        TextInputLayout til2 = (TextInputLayout) findViewById(R.id.text_input_layout2);

        if (Strings.isNullOrBlank(noteTitleText.getText().toString())) {
            //message = new StringBuilder().append(getString(R.string.title_required));
            til.setErrorEnabled(true);
            til.setError(getString(R.string.title_required));
            til.setErrorEnabled(false);
        }
        if (Strings.isNullOrBlank(noteContentText.getText().toString())) {
            til2.setErrorEnabled(true);
            til2.setError(getString(R.string.content_required));
            til2.setErrorEnabled(false);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBackPressed() {
        // No se edito ningúna nota ni creo alguna nota
        setResult(RESULT_CANCELED);
        Intent i = new Intent(EditNoteActivity.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ServiceUtils.setSavedAnimations(this);
        finish();
    }
}