package notes.service.com.servicenotes.widget;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import notes.service.com.servicenotes.R;
import notes.service.com.servicenotes.data.Note;

public class NotesAdapter extends BaseAdapter implements Filterable {
    /**
     * Wrapper para notas. Util para cambiar el fondo de los item seleccionados.
     */

    private static final String DATABASE_TABLE = "notes_schema-v%s.sql";
    private SQLiteDatabase mDb;
    private ArrayList<NotesAdapter.NoteViewWrapper> notesData;
    public Context context;

    private ItemFilter mFilter = new ItemFilter();

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }


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
    private List<NoteViewWrapper> filteredData;
    //return the filtered data rather than the original data
    //I think you could reset the filtered data back to the original onBackPressed from the search

    /**
     * Constructor.
     *
     * @param data la lista de notas a usar como fuente de datos para este adaptador.
     */
    public NotesAdapter(List<NoteViewWrapper> data) {
        this.filteredData = data;
        this.data = data;
    }

    /**
     * @return cuantos datos hay en la lista de notas.
     */
    @Override
    public int getCount() {
        return filteredData.size();
    }

    /**
     * @param position la posición de la nota que se quiere
     * @return la nota en la posición dada.
     */
    @Override
    public NoteViewWrapper getItem(int position) {
        return filteredData.get(position);
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
            //CardView cardsView = (CardView) convertView.findViewById(R.id.note_cardboard);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else holder = (ViewHolder) convertView.getTag(); // ya existe, solo es reciclarlo
        // Inicializa la vista con los datos de la nota
        NoteViewWrapper noteViewWrapper = filteredData.get(position);
        holder.noteIdText.setText(String.valueOf(noteViewWrapper.note.getId()));
        holder.noteTitleText.setText(noteViewWrapper.note.getTitle());
        // se la nota è più di 80 caratteri"..."
        holder.noteContentText.setText(noteViewWrapper.note.getContent().length() >= 80 ? noteViewWrapper.note.getContent().substring(0, 80).concat("...") : noteViewWrapper.note.getContent());
        holder.noteDateText.setText(DATETIME_FORMAT.format(noteViewWrapper.note.getUpdatedAt()));
        // Cambia il colore se la nota è cliccata
        if (noteViewWrapper.isSelected) {
            CardView cardsView = (CardView) convertView.findViewById(R.id.note_cardboard);
            // sfondo grigio quando nota selezionata
            cardsView.setCardBackgroundColor(Color.parseColor("#cacaca"));
            //cardsView.setCardBackgroundColor(R.drawable.statelist_item_background);
            //cardsView.setBackgroundResource(R.drawable.selected_note);
            //holder.parent.setBackgroundColor(parent.getContext().getResources().getColor(R.color.selected_note));
            //LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(
            //LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        } else {
            CardView cardsView = (CardView) convertView.findViewById(R.id.note_cardboard);
            //sfondo bianco quando nota non selezionata
            cardsView.setCardBackgroundColor(Color.WHITE);
            //holder.parent.setBackgroundColor(parent.getContext().getResources().getColor(android.R.color.transparent));
        }
        return convertView;
    }


    /**
     * Almacena componentes visuales para acceso rápido sin necesidad de buscarlos muy seguido.
     */
    private static class ViewHolder {

        private TextView noteIdText;
        private TextView noteTitleText;
        private TextView noteContentText;
        private TextView noteContentText2;
        private TextView noteEmails;
        private TextView notePhone;
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
    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();


            int count = data.size();
            List<NoteViewWrapper> nlist = new ArrayList<>();

            String filterableString;
            String filterableStringDesc;
            String filterableStringEmail;
            String filterableStringAdres;
            String filterableStringPhone;

            for (int i = 0; i < count; i++) {
                filterableString = data.get(i).getNote().getTitle();
                filterableStringDesc = data.get(i).getNote().getContent();
                filterableStringEmail = data.get(i).getNote().getEmails();
                filterableStringAdres = data.get(i).getNote().getContent2();
                filterableStringPhone = data.get(i).getNote().getPhone();
                if (filterableString.toLowerCase().contains(filterString) || filterableStringPhone.toLowerCase().contains(filterString) ||  filterableStringAdres.toLowerCase().contains(filterString) || filterableStringEmail.toLowerCase().contains(filterString) || filterableStringDesc.toLowerCase().contains(filterString)) {
                    //checks for matching word in title, content, email, phone and address
                    //add any other content to search in this if statement
                    nlist.add(data.get(i));
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<NoteViewWrapper>) results.values;
            notifyDataSetChanged();
        }

    }

}