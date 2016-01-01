package notes.service.com.servicenotes.widget;

import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.List;

import notes.service.com.servicenotes.R;
import notes.service.com.servicenotes.data.Note;

/**
 * Adaptador de notas. Actua como intermediario entre la vista y los datos.
 *
 * @author Daniel Pedraza Arcega
 * @see <a href="http://bit.ly/1vZt3ny">Building Layouts with an Adapter</a>
 */
public class NotesAdapter extends BaseAdapter {
    /** Wrapper para notas. Util para cambiar el fondo de los item seleccionados. */
    public static final String KEY_TITLE = "title";
    public static final String KEY_BODY = "body";
    public static final String KEY_CATID = "id";
    private static final String DATABASE_TABLE = "notes_schema-v%s.sql";
    private SQLiteDatabase mDb;
    public static class NoteViewWrapper {

        private final Note note;
        private boolean isSelected;

        /**
         * Contruye un nuevo NoteWrapper con la nota dada.
         *
         * @param note una nota.
         */
        public NoteViewWrapper(Note note) {
            this.note = note;
        }

        public Note getNote() {
            return note;
        }

        public void setSelected(boolean isSelected) {
            this.isSelected = isSelected;
        }
    }

    private static final DateFormat DATETIME_FORMAT = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);

    private final List<NoteViewWrapper> data;

    /**
     * Constructor.
     *
     * @param data la lista de notas a usar como fuente de datos para este adaptador.
     */
    public NotesAdapter(List<NoteViewWrapper> data) {
        this.data = data;
    }

    /** @return cuantos datos hay en la lista de notas. */
    @Override
    public int getCount() {
        return data.size();
    }

    /**
     * @param position la posición de la nota que se quiere
     * @return la nota en la posición dada.
     */
    @Override
    public NoteViewWrapper getItem(int position) {
        return data.get(position);
    }

    /**
     * @param position una posición
     * @return la misma posición dada
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) { // inflar componente visual
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_row, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag(); // ya existe, solo es reciclarlo
        // Inicializa la vista con los datos de la nota
        NoteViewWrapper noteViewWrapper = data.get(position);
        holder.noteIdText.setText(String.valueOf(noteViewWrapper.note.getId()));
        holder.noteTitleText.setText(noteViewWrapper.note.getTitle());
        // Corta la cadena a 80 caracteres y le agrega "..."
        holder.noteContentText.setText(noteViewWrapper.note.getContent().length() >= 80 ? noteViewWrapper.note.getContent().substring(0, 80).concat("...") : noteViewWrapper.note.getContent());
        holder.noteDateText.setText(DATETIME_FORMAT.format(noteViewWrapper.note.getUpdatedAt()));
        // Cambia el color del fondo si es seleccionado
        if (noteViewWrapper.isSelected) holder.parent.setBackgroundColor(parent.getContext().getResources().getColor(R.color.selected_note));
        // Sino lo regresa a transparente
        else holder.parent.setBackgroundColor(parent.getContext().getResources().getColor(android.R.color.transparent));
        return convertView;
    }

   /* public String createJsonFromNotes(){
        List<Note> noteList = new ArrayList<>();
        String orderBy = NotesAdapter.KEY_TITLE + " ASC";
        Cursor cursor = mDb.query(DATABASE_TABLE, new String[] { KEY_CATID, KEY_TITLE,
                KEY_BODY}, null, null, null, null, orderBy);
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(0);
                note.setTitle(cursor.getString(cursor.getColumnIndex(NotesAdapter.KEY_TITLE)));
                note.setContent(cursor.getString(cursor.getColumnIndex(NotesAdapter.KEY_BODY)));
                noteList.add(note);
            } while (cursor.moveToNext());
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //Use GSON to serialize Array List to JSON
        System.out.println(gson.toJson(noteList));

        return gson.toJson(noteList);
    }

    public void createNotesFromJson(String json) {
        System.out.println(json);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Note> noteList = gson.fromJson(json, new TypeToken<List<Note>>() {
        }.getType());

        for (Note note : noteList) {
            createNote(note.getTitle(),  note.getContent(), note.getId());
        }
    }

    public long createNote(String title, String body) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITLE, title);
        initialValues.put(KEY_BODY, body);
        initialValues.put(KEY_CATID, 0);

        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    public long createNote(String title, String body, int Id) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITLE, title);
        initialValues.put(KEY_BODY, body);
        initialValues.put(KEY_CATID, Id);

        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }*/
    /** Almacena componentes visuales para acceso rápido sin necesidad de buscarlos muy seguido.*/
    private static class ViewHolder {

        private TextView noteIdText;
        private TextView noteTitleText;
        private TextView noteContentText;
        private TextView noteDateText;

        private View parent;

        /**
         * Constructor. Encuentra todas los componentes visuales en el componente padre dado.
         *
         * @param parent un componente visual.
         */
        private ViewHolder(View parent) {
            this.parent = parent;
            noteIdText = (TextView) parent.findViewById(R.id.note_id);
            noteTitleText = (TextView) parent.findViewById(R.id.note_title);
            noteContentText = (TextView) parent.findViewById(R.id.note_content);
            noteDateText = (TextView) parent.findViewById(R.id.note_date);
        }
    }
}